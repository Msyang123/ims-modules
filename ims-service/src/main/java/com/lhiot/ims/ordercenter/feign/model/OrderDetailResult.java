package com.lhiot.ims.ordercenter.feign.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lhiot.ims.ordercenter.feign.entity.DeliverNote;
import com.lhiot.ims.ordercenter.feign.entity.OrderFlow;
import com.lhiot.ims.ordercenter.feign.entity.OrderProduct;
import com.lhiot.ims.ordercenter.feign.entity.OrderStore;
import com.lhiot.ims.ordercenter.feign.type.AllowRefund;
import com.lhiot.ims.ordercenter.feign.type.OrderStatus;
import com.lhiot.ims.ordercenter.feign.type.OrderType;
import com.lhiot.ims.ordercenter.feign.type.ReceivingWay;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zhangfeng created in 2018/9/19 15:39
 **/
@Data
@ApiModel
public class OrderDetailResult {
    @ApiModelProperty(notes = "订单Id", dataType = "Long")
    private Long id;

    @ApiModelProperty(notes = "订单编码", dataType = "String")
    private String code;

    @ApiModelProperty(notes = "用户Id", dataType = "Long")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    @ApiModelProperty(notes = "用户手机号", dataType = "String")
    private String phone;

    @ApiModelProperty(notes = "应用类型", dataType = "String")
    private String applicationType;

    @ApiModelProperty(notes = "订单类型：NORMAL-普通订单,CUSTOM-定制订单,TEAM_BUY-团购订单", dataType = "OrderType")
    private OrderType orderType;

    @ApiModelProperty(notes = "提货方式 TO_THE_STORE-门店自提,TO_THE_HOME-送货上门", dataType = "ReceivingWay")
    private ReceivingWay receivingWay;

    @ApiModelProperty(notes = "订单总金额", dataType = "Integer")
    private Integer totalAmount;

    @ApiModelProperty(notes = "订单应付金额", dataType = "Integer")
    private Integer amountPayable;

    @ApiModelProperty(notes = "配送费", dataType = "Integer")
    private Integer deliveryAmount;

    @ApiModelProperty(notes = "优惠金额", dataType = "Integer")
    private Integer couponAmount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(notes = "海鼎备货时间", dataType = "Date")
    private Date hdStockAt;

    @ApiModelProperty(notes = "订单状态 WAIT_PAYMENT-待支付-WAIT_SEND_OUT-待出库-SEND_OUTING,WAIT_DISPATCHING-待配送,DISPATCHING-配送中," +
            " RECEIVED-已收货,RETURNING-退货中,RETURN_FAILURE-退款失败,ALREADY_RETURN-退货完成,FAILURE-已失效,FINISHED-完成", dataType = "OrderStatus")
    private OrderStatus status;

    @ApiModelProperty(notes = "收货人", dataType = "String")
    private String receiveUser;

    @ApiModelProperty(notes = "收货人联系方式", dataType = "String")
    private String contactPhone;

    @ApiModelProperty(notes = "收货地址：门店自提订单填写门店地址", dataType = "String")
    private String address;

    @ApiModelProperty(notes = "备注", dataType = "String")
    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(notes = "提货截止时间", dataType = "Date")
    private Date deliveryEndAt;

    @ApiModelProperty(notes = "海鼎的订单编码", dataType = "String")
    private String hdOrderCode;

    @ApiModelProperty(notes = "用户昵称", dataType = "String")
    private String nickname;

    @ApiModelProperty(notes = "配送时间段", dataType = "String")
    private String deliverAt;

    @ApiModelProperty(notes = "是否允许退款 YES-是 NO-否", dataType = "AllowRefund")
    private AllowRefund allowRefund = AllowRefund.YES;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(notes = "订单创建时间", dataType = "Date")
    private Date createAt;

    @ApiModelProperty(notes = "支付Id", dataType = "String")
    private String payId;

    @ApiModelProperty(notes = "订单商品", dataType = "java.util.list")
    private List<OrderProduct> orderProductList;

    @ApiModelProperty(notes = "订单门店信息", dataType = "OrderStore")
    private OrderStore orderStore;

    @ApiModelProperty(notes = "订单状态流水列表", dataType = "List")
    private List<OrderFlow> orderFlowList;

    @ApiModelProperty(notes = "配送信息", dataType = "DeliverNote")
    private DeliverNote deliverNote;

}
