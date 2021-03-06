package com.lhiot.ims.rbac.api;

import com.leon.microx.util.Maps;
import com.leon.microx.util.StringUtils;
import com.leon.microx.util.auditing.MD5;
import com.leon.microx.web.result.Tips;
import com.leon.microx.web.session.Authority;
import com.leon.microx.web.session.Sessions;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.rbac.aspect.LogCollection;
import com.lhiot.ims.rbac.domain.*;
import com.lhiot.ims.rbac.service.ImsOperationService;
import com.lhiot.ims.rbac.service.ImsRelationUserRoleService;
import com.lhiot.ims.rbac.service.ImsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Description:用户接口类
 *
 * @author yijun
 * @date 2018/09/29
 */
@Api(description = "用户接口")
@Slf4j
@RestController
@RequestMapping("/admin")
@Component
public class ImsUserApi {


    private Sessions session;
    private final ImsUserService imsUserService;
    private final ImsOperationService imsOperationService;
    private final ImsRelationUserRoleService imsRelationUserRoleService;

    public ImsUserApi(ObjectProvider<Sessions> sessionsObjectProvider, ImsUserService imsUserService, ImsOperationService imsOperationService, ImsRelationUserRoleService imsRelationUserRoleService) {
        this.session = sessionsObjectProvider.getIfAvailable();
        this.imsUserService = imsUserService;
        this.imsOperationService = imsOperationService;
        this.imsRelationUserRoleService = imsRelationUserRoleService;
    }

    @Sessions.Uncheck
    @PostMapping("/backdoor")
    @ApiOperation("管理后门。（绕开所有权限）")
    public ResponseEntity backdoor(HttpServletRequest request) {
        Sessions.User sessionUser = session.create(request).user(Maps.of("1", "leon"))
                .timeToLive(30, TimeUnit.MINUTES)
                .authorities(Authority.of("/**", RequestMethod.values())); // 具有所有API访问权限
        String sessionId = session.cache(sessionUser);
        return ResponseEntity
                .created(URI.create("/ims-user/session/" + sessionId))
                .header(Sessions.HTTP_HEADER_NAME, sessionId)
                .build();
    }

