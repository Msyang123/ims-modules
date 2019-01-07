package com.lhiot.ims.ordercenter.feign.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhiot.ims.ordercenter.feign.entity.DeliverFeeRuleDetail;
import com.lhiot.ims.ordercenter.feign.type.DeliverAtType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author zhangfeng create in 12:04 2018/12/11
 */
@Data
@ApiModel
public class DeliverFeeRuleParam {
    @ApiModelProperty(value = "配送规则id", dataType = "Long")
    private Long id;

    @Min(0)
    @ApiModelProperty(value = "最低配送金额", dataType = "Integer")
    private Integer minOrderAmount;

    @Min(1)
    @ApiModelProperty(value = "最高配送金额", dataType = "Integer")
    private Integer maxOrderAmount;

    @NotNull
    @ApiModelProperty(value = "配送时间段类型 DAYTIME(8:00,18:00), EVENING(18:00,22:00),ALL_DAY(8:00,22:00)", dataType = "DeliverAtType")
    private DeliverAtType deliveryAtType;

    @ApiModelProperty(value = "创建人", dataType = "String")
    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateAt;

    @ApiModelProperty(value = "删除的配送规格详情ids", dataType = "List")
    private List<String> deleteIds;

    @ApiModelProperty(value = "配送费规格详情", dataType = "List")
    private List<DeliverFeeRuleDetail> detailList;

}
