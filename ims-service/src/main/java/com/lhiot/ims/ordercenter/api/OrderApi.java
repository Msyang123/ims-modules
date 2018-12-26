package com.lhiot.ims.ordercenter.api;

import com.leon.microx.util.Jackson;
import com.leon.microx.util.StringUtils;
import com.leon.microx.web.result.Pages;
import com.leon.microx.web.session.Sessions;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.datacenter.feign.ProductShelfFeign;
import com.lhiot.ims.datacenter.feign.entity.ProductShelf;
import com.lhiot.ims.datacenter.feign.entity.ProductSpecification;
import com.lhiot.ims.datacenter.feign.model.ProductShelfParam;
import com.lhiot.ims.ordercenter.feign.DeliveryFeign;
import com.lhiot.ims.ordercenter.feign.OrderFeign;
import com.lhiot.ims.ordercenter.feign.entity.DeliverNote;
import com.lhiot.ims.ordercenter.feign.entity.OrderProduct;
import com.lhiot.ims.ordercenter.feign.model.BaseOrderParam;
import com.lhiot.ims.ordercenter.feign.model.DeliverTime;
import com.lhiot.ims.ordercenter.feign.model.OrderDetailResult;
import com.lhiot.ims.usercenter.feign.UserFeign;
import com.lhiot.ims.usercenter.feign.model.UserDetailResult;
import com.lhiot.util.FeginResponseTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    private final ProductShelfFeign productShelfFeign;

    @Autowired
    public OrderApi(OrderFeign orderFeign, UserFeign userFeign, DeliveryFeign deliveryFeign, ProductShelfFeign productShelfFeign) {
        this.orderFeign = orderFeign;
        this.userFeign = userFeign;
        this.deliveryFeign = deliveryFeign;
        this.productShelfFeign = productShelfFeign;
    }

    @ApiOperation(value = "根据条件分页获取订单列表", response = OrderDetailResult.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "查询条件", dataType = "BaseOrderParam")
    @PostMapping("/orders/pages")
    public ResponseEntity search(@RequestBody BaseOrderParam param) {
        log.debug("获取订单列表\t param:{}", param);

        param.setOrderCode(param.getCode());
        param.setPhone(param.getUserPhone());
        ResponseEntity entity = orderFeign.search(param);
        return FeginResponseTools.convertNoramlResponse(entity);
    }

    @ApiOperation(value = "根据订单code查询订单详情", response = OrderDetailResult.class)
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "orderCode", dataType = "String", required = true, value = "订单code")
    @GetMapping("/orders/{orderCode}")
    public ResponseEntity orderDetail(@PathVariable("orderCode") String orderCode) {
        log.debug("根据订单code查询订单详情\t param:{}", orderCode);

        OrderDetailResult orderDetailResult = new OrderDetailResult();
        ResponseEntity entity = orderFeign.orderDetail(orderCode, true, false);
        if (entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        } else if (Objects.isNull(entity.getBody())){
            return ResponseEntity.ok(orderDetailResult);
        }
        orderDetailResult = (OrderDetailResult)entity.getBody();
        // 自动配送解析配送时间
        String deliveryAt = orderDetailResult.getDeliverAt();
        DeliverTime deliverTime =Jackson.object(deliveryAt, DeliverTime.class);
        SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2End = new SimpleDateFormat("HH:mm:ss");
        Date startTime = deliverTime.getStartTime();
        Date endTime = deliverTime.getEndTime();
        orderDetailResult.setDeliverAt(sdfStart.format(startTime) + "-" + sdf2End.format(endTime));

        Long userId = orderDetailResult.getUserId();
        // 用户信息
        if (Objects.nonNull(userId)) {
            ResponseEntity userEntity = userFeign.findById(userId);
            if (userEntity.getStatusCode().isError()) {
                return ResponseEntity.badRequest().body((UserDetailResult)userEntity.getBody());
            } else if (Objects.nonNull(userEntity.getBody())) {
                UserDetailResult userDetailResult = (UserDetailResult) userEntity.getBody();
                orderDetailResult.setUserPhone(userDetailResult.getPhone());
            }
        }
        // 配送信息
        String hdOrderCode = orderDetailResult.getHdOrderCode();
        ResponseEntity deliverNoteEntity = deliveryFeign.localDetail(hdOrderCode);
        if (deliverNoteEntity.getStatusCode().isError() || Objects.isNull(deliverNoteEntity.getBody()) || Objects.equals("该配送单不存在", deliverNoteEntity.getBody())) {
            orderDetailResult.setDeliverNote(new DeliverNote());
        }else if (Objects.nonNull(deliverNoteEntity.getBody())) {
            orderDetailResult.setDeliverNote((DeliverNote)deliverNoteEntity.getBody());
        }
        // 上架规格完善
        List<OrderProduct> orderProductList = orderDetailResult.getOrderProductList();
        if (!CollectionUtils.isEmpty(orderProductList)) {
            List<Long> shelfIdList = orderProductList.stream().filter(orderProduct -> Objects.nonNull(orderProduct.getShelfId())).map(OrderProduct::getShelfId).collect(Collectors.toList());
            ProductShelfParam productShelfParam = new ProductShelfParam();
            productShelfParam.setIds(StringUtils.collectionToDelimitedString(shelfIdList, ","));
            productShelfParam.setIncludeProduct(true);
            ResponseEntity<Pages<ProductShelf>> pages = productShelfFeign.pages(productShelfParam);
            if (pages.getStatusCode().isError()) {
                return ResponseEntity.badRequest().body(pages.getBody());
            }
            List<ProductShelf> productShelfList = pages.getBody().getArray();
            if (!CollectionUtils.isEmpty(productShelfList)) {
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
        return ResponseEntity.ok(entity.getBody());
    }

    @ApiOperation(value = "海鼎订单调货", response = ResponseEntity.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "orderCode", value = "调货订单编码", dataType = "String", required = true),
            @ApiImplicitParam(paramType = ApiParamType.QUERY, name = "storeId", value = "调货目标门店id", dataType = "Long", required = true)
    })
    @PutMapping("/orders/{orderCode}/store")
    public ResponseEntity modifyStoreInOrder(@PathVariable("orderCode") String orderCode, @RequestParam("storeId") Long storeId, Sessions.User user) {
        log.debug("海鼎订单调货\t param:{}", orderCode);

        String operationUser = (String) user.getUser().get("name");
        ResponseEntity entity = orderFeign.modifyStoreInOrder(orderCode, storeId, operationUser);
        return FeginResponseTools.convertNoramlResponse(entity);
    }
}