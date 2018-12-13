package com.lhiot.ims.ordercenter.feign.entity;

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

    @ApiModelProperty(notes = "配送单编码", dataType = "String")
    private Long id;
    @ApiModelProperty(notes = "配送单编码", dataType = "String")
    private String deliverCode;

    /**
     * 订单id
     */
    @ApiModelProperty(notes = "配送单编码", dataType = "String")
    private Long orderId;

    /**
     * 创建时间
     */
    @ApiModelProperty(notes = "配送单编码", dataType = "String")
    private Date createTime;


    /**
     * 配送员姓名
     */
    @ApiModelProperty(notes = "配送单编码", dataType = "String")
    private String deliverName;

    /**
     * 配送员手机号
     */
    @ApiModelProperty(notes = "配送单编码", dataType = "String")
    private String deliverPhone;

    /**
     * 配送距离
     */
    @ApiModelProperty(notes = "配送单编码", dataType = "String")
    private Double distance;

    /**
     * 配送费
     */
    @ApiModelProperty(notes = "配送单编码", dataType = "String")
    private Integer fee;

    /**
     * 订单编码
     */
    @ApiModelProperty(notes = "配送单编码", dataType = "String")
    private String orderCode;

    /**
     * 配送方式 FENGNIAO-蜂鸟配送DADA-达达配送 OWN-自己配送
     */
    @ApiModelProperty(notes = "配送单编码", dataType = "String")
    private DeliverType deliverType;

    /**
     * 配送失败原因
     */
    private String failureCause;

    /**
     * 配送取消时间
     */
    private Date cancelTime;


    /**
     * 接单时间
     */
    private Date receiveTime;

    /**
     * 配送状态 UNRECEIVED-未接单 WAIT_GET-待取货 DELIVERING-配送中 DONE-配送完成 FAILURE-配送失败
     */
    private DeliverStatus deliverStatus;

    /**
     * 门店编码
     */
    private String storeCode;

    /**
     * 备注
     */
    private String remark;

    /**
     * 扩展数据
     */
    private String ext;

}
