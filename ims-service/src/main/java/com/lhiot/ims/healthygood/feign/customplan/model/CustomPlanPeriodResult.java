package com.lhiot.ims.healthygood.feign.customplan.model;

import com.lhiot.ims.healthygood.feign.customplan.entity.CustomPlanSpecification;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@ApiModel
@NoArgsConstructor
/**
 * 周期类型
 */
public class CustomPlanPeriodResult {
    private  Integer index;

    @ApiModelProperty(value = "定制计划周期（7-周 30-月）", dataType = "Integer")
    private Integer planPeriod;


    @ApiModelProperty(value = "定制规格集合", dataType = "List")
    private List<CustomPlanSpecification> specificationList;

    @ApiModelProperty(value = "定制商品集合", dataType = "List")
    private List<CustomPlanProductResult> products;

}
