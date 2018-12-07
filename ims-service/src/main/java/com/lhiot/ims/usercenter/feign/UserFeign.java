package com.lhiot.ims.usercenter.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author hufan created in 2018/12/7 12:18
 **/
@FeignClient(value = "basic-user-service-v1-0")
@Component
public interface UserFeign {




    /**
     * 后台管理分页查询用户列表
     * @param param
     * @return
     */
//    @PostMapping("/users/query/search")
//    ResponseEntity<Pages<UserDetailResult>> search(@RequestBody SearchParam param);
}
