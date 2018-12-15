package com.lhiot.ims.ordercenter.api;

import com.leon.microx.util.StringUtils;
import com.leon.microx.web.result.Pages;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.datacenter.feign.ProductShelfFegin;
import com.lhiot.ims.datacenter.feign.entity.ProductShelf;
import com.lhiot.ims.datacenter.feign.entity.ProductSpecification;
import com.lhiot.ims.datacenter.feign.model.ProductShelfParam;
import com.lhiot.ims.ordercenter.feign.DeliveryFeign;
import com.lhiot.ims.ordercenter.feign.OrderFeign;
import com.lhiot.ims.ordercenter.feign.entity.DeliverNote;
import com.lhiot.ims.ordercenter.feign.entity.OrderProduct;
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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author hufan created in 2018/12/11 19:46
 **/
@Api(description = "基础订单接口")
@Slf4j
@RestController
public class OrderApi {
    private final OrderFeign orderFeign;
    private final UserFeign userFeign;
    private final DeliveryFeign deliveryFeign;
    private final ProductShelfFegin productShelfFegin;

    public OrderApi(OrderFeign orderFeign, UserFeign userFeign, DeliveryFeign deliveryFeign, ProductShelfFegin productShelfFegin) {
        this.orderFeign = orderFeign;
        this.userFeign = userFeign;
        this.deliveryFeign = deliveryFeign;
        this.productShelfFegin = productShelfFegin;
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
    @ApiImplicitParam(paramType = "path", name = "orderCode", dataType = "String", required = true, value = "订单code")
    @GetMapping("/orders/{orderCode}")
    public ResponseEntity orderDetail(@PathVariable("orderCode") String orderCode) {
        log.debug("根据订单code查询订单详情\t param:{}", orderCode);

        ResponseEntity<OrderDetailResult> entity = orderFeign.orderDetail(orderCode, true, false);
        if (entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        OrderDetailResult orderDetailResult = entity.getBody();
        Long userId = orderDetailResult.getUserId();
        // 用户信息
        if (Objects.nonNull(userId)) {
            ResponseEntity<UserDetailResult> userEntity = userFeign.findById(userId);
            if (userEntity.getStatusCode().isError()) {
                return ResponseEntity.badRequest().body(userEntity.getBody());
            } else if (Objects.nonNull(userEntity.getBody())) {
                orderDetailResult.setPhone(userEntity.getBody().getPhone());
            }
        }
        // 配送信息
        String hdOrderCode = orderDetailResult.getHdOrderCode();
        ResponseEntity<DeliverNote> deliverNoteEntity = deliveryFeign.localDetail(hdOrderCode);
        if (deliverNoteEntity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(deliverNoteEntity.getBody());
        }
        if (Objects.nonNull(deliverNoteEntity.getBody())) {
            orderDetailResult.setDeliverNote(deliverNoteEntity.getBody());
        }
        // 上架规格完善
        List<OrderProduct> orderProductList = orderDetailResult.getOrderProductList();
        if (!orderProductList.isEmpty() || orderProductList.size() != 0) {
            List<Long> shelfIdList = orderProductList.stream().filter(orderProduct -> Objects.nonNull(orderProduct.getShelfId())).map(OrderProduct::getShelfId).collect(Collectors.toList());
            ProductShelfParam productShelfParam = new ProductShelfParam();
            productShelfParam.setIds(StringUtils.collectionToDelimitedString(shelfIdList, ","));
            productShelfParam.setIncludeProduct(true);
            ResponseEntity<Pages<ProductShelf>> pages = productShelfFegin.pages(productShelfParam);
            if (pages.getStatusCode().isError()) {
                return ResponseEntity.badRequest().body(pages.getBody());
            }
            List<ProductShelf> productShelfList = pages.getBody().getArray();
            if (!productShelfList.isEmpty() || productShelfList.size() != 0) {
                orderProductList.forEach(orderProduct -> {
                    productShelfList.forEach(productShelf -> {
                        if (Objects.equals(orderProduct.getShelfId(), productShelf.getId())) {
                            ProductSpecification productSpecification = productShelf.getProductSpecification();
                            String shelfSpecification = productSpecification.getWeight() + "*" + productSpecification.getSpecificationQty() + productSpecification.getPackagingUnit();
                            orderProduct.setShelfSpecification(shelfSpecification);
                        }
                    });
                });
            }
        }
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }
}