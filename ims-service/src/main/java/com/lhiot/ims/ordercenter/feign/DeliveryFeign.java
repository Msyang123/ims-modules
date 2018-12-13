package com.lhiot.ims.ordercenter.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author hufan created in 2018/12/13 12:06
 **/
@FeignClient(value = "base-order-service-v1-0")
@Component
public interface DeliveryFeign {
}
