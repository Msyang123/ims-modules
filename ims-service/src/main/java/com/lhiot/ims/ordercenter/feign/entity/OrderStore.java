package com.lhiot.ims.ordercenter.feign.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.Instant;
import java.util.Date;

/**
 * @author zhangfeng created in 2018/9/18 15:29
 **/
@Data
public class OrderStore {
    @ApiModelProperty(notes = "海鼎编码", dataType = "String hdOrderCode")
    private String hdOrderCode;

    @ApiModelProperty(notes = "订单id", dataType = "Long")
    private Long orderId;

    @ApiModelProperty(notes = "门店id", dataType = "Long")
    private Long storeId;

    @ApiModelProperty(notes = "门店编码", dataType = "String")
    private String storeCode;

    @ApiModelProperty(notes = "门店名称", dataType = "String")
    private String storeName;

    @ApiModelProperty(notes = "操作用户", dataType = "String")
    private String operationUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(notes = "创建时间", dataType = "Date")
    private Date createAt = Date.from(Instant.now());
}
