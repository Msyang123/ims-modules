package com.lhiot.ims.healthygood.feign.customplan;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.healthygood.feign.customplan.entity.CustomOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author hufan created in 2018/12/8 19:34
 **/
@Component
@FeignClient(value = "healthy-good-service-v2-0")
public interface CustomOrderFeign {

    /**
     * 根据条件分页查询定制订单信息列表
     *
     * @param customOrder
     * @return
     */
    @PostMapping("/custom-orders/pages")
    ResponseEntity<Pages<CustomOrder>> search(@RequestBody CustomOrder customOrder);


    /**
     * 根据orderCode查询定制订单信息
     *
     * @param orderCode
     * @return
     */
    @GetMapping("/custom-orders/{orderCode}")
    ResponseEntity<CustomOrder> selectByCode(@PathVariable("orderCode") String orderCode);
}
