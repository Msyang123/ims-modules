package com.lhiot.ims.rbac.api;

import com.leon.microx.support.session.Authority;
import com.leon.microx.support.session.Sessions;
import com.leon.microx.support.swagger.ApiParamType;
import com.leon.microx.util.Maps;
import com.leon.microx.util.StringUtils;
import com.leon.microx.util.auditing.MD5;
import com.lhiot.ims.rbac.mapper.AdminMapper;
import com.lhiot.ims.rbac.po.Admin;
import com.lhiot.ims.rbac.po.Status;
import com.lhiot.ims.rbac.vo.AdminLogin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Leon (234239150@qq.com) created in 11:43 18.8.26
 */
@Api
@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminApi {

    private Sessions session;
    private AdminMapper adminMapper;

    public AdminApi(Sessions session, AdminMapper adminMapper) {
        this.session = session;
        this.adminMapper = adminMapper;
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
                .created(URI.create("/admin/session/" + sessionId))
                .header(Sessions.HTTP_HEADER_NAME, sessionId)
                .build();
    }

    @Sessions.Uncheck
    @PostMapping("/session")
    @ApiOperation("管理员登录, 返回session用户")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "登录参数", required = true, dataType = "AdminLogin")
    public ResponseEntity login(@RequestBody AdminLogin login, @ApiIgnore HttpServletRequest request) {
        Admin admin = adminMapper.selectByAccount(login.getAccount());
        if (Objects.isNull(admin)) {
            return ResponseEntity.badRequest().body("帐号不存在");
        }

        if (!Objects.equals(Status.AVAILABLE, admin.getStatus())) {
            return ResponseEntity.badRequest().body("帐号状态异常：" + admin.getStatus().getMessage());
        }

        String md5 = MD5.str(admin.getId() + login.getPassword());
        if (!md5.equalsIgnoreCase(admin.getPassword())) {
            return ResponseEntity.badRequest().body("密码错误");
        }

        Sessions.User sessionUser = Sessions.create(request).user(Maps.of("1", "leon")).timeToLive(30, TimeUnit.MINUTES);

        // TODO 填充访问权限......sessionUser.authorities(Authority.of("/**/users/?, RequestMethod.GET))

        String sessionId = session.cache(sessionUser);
        try {
            return ResponseEntity
                    .created(URI.create("/admin/session/" + sessionId))
                    .header(Sessions.HTTP_HEADER_NAME, sessionId)
                    .build();
        } finally {
            adminMapper.updateLastLogin(Maps.of("id", admin.getId(), "last_login_at", Instant.now()));
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
}
