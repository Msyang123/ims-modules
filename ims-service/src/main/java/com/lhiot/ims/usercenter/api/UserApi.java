package com.lhiot.ims.usercenter.api;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.rbac.aspect.LogCollection;
import com.lhiot.ims.usercenter.feign.UserFeign;
import com.lhiot.ims.usercenter.feign.model.QuerySearch;
import com.lhiot.ims.usercenter.feign.model.UserDetailResult;
import com.lhiot.ims.usercenter.feign.type.LockStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author hufan created in 2018/12/11 10:39
 **/
@Api(description = "基础用户管理")
@RestController
@Slf4j
public class UserApi {
    private final UserFeign userFeign;

    @Autowired
    public UserApi(UserFeign userFeign) {
        this.userFeign = userFeign;
    }

    @ApiOperation("后台管理分页查询用户列表")
    @PostMapping("/user/query/search")
    public ResponseEntity query(@RequestBody QuerySearch querySearch) {
        log.debug("后台管理分页查询用户列表\t param{}", querySearch);

        if (Objects.nonNull(querySearch.getRows()) && Objects.nonNull(querySearch.getPage())) {
            querySearch.setStartRow((querySearch.getPage() - 1) * querySearch.getRows());
        }
        ResponseEntity<Pages<UserDetailResult>> entity = userFeign.query(querySearch);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body("调用基础用户失败") : ResponseEntity.ok(entity.getBody());
    }

    @LogCollection
    @ApiOperation("修改用户锁状态")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "userId", value = "业务用户Id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = ApiParamType.QUERY, name = "lockStatus", value = "锁状态", dataTypeClass = LockStatus.class, required = true)
    })
    @PutMapping("/{userId}/locked-status")
    public ResponseEntity unlock(@PathVariable("userId") Long userId, @RequestParam("lockStatus") LockStatus lockStatus) {
        log.debug("修改用户锁状态\t userId{},param{}", userId, lockStatus);

        ResponseEntity entity = userFeign.unlock(userId, lockStatus);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok().build();
    }
}