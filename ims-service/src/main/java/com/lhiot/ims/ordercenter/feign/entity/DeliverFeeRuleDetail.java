package com.lhiot.ims.ordercenter.feign.entity;

import com.lhiot.ims.ordercenter.feign.type.UpdateWay;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

/**
 * @author zhangfeng create in 11:50 2018/12/11
 */
@Data
@ApiModel
public class DeliverFeeRuleDetail {
    @Min(1)
    @ApiModelProperty(value = "配送规则详情id", dataType = "Long")
    private Long id;

    @Min(1)
    @ApiModelProperty(value = "配送规则id", dataType = "Long")
    private Long deliveryFeeRuleId;

    @Min(0)
    @ApiModelProperty(value = "最小配送距离", dataType = "Integer")
    private Integer minDistance;

    @ApiModelProperty(value = "最大配送距离", dataType = "Integer")
    private Integer maxDistance;

    @Min(0)
    @ApiModelProperty(value = "首重", dataType = "BigDecimal")
    private BigDecimal firstWeight;

    @ApiModelProperty(value = "首重费用", dataType = "Integer")
    private Integer firstFee;

    @ApiModelProperty(value = "续重", dataType = "BigDecimal")
    private BigDecimal additionalWeight;

    @ApiModelProperty(value = "续重费用", dataType = "Integer")
    private Integer additionalFee;

    @ApiModelProperty(value = "操作方式 INSERT(新增),UPDATE(修改)", dataType = "UpdateWay")
    private UpdateWay updateWay;
}
