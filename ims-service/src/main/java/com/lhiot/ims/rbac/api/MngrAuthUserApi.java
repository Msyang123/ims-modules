package com.lhiot.ims.rbac.api;

import com.leon.microx.support.session.Sessions;
import com.leon.microx.support.swagger.ApiParamType;
import com.leon.microx.util.Maps;
import com.leon.microx.util.auditing.MD5;
import com.lhiot.ims.rbac.domain.MngrAuthUser;
import com.lhiot.ims.rbac.entity.Admin;
import com.lhiot.ims.rbac.entity.Status;
import com.lhiot.ims.rbac.model.AdminLogin;
import com.lhiot.ims.rbac.model.MngrAuthUserLogin;
import com.lhiot.ims.rbac.service.MngrAuthUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lhiot.ims.rbac.common.PagerResultObject;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.sql.Timestamp;
import java.time.Instant;
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
@RequestMapping("/mngrAuthUser")
public class MngrAuthUserApi {

    private final MngrAuthUserService mngrAuthUserService;

    private Sessions session;

    @Autowired
    public MngrAuthUserApi(MngrAuthUserService mngrAuthUserService) {
        this.mngrAuthUserService = mngrAuthUserService;
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
    @ApiImplicitParam(paramType = "path", name = "ids", value = "要删除用户的ids,逗号分割", required = true, dataType = "String")
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

    @Sessions.Uncheck
    @PostMapping("/login")
    @ApiOperation("管理员登录, 返回session用户")
//    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "登录参数", required = true, dataType = "AdminLogin")
    public ResponseEntity login(@RequestBody MngrAuthUserLogin login, @ApiIgnore HttpServletRequest request) {
        MngrAuthUser user = mngrAuthUserService.selectByAccount(login.getAccount());
        if (Objects.isNull(user)) {
            return ResponseEntity.badRequest().body("帐号不存在");
        }

        if (!Objects.equals(Status.AVAILABLE, user.getStatus())) {
            return ResponseEntity.badRequest().body("帐号状态异常：" + user.getStatus().getMessage());
        }

        String md5 = MD5.str(user.getId() + login.getPassword());
        if (!md5.equalsIgnoreCase(user.getPassword())) {
            return ResponseEntity.badRequest().body("密码错误");
        }

        Sessions.User sessionUser = Sessions.create(request).user(Maps.of("1", "leon")).timeToLive(30, TimeUnit.MINUTES);

        // TODO 填充访问权限：sessionUser.authorities(Authority.of("/**/users/?, RequestMethod.GET))

        String sessionId = session.cache(sessionUser);
        try {
            return ResponseEntity
                    .created(URI.create("/mngrAuthUser/login/" + sessionId))
                    .header(Sessions.HTTP_HEADER_NAME, sessionId)
                    .build();
        } finally {
            // 更新用户最后登录时间
            user.setLastLoginAt(new Timestamp(System.currentTimeMillis()));
            mngrAuthUserService.updateById(user);
        }
    }
    
}
