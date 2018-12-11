package com.lhiot.ims.ordercenter.feign;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.ordercenter.feign.model.BaseOrderParam;
import com.lhiot.ims.ordercenter.feign.model.OrderDetailResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author hufan created in 2018/12/11 19:38
 **/
@FeignClient(value = "basic-user-service-v1-0")
@Component
public interface OrderFeign {

    /**
     * 根据条件分页获取基础订单列表
     * @param param
     * @return
     */
    @PostMapping("/orders/pages")
    ResponseEntity<Pages<OrderDetailResult>> search(@RequestBody BaseOrderParam param);
}
