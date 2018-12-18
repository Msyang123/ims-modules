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
     * @param param 分页查询条件
     * @return 分页查询结果
     */
    @PostMapping("/orders/pages")
    ResponseEntity<Pages<OrderDetailResult>> search(@RequestBody BaseOrderParam param);

    /**
     * 根据订单code查询订单详情
     *
     * @param orderCode         订单code
     * @param needProductList   是否需要加载商品信息
     * @param needOrderFlowList 是否需要订单流信息
     * @return 查询结果
     */
    @GetMapping("/orders/{orderCode}")
    ResponseEntity<OrderDetailResult> orderDetail(@PathVariable("orderCode") String orderCode, @RequestParam("needProductList") boolean needProductList,
                                                  @RequestParam("needOrderFlowList") boolean needOrderFlowList);

    /**
     * 海鼎订单调货
     *
     * @param orderCode     订单code
     * @param storeId       门店id
     * @param operationUser 操作用户
     * @return 海鼎订单调货结果
     */
    @PutMapping("/orders/{orderCode}/store")
    ResponseEntity modifyStoreInOrder(@PathVariable("orderCode") String orderCode, @RequestParam("storeId") Long storeId, @RequestParam("operationUser") String operationUser);
}
