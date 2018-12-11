package com.lhiot.ims.ordercenter.api;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.ordercenter.feign.OrderFeign;
import com.lhiot.ims.ordercenter.feign.model.BaseOrderParam;
import com.lhiot.ims.ordercenter.feign.model.OrderDetailResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hufan created in 2018/12/11 19:46
 **/
@Api(description = "基础订单接口")
@Slf4j
@RestController
public class OrderApi {
    private final OrderFeign orderFeign;

    public OrderApi(OrderFeign orderFeign) {
        this.orderFeign = orderFeign;
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
}