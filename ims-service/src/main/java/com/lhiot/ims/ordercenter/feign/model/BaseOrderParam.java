package com.lhiot.ims.ordercenter.feign.model;

import com.lhiot.ims.ordercenter.feign.type.OrderStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiaojian  created in  2018/12/8 11:28
 */
@ApiModel
@Data
public class BaseOrderParam {
    @ApiModelProperty(notes = "用户ID(多个以英文逗号分隔)", dataType = "String")
    private String userIds;

    @ApiModelProperty(notes = "订单类型", dataType = "String")
    private String orderType;

    @ApiModelProperty(notes = "订单状态 WAIT_PAYMENT-待支付,WAIT_SEND_OUT-待出库,SEND_OUTING-出库中,WAIT_DISPATCHING-待配送,DISPATCHING-配送中," +
            "RECEIVED-已收货,RETURNING-退货中,RETURN_FAILURE-退款失败,ALREADY_RETURN-退货完成,FAILURE-已失效,FINISHED-完成", dataType = "OrderStatus")
    private OrderStatus orderStatus;

    @ApiModelProperty(notes = "每页查询条数(为空或0不分页查所有)", dataType = "Integer")
    private Integer rows;

    @ApiModelProperty(notes = "当前页", dataType = "Integer")
    private Integer page;
}
