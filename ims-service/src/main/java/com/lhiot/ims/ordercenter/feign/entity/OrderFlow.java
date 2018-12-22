package com.lhiot.ims.ordercenter.feign.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhiot.ims.ordercenter.feign.type.OrderStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 订单操作流水记录
 */
@EqualsAndHashCode(callSuper = false)
@Data
@ApiModel
public class OrderFlow {
    @ApiModelProperty(notes = "订单留id", dataType = "Long")
    private Long id;

    @ApiModelProperty(notes = "订单id", dataType = "Long")
    private Long orderId;

    @ApiModelProperty(notes = "现在状态 AIT_PAYMENT-待支付,WAIT_SEND_OUT-待出库,SEND_OUTING-出库中,WAIT_DISPATCHING-待配送,DISPATCHING-配送中," +
            "RECEIVED-已收货,RETURNING-退货中,RETURN_FAILURE-退款失败，ALREADY_RETURN-退货完成，FAILURE-已失效，FINISHED-完成", dataType = "OrderStatus")
    private OrderStatus status;

    @ApiModelProperty(notes = "之前状态 AIT_PAYMENT-待支付,WAIT_SEND_OUT-待出库,SEND_OUTING-出库中,WAIT_DISPATCHING-待配送,DISPATCHING-配送中," +
            "RECEIVED-已收货,RETURNING-退货中,RETURN_FAILURE-退款失败，ALREADY_RETURN-退货完成，FAILURE-已失效，FINISHED-完成", dataType = "OrderStatus")
    private OrderStatus preStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(notes = "创建时间", dataType = "Date")
    private Date createAt;
}
