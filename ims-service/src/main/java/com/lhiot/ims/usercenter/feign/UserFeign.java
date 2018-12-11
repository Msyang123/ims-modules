package com.lhiot.ims.usercenter.feign;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.usercenter.feign.model.QuerySearch;
import com.lhiot.ims.usercenter.feign.model.UserDetailResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author hufan created in 2018/12/11 10:33
 **/
@FeignClient(value = "basic-user-service-v1-0-hufan")
@Component
public interface UserFeign {

        /**
         * 后台管理分页查询用户列表
         *
         * @param querySearch
         * @return
         */
        @PostMapping("/users/query/search")
        ResponseEntity<Pages<UserDetailResult>> query(@RequestBody QuerySearch querySearch);
        /**
         * 解除用户锁定
         *
         * @param userId
         * @return
         */
        @PutMapping("/users/{userId}/unlocked")
        ResponseEntity unlock(@PathVariable("userId") Long userId);

}
