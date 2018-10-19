package com.lhiot.ims.rbac.api;

import com.leon.microx.support.http.RemoteInvoker;
import com.leon.microx.support.result.Pages;
import com.leon.microx.support.session.Authority;
import com.leon.microx.support.session.Sessions;
import com.leon.microx.support.swagger.ApiParamType;
import com.leon.microx.util.Maps;
import com.leon.microx.util.StringUtils;
import com.leon.microx.util.auditing.MD5;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
* Description:用户接口类
* @author yijun
* @date 2018/09/29
*/
@Api(description = "用户接口")
@Slf4j
@RestController
@RequestMapping("/admin")
public class ImsUserApi {

    private RemoteInvoker invoker;
    private Sessions session;
    private final ImsUserService imsUserService;
    private final ImsOperationService imsOperationService;
    private final ImsRelationUserRoleService imsRelationUserRoleService;

    @Autowired
    public ImsUserApi(RemoteInvoker invoker, ObjectProvider<Sessions> sessionsObjectProvider, ImsUserService imsUserService, ImsOperationService imsOperationService, ImsRelationUserRoleService imsRelationUserRoleService) {
        this.invoker = invoker;
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

    @Sessions.Uncheck
    @PostMapping("/sessions")
    @ApiOperation("管理员登录, 返回session用户")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "login", value = "登录参数", required = true, dataType = "AdminLogin")
    public ResponseEntity login(@RequestBody AdminLogin login, @ApiIgnore HttpServletRequest request) {

        //接口请求例子
//         ResponseEntity<String> response = invoker.server("user-center")
//                 .uriVariables(Maps.of("id", "111"))
//                 .addHeaders(Pair.of("version", "v1-1-1"))
//                 .body(Collections.emptyMap())
//                 .request("/user/session/{id}", HttpMethod.PUT, String.class);

        ImsUser admin = imsUserService.selectByAccount(login.getAccount());
        if (Objects.isNull(admin)) {
            return ResponseEntity.badRequest().body("帐号不存在");
        }

        if (!Objects.equals(Status.AVAILABLE, admin.getStatus())) {
            return ResponseEntity.badRequest().body("帐号状态异常：" + admin.getStatus().getMessage());
        }

        String md5 = MD5.str(login.getPassword());
        if (!md5.equalsIgnoreCase(admin.getPassword())) {
            return ResponseEntity.badRequest().body("密码错误");
        }
        Sessions.User sessionUser = session.create(request).user(Maps.of(
                "id",admin.getId(),"name",admin.getName(),"account",admin.getAccount(),"avatar",admin.getAvatarUrl()
        )).timeToLive(30, TimeUnit.MINUTES);

        //填充访问权限：sessionUser.authorities(Authority.of("/**/users/?, RequestMethod.GET))
        //查找用户的操作权限
        List<ImsOperation> imsOperationList = imsOperationService.listByUserId(admin.getId());
        List<Authority> authorityList = Objects.requireNonNull(imsOperationList).stream()
                .map(op -> Authority.of(op.getAntUrl(), StringUtils.tokenizeToStringArray(op.getType(), ",")))
                .collect(Collectors.toList());

//                new ArrayList<>(imsOperationList.size());
//        imsOperationList.forEach(item->authorityList.add(Authority.of(item.getAntUrl(), ConverStrToRequestMethod.getListFromStr(item.getType()))));
        //sessionUser.authorities(Authority.of("/**/ims-menu/**", RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT));
        sessionUser.authorities(authorityList);
        String sessionId = session.cache(sessionUser);
        try {
                return ResponseEntity
                    .created(URI.create("/ims-user/session/" + sessionId))
                    .header(Sessions.HTTP_HEADER_NAME, sessionId)
                    .body("{\"XSessionId\":\""+sessionId+"\"}");
        } finally {
            //adminMapper.updateLastLogin(Maps.of("id", admin.getId(), "last_login_at", Instant.now()));
        }
    }

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
    public ResponseEntity userInfo(HttpServletRequest request, Sessions.User user) {
        String sessionId = session.id(request);
        return ResponseEntity.ok(session.user(sessionId).getUser());
    }

    @PostMapping("/")
    @ApiOperation(value = "添加用户")
    @ApiImplicitParam(paramType = "body", name = "imsUser", value = "要添加的用户", required = true, dataType = "ImsUser")
    public ResponseEntity<ImsUser> create(@RequestBody ImsUser imsUser) {
        log.debug("添加用户\t param:{}",imsUser);
        
        return ResponseEntity.ok(imsUserService.create(imsUser));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "根据id更新用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "用户id", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "body", name = "imsUser", value = "要更新的用户", required = true, dataType = "ImsUser")
    })
    public ResponseEntity<ImsUser> update(@PathVariable("id") Long id,@RequestBody ImsUser imsUser) {
        log.debug("根据id更新用户\t id:{} param:{}",id,imsUser);
        imsUser.setId(id);
        
        return ResponseEntity.ok(imsUserService.updateById(imsUser));
    }

    @PutMapping("/update/relation/{id}/{roleIds}")
    @ApiOperation(value = "根据id更新用户角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "用户id", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "path", name = "roleIds", value = "要更新的用户角色ids", required = true, dataType = "String")
    })
    public ResponseEntity<Integer> update(@PathVariable("id") Long id,@PathVariable("roleIds") String roleIds) {
        log.debug("根据用户id更新用户关联角色信息\t id:{} param:{}",id,roleIds);

        return ResponseEntity.ok(imsRelationUserRoleService.create(id,roleIds));
    }


    @DeleteMapping("/{ids}")
    @ApiOperation(value = "根据ids删除用户")
    @ApiImplicitParam(paramType = "path", name = "ids", value = "要删除用户的ids,逗号分割", required = true, dataType = "String")

    public ResponseEntity<Integer> deleteByIds(@PathVariable("ids") String ids) {
        log.debug("根据ids删除用户\t param:{}",ids);
        
        return ResponseEntity.ok(imsUserService.deleteByIds(ids));
    }
    
    @ApiOperation(value = "根据id查询用户", notes = "根据id查询用户")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键id", required = true, dataType = "Long")
    @GetMapping("/{id}")
    public ResponseEntity<ImsUser> findImsUser(@PathVariable("id") Long id) {

        return ResponseEntity.ok(imsUserService.selectById(id));
    }
    
    @PostMapping("/pages")
    @ApiOperation(value = "查询用户分页列表")
    @ApiImplicitParam(paramType = "body", name = "imsUser", value = "查询用户参数", required = true, dataType = "ImsUser")
    public ResponseEntity<Pages<ImsUser>> pageQuery(@RequestBody ImsUser imsUser){
        log.debug("查询用户分页列表\t param:{}",imsUser);
        
        return ResponseEntity.ok(imsUserService.pageList(imsUser));
    }

    @PostMapping("/relation/roles/{id}")
    @ApiOperation(value = "查询用户id查询已关联的角色列表")
    @ApiImplicitParam(paramType = "path", name = "id", value = "要查询的用户id", required = true, dataType = "Long")
    public ResponseEntity<List<ImsRole>> getRelationRolesById(@PathVariable("id") Long id){
        log.debug("查询用户id查询已关联的角色列表\t param:{}",id);

        return ResponseEntity.ok(imsUserService.getRelationRolesById(id));
    }

}
