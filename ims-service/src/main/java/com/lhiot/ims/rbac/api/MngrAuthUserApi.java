package com.lhiot.ims.rbac.api;

import com.leon.microx.support.http.RemoteInvoker;
import com.leon.microx.support.session.Authority;
import com.leon.microx.support.session.Sessions;
import com.leon.microx.support.swagger.ApiParamType;
import com.leon.microx.util.Maps;
import com.leon.microx.util.StringUtils;
import com.leon.microx.util.auditing.MD5;
import com.lhiot.ims.rbac.domain.MngrAuthUser;
import com.lhiot.ims.rbac.entity.Status;
import com.lhiot.ims.rbac.domain.AdminLogin;
import com.lhiot.ims.rbac.service.MngrAuthUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lhiot.ims.rbac.common.PagerResultObject;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
* Description:用户接口类
* @author yijun
* @date 2018/09/29
*/
@Api(description = "用户接口")
@Slf4j
@RestController
@RequestMapping("/mngr-auth-user")
public class MngrAuthUserApi {

    private RemoteInvoker invoker;
    private Sessions session;
    private final MngrAuthUserService mngrAuthUserService;

    @Autowired
    public MngrAuthUserApi(RemoteInvoker invoker, Sessions session, MngrAuthUserService mngrAuthUserService) {
        this.invoker = invoker;
        this.session = session;
        this.mngrAuthUserService = mngrAuthUserService;
    }

    @Sessions.Uncheck
    @PostMapping("/backdoor")
    @ApiOperation("管理后门。（绕开所有权限）")
    public ResponseEntity backdoor(HttpServletRequest request) {
        Sessions.User sessionUser = Sessions.create(request).user(Maps.of("1", "leon"))
                .timeToLive(30, TimeUnit.MINUTES)
                .authorities(Authority.of("/**", RequestMethod.values())); // 具有所有API访问权限
        String sessionId = session.cache(sessionUser);
        return ResponseEntity
                .created(URI.create("/mngr-auth-user/session/" + sessionId))
                .header(Sessions.HTTP_HEADER_NAME, sessionId)
                .build();
    }

    @Sessions.Uncheck
    @PostMapping("/session")
    @ApiOperation("管理员登录, 返回session用户")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "login", value = "登录参数", required = true, dataType = "AdminLogin")
    public ResponseEntity login(@RequestBody AdminLogin login, @ApiIgnore HttpServletRequest request) {

        //接口请求例子
//         ResponseEntity<String> response = invoker.server("user-center")
//                 .uriVariables(Maps.of("id", "111"))
//                 .addHeaders(Pair.of("version", "v1-1-1"))
//                 .body(Collections.emptyMap())
//                 .request("/user/session/{id}", HttpMethod.PUT, String.class);

        MngrAuthUser admin = mngrAuthUserService.selectByAccount(login.getAccount());
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

        Sessions.User sessionUser = Sessions.create(request).user(Maps.of("1", "leon")).timeToLive(30, TimeUnit.MINUTES);

        // TODO 填充访问权限：sessionUser.authorities(Authority.of("/**/users/?, RequestMethod.GET))
        sessionUser.authorities(Authority.of("/**/mngr-auth-menu/**", RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT));

        String sessionId = session.cache(sessionUser);
        try {
            return ResponseEntity
                    .created(URI.create("/mngr-auth-user/session/" + sessionId))
                    .header(Sessions.HTTP_HEADER_NAME, sessionId)
                    .build();
        } finally {
            //adminMapper.updateLastLogin(Maps.of("id", admin.getId(), "last_login_at", Instant.now()));
        }
    }

    @DeleteMapping("/session")
    @ApiOperation("管理员退出登录")
    public ResponseEntity logout(HttpServletRequest request, Sessions.User user) {
        String sessionId = Sessions.id(request);
        if (StringUtils.hasLength(sessionId)) {
            session.invalidate(sessionId);
            log.info("User {} Logout.", user.toString());
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/create")
    @ApiOperation(value = "添加用户")
    @ApiImplicitParam(paramType = "body", name = "mngrAuthUser", value = "要添加的用户", required = true, dataType = "MngrAuthUser")
    public ResponseEntity<Integer> create(@RequestBody MngrAuthUser mngrAuthUser) {
        log.debug("添加用户\t param:{}",mngrAuthUser);
        
        return ResponseEntity.ok(mngrAuthUserService.create(mngrAuthUser));
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "根据id更新用户")
    @ApiImplicitParam(paramType = "body", name = "mngrAuthUser", value = "要更新的用户", required = true, dataType = "MngrAuthUser")
    public ResponseEntity<Integer> update(@PathVariable("id") Long id,@RequestBody MngrAuthUser mngrAuthUser) {
        log.debug("根据id更新用户\t id:{} param:{}",id,mngrAuthUser);
        mngrAuthUser.setId(id);
        
        return ResponseEntity.ok(mngrAuthUserService.updateById(mngrAuthUser));
    }

    @DeleteMapping("/{ids}")
    @ApiOperation(value = "根据ids删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.HEADER, name = Sessions.HTTP_HEADER_NAME, value = "session-id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "path", name = "ids", value = "要删除用户的ids,逗号分割", required = true, dataType = "String")
    })

    public ResponseEntity<Integer> deleteByIds(@PathVariable("ids") String ids) {
        log.debug("根据ids删除用户\t param:{}",ids);
        
        return ResponseEntity.ok(mngrAuthUserService.deleteByIds(ids));
    }
    
    @ApiOperation(value = "根据id查询用户", notes = "根据id查询用户")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键id", required = true, dataType = "Long")
    @GetMapping("/{id}")
    public ResponseEntity<MngrAuthUser> findMngrAuthUser(@PathVariable("id") Long id) {

        return ResponseEntity.ok(mngrAuthUserService.selectById(id));
    }
    
    @GetMapping("/page/query")
    @ApiOperation(value = "查询用户分页列表")
    public ResponseEntity<PagerResultObject<MngrAuthUser>> pageQuery(MngrAuthUser mngrAuthUser){
        log.debug("查询用户分页列表\t param:{}",mngrAuthUser);
        
        return ResponseEntity.ok(mngrAuthUserService.pageList(mngrAuthUser));
    }
    
}
