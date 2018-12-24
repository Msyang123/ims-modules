package com.lhiot.ims.healthygood.feign.customplan.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lhiot.ims.healthygood.feign.customplan.type.CustomOrderDeliveryStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString(callSuper = true)
@ApiModel(description = "个人购买定制计划领取记录")
public class CustomOrderDelivery {

    @ApiModelProperty(hidden = true)
    private Long id;

    @JsonProperty("shelfId")
    @ApiModelProperty(dataType = "Long", hidden = true, notes = "商品上架id")
    private Long productShelfId;

    @ApiModelProperty(value = "创建时间", dataType = "Date", hidden = true, example = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt;

    @ApiModelProperty(value = "要求配送时间", dataType = "String", hidden = true)
    private String deliveryTime;

    @ApiModelProperty(notes = "要求配送地址", dataType = "String", hidden = true)
    private String deliveryAddress;

    @ApiModelProperty(value = "提取的定制订单状态", dataType = "CustomOrderDeliveryStatus")
    private CustomOrderDeliveryStatus deliveryStatus;

    @ApiModelProperty(notes = "收货时间", dataType = "Date", example = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date recevingTime;

    @ApiModelProperty(notes = "订单编码", dataType = "String")
    private String orderCode;

    @ApiModelProperty(notes = "购买的定制计划订单id", dataType = "Long")
    private Long customOrderId;

    @ApiModelProperty(notes = "购买的定制计划商品id", dataType = "Long")
    private Long customPlanProductId;

    @ApiModelProperty(notes = "定制商品名称", dataType = "String")
    private String productName;

    @ApiModelProperty(notes = "定制商品上架图", dataType = "String")
    private String image;

    @ApiModelProperty(notes = "定制计划id", dataType = "Long")
    private Long planId;

    @ApiModelProperty(notes = "第x天", dataType = "Integer")
    private Integer dayOfPeriod;

    @ApiModelProperty(notes = "备注", dataType = "String")
    private String remark;
}
