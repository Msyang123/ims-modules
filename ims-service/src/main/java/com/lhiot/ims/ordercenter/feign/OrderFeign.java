package com.lhiot.ims.ordercenter.feign;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.ordercenter.feign.model.BaseOrderParam;
import com.lhiot.ims.ordercenter.feign.model.OrderDetailResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2018/12/11 19:38
 **/
@FeignClient(value = "base-order-service-v1-0")
@Component
public interface OrderFeign {

    /**
     * 根据条件分页获取基础订单列表
     *
     * @param param
     * @return
     */
    @PostMapping("/orders/pages")
    ResponseEntity<Pages<OrderDetailResult>> search(@RequestBody BaseOrderParam param);

    /**
     * 根据订单code查询订单详情
     *
     * @param orderCode
     * @param needProductList
     * @param needOrderFlowList
     * @return
     */
    @GetMapping("/orders/{orderCode}")
    ResponseEntity<OrderDetailResult> orderDetail(@PathVariable("orderCode") String orderCode, @RequestParam("needProductList") boolean needProductList,
                                                  @RequestParam("needOrderFlowList") boolean needOrderFlowList);
}
