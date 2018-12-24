package com.lhiot.ims.usercenter.feign;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.usercenter.feign.model.QuerySearch;
import com.lhiot.ims.usercenter.feign.model.UserDetailResult;
import com.lhiot.ims.usercenter.feign.type.LockStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2018/12/11 10:33
 **/
@FeignClient(value = "basic-user-service-v1-0-hufan")
@Component
public interface UserFeign {

    /**
     * 后台管理分页查询用户列表
     *
     * @param querySearch 分页查询条件
     * @return 分页查询结果
     */
    @PostMapping("/users/query/search")
    ResponseEntity<Pages<UserDetailResult>> query(@RequestBody QuerySearch querySearch);

    /**
     * 解除用户锁定
     *
     * @param userId     用户id
     * @param lockStatus 解锁状态
     * @return 解锁结果
     */
    @PutMapping("/users/{userId}/locked-status")
    ResponseEntity unlock(@PathVariable("userId") Long userId, @RequestParam("lockStatus") LockStatus lockStatus);

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户id
     * @return 查询结果
     */
    @GetMapping("/users/{id}")
    ResponseEntity<UserDetailResult> findById(@PathVariable("id") Long userId);
}
