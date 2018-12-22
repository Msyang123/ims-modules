package com.lhiot.ims.ordercenter.feign.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhiot.ims.ordercenter.feign.type.DeliverStatus;
import com.lhiot.ims.ordercenter.feign.type.DeliverType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 配送单信息实体类
 */
@Data
@ToString
public class DeliverNote {

    @ApiModelProperty(notes = "配送单id", dataType = "Long")
    private Long id;

    @ApiModelProperty(notes = "配送单编码", dataType = "String")
    private String deliverCode;

    @ApiModelProperty(notes = "订单id", dataType = "Long")
    private Long orderId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(notes = "配送单编码", dataType = "Date")
    private Date createTime;

    @ApiModelProperty(notes = "配送员姓名", dataType = "String")
    private String deliverName;

    @ApiModelProperty(notes = "配送员手机号", dataType = "String")
    private String deliverPhone;

    @ApiModelProperty(notes = "配送距离", dataType = "Double")
    private Double distance;

    @ApiModelProperty(notes = "配送费", dataType = "Integer")
    private Integer fee;

    @ApiModelProperty(notes = "订单编码", dataType = "String")
    private String orderCode;

    @ApiModelProperty(notes = "配送方式 FENGNIAO-蜂鸟配送DADA-达达配送 OWN-自己配送", dataType = "DeliverType")
    private DeliverType deliverType;

    @ApiModelProperty(notes = "配送失败原因", dataType = "String")
    private String failureCause;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(notes = "配送取消时间", dataType = "Date")
    private Date cancelTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(notes = "接单时间", dataType = "Date")
    private Date receiveTime;

    @ApiModelProperty(notes = "配送状态 UNRECEIVED-未接单 WAIT_GET-待取货 DELIVERING-配送中 DONE-配送完成 FAILURE-配送失败", dataType = "DeliverStatus")
    private DeliverStatus deliverStatus;

    @ApiModelProperty(notes = "门店编码", dataType = "String")
    private String storeCode;

    @ApiModelProperty(notes = "备注", dataType = "String")
    private String remark;

    @ApiModelProperty(notes = "扩展数据", dataType = "String")
    private String ext;

}
