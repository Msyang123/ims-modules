package com.lhiot.ims.healthygood.api.customplan;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tips;
import com.leon.microx.web.swagger.ApiHideBodyProperty;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.datacenter.util.FeginResponseTools;
import com.lhiot.ims.healthygood.feign.customplan.CustomOrderFeign;
import com.lhiot.ims.healthygood.feign.customplan.entity.CustomOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2018/12/8 19:37
 **/
@Api(description = "定制计划订单")
@Slf4j
@RestController
public class CustomOrderApi {

    private final CustomOrderFeign customOrderFeign;

    public CustomOrderApi(CustomOrderFeign customOrderFeign) {
        this.customOrderFeign = customOrderFeign;
    }

    @GetMapping("/custom-orders/{orderCode}")
    @ApiOperation(value = "根据orderCode查询定制订单信息",response = CustomOrder.class)
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "orderCode", value = "定制订单id", dataType = "String", required = true)
    @ApiHideBodyProperty({"user","customOrderDeliveryList","customPlan"})
    public ResponseEntity<Tips> selectByCode(@PathVariable("orderCode") String orderCode) {
        log.debug("根据orderCode查询定制订单信息\t param:{}", orderCode);

        ResponseEntity<CustomOrder> entity = customOrderFeign.selectByCode(orderCode);
        Tips<CustomOrder> tips = FeginResponseTools.convertResponse(entity);
        return  FeginResponseTools.returnTipsResponse(tips);
    }

    @PostMapping("/custom-orders/pages")
    @ApiOperation(value = "根据条件分页查询定制订单信息列表", response = CustomOrder.class, responseContainer = "Set")
    @ApiHideBodyProperty({"user","customOrderDeliveryList","customPlan"})
    public ResponseEntity<Tips> search(@RequestBody CustomOrder customOrder) {
        log.debug("根据条件分页查询定制订单信息列表\t param:{}", customOrder);

        ResponseEntity<Pages<CustomOrder>> entity = customOrderFeign.search(customOrder);
        Tips<Pages<CustomOrder>> tips = FeginResponseTools.convertResponse(entity);
        return FeginResponseTools.returnTipsResponse(tips);
    }
}