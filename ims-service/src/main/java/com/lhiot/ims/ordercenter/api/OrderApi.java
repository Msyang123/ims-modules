package com.lhiot.ims.ordercenter.api;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.ordercenter.feign.OrderFeign;
import com.lhiot.ims.ordercenter.feign.model.BaseOrderParam;
import com.lhiot.ims.ordercenter.feign.model.OrderDetailResult;
import com.lhiot.ims.usercenter.feign.UserFeign;
import com.lhiot.ims.usercenter.feign.model.UserDetailResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author hufan created in 2018/12/11 19:46
 **/
@Api(description = "基础订单接口")
@Slf4j
@RestController
public class OrderApi {
    private final OrderFeign orderFeign;
    private final UserFeign userFeign;

    public OrderApi(OrderFeign orderFeign, UserFeign userFeign) {
        this.orderFeign = orderFeign;
        this.userFeign = userFeign;
    }


    @ApiOperation(value = "根据条件分页获取订单列表", response = OrderDetailResult.class, responseContainer = "Set")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "查询条件", dataType = "BaseOrderParam")
    })
    @PostMapping("/orders/pages")
    public ResponseEntity search(@RequestBody BaseOrderParam param) {
        log.debug("获取订单列表\t param:{}", param);

        ResponseEntity<Pages<OrderDetailResult>> entity = orderFeign.search(param);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }

    @ApiOperation(value = "根据订单code查询订单详情", response = OrderDetailResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "orderCode", dataType = "String", required = true, value = "订单code"),
            @ApiImplicitParam(paramType = "query", name = "needProductList", dataType = "boolean", required = true, value = "是否需要加载商品信息"),
            @ApiImplicitParam(paramType = "query", name = "needOrderFlowList", dataType = "boolean", required = true, value = "是否需要加载订单操作流水信息")
    })
    @GetMapping("/orders/{orderCode}")
    public ResponseEntity orderDetail(@PathVariable("orderCode") String orderCode, @RequestParam("needProductList") boolean needProductList,
                                      @RequestParam("needOrderFlowList") boolean needOrderFlowList) {
        log.debug("根据订单code查询订单详情\t param:{}", orderCode, needProductList, needOrderFlowList);

        ResponseEntity<OrderDetailResult> entity = orderFeign.orderDetail(orderCode, needProductList, needOrderFlowList);
        if(entity.getStatusCode().isError()){
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        OrderDetailResult orderDetailResult = entity.getBody();
        Long userId = orderDetailResult.getUserId();
        // 用户信息
        if (Objects.nonNull(userId)) {
            ResponseEntity<UserDetailResult> userEntity = userFeign.findById(userId);
            if (Objects.isNull(userEntity.getBody()) || userEntity.getStatusCode().isError()) {
                return ResponseEntity.badRequest().body(userEntity.getBody());
            }
            orderDetailResult.setPhone(userEntity.getBody().getPhone());
        }
        // 配送信息 TODO


        // 上架规格完善 TODO

        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }
}