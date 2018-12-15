package com.lhiot.ims.ordercenter.feign;

import com.lhiot.ims.ordercenter.feign.entity.DeliverNote;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author hufan created in 2018/12/13 12:06
 **/
@FeignClient(value = "delivery-service-v1-0")
@Component
public interface DeliveryFeign {
    /**
     * 查询配送单详情
     *
     * @param code 配送单编码 为海鼎订单编码
     * @return
     */
    @GetMapping("/delivery-notes/{code}")
    ResponseEntity<DeliverNote> localDetail(@PathVariable("code") String code);
}