    @LogCollection
    @Sessions.Uncheck
    @PostMapping("/sessions")
    @ApiOperation("管理员登录, 返回session用户")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "login", value = "登录参数", required = true, dataType = "AdminLogin")
    public ResponseEntity login(@RequestBody AdminLogin login, @ApiIgnore HttpServletRequest request) {
        ImsUser admin = imsUserService.selectByAccount(login.getAccount());
        if (Objects.isNull(admin)) {
            return ResponseEntity.badRequest().body("帐号不存在");
        }

        if (!Objects.equals(Status.AVAILABLE, Status.valueOf(Status.class, admin.getStatus()))) {
            return ResponseEntity.badRequest().body("帐号状态异常：" + Status.valueOf(admin.getStatus()).getMessage());
        }

        String md5 = MD5.str(login.getPassword());
        if (!md5.equalsIgnoreCase(admin.getPassword())) {
            return ResponseEntity.badRequest().body("密码错误");
        }
        Sessions.User sessionUser = session
                .create(request)
                .user(Maps.of(
                        "id", admin.getId(), "name", admin.getName(), "account", admin.getAccount(), "avatar", StringUtils.isBlank(admin.getAvatarUrl()) ? "" : admin.getAvatarUrl()
                ))
                .timeToLive(30, TimeUnit.MINUTES);

        //填充访问权限：sessionUser.authorities(Authority.of("/**/users/?, RequestMethod.GET))
        //查找用户的操作权限
        List<ImsOperation> imsOperationList = imsOperationService.listByUserId(admin.getId());
        List<Authority> authorityList = imsOperationList.stream()
                .map(op -> Authority.of(op.getAntUrl(), StringUtils.tokenizeToStringArray(op.getType(), ",")))
                .collect(Collectors.toList());
        sessionUser.authorities(authorityList);
        String sessionId = session.cache(sessionUser);
        this.session.expired(sessionId, (bind, user) -> log.warn("用户 {} session过期……", bind));
        try {
            return ResponseEntity
                    .created(URI.create("/ims-user/session/" + sessionId))
                    .header(Sessions.HTTP_HEADER_NAME, sessionId)
                    .body("{\"XSessionId\":\"" + sessionId + "\"}");
        } finally {
            // 更新用户最后登录时间
            imsUserService.updateLastLogin(Maps.of("id", admin.getId(), "last_login_at", Instant.now()));
        }
    }


    @LogCollection
    @DeleteMapping("/sessions")
    @ApiOperation("管理员退出登录")
    public ResponseEntity logout(HttpServletRequest request, Sessions.User user) {
        String sessionId = session.id(request);
        if (StringUtils.hasLength(sessionId)) {
            session.invalidate(sessionId);
            log.info("User {} Logout.", user.toString());
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sessions")
    @ApiOperation("获取登录用户信息")
    public ResponseEntity userInfo(Sessions.User user) {
        return ResponseEntity.ok(user.getUser());
    }

    @LogCollection
    @PostMapping("/")
    @ApiOperation(value = "添加用户", response = ImsUser.class)
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "imsUser", value = "要添加的用户", required = true, dataType = "ImsUser")
    public ResponseEntity create(@RequestBody ImsUser imsUser) {
        log.debug("添加用户\t param:{}", imsUser);

        imsUser.setCreateAt(Date.from(Instant.now()));
        Tips<ImsUser> tips = imsUserService.create(imsUser);
        return tips.err() ? ResponseEntity.badRequest().body(tips.getMessage()) : ResponseEntity.ok(tips.getData());
    }


    @LogCollection
    @PutMapping("/{id}")
    @ApiOperation(value = "根据id更新用户", response = ImsUser.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "用户id", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = ApiParamType.BODY, name = "imsUser", value = "要更新的用户", required = true, dataType = "ImsUser")
    })
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody ImsUser imsUser) {
        log.debug("根据id更新用户\t id:{} param:{}", id, imsUser);

        imsUser.setId(id);
        return ResponseEntity.ok(imsUserService.updateById(imsUser));
    }

    @LogCollection
    @PutMapping("/update/relation/{id}/{roleIds}")
    @ApiOperation(value = "根据用户id更新用户关联角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "用户id", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "roleIds", value = "要更新的用户角色ids", required = true, dataType = "String")
    })
    public ResponseEntity updateRelation(@PathVariable("id") Long id, @PathVariable("roleIds") String roleIds) {
        log.debug("根据用户id更新用户关联角色信息\t id:{} param:{}", id, roleIds);

        return imsRelationUserRoleService.create(id, roleIds) > 0 ? ResponseEntity.ok().build() : ResponseEntity.badRequest().body("根据用户id更新用户关联角色信息失败");
    }

    @LogCollection
    @DeleteMapping("/batch/{ids}")
    @ApiOperation(value = "根据ids删除用户")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "ids", value = "要删除用户的ids,逗号分割", required = true, dataType = "String")
    public ResponseEntity deleteByIds(@PathVariable("ids") String ids) {
        log.debug("根据ids删除用户\t param:{}", ids);

        return imsUserService.deleteByIds(ids) > 0 ? ResponseEntity.noContent().build() : ResponseEntity.badRequest().body("根据ids删除用户失败");
    }

    @ApiOperation(value = "根据id查询用户", notes = "根据id查询用户", response = ImsUser.class)
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "主键id", required = true, dataType = "Long")
    @GetMapping("/id/{id}")
    public ResponseEntity findImsUser(@PathVariable("id") Long id) {
        log.debug("根据id查询用户\t param:{}", id);

        return ResponseEntity.ok(imsUserService.selectById(id));
    }

    @PostMapping("/pages")
    @ApiOperation(value = "查询用户分页列表", response = ImsUser.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "imsUser", value = "查询用户参数", required = true, dataType = "ImsUser")
    public ResponseEntity pageQuery(@RequestBody ImsUser imsUser) {
        log.debug("查询用户分页列表\t param:{}", imsUser);

        return ResponseEntity.ok(imsUserService.pageList(imsUser));
    }

    @PostMapping("/relation/roles/{id}")
    @ApiOperation(value = "查询用户id查询已关联的角色列表", response = ImsRole.class, responseContainer = "List")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "要查询的用户id", required = true, dataType = "Long")
    public ResponseEntity getRelationRolesById(@PathVariable("id") Long id) {
        log.debug("查询用户id查询已关联的角色列表\t param:{}", id);

        return ResponseEntity.ok(imsUserService.getRelationRolesById(id));
    }

}
