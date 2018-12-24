package com.lhiot.ims.healthygood.feign.customplan.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Description:定制计划规格实体类
 *
 * @author zhangs
 * @date 2018/11/22
 */
@Data
@ToString(callSuper = true)
@ApiModel
@NoArgsConstructor
public class CustomPlanSpecification {

    private Integer index;

    @ApiModelProperty(value = "id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "定制计划id", dataType = "Long")
    private Long planId;

    @ApiModelProperty(value = "价格", dataType = "Integer")
    private Integer price;

    @ApiModelProperty(value = "数量", dataType = "Integer")
    private Integer quantity;

    @ApiModelProperty(value = "描述", dataType = "String")
    private String description;

    @ApiModelProperty(value = "定制计划周期（7-周 30-月）", dataType = "Integer")
    private Integer planPeriod;

    @ApiModelProperty(value = "定制说明配图", dataType = "String")
    private String image;

    @ApiModelProperty(value = "定制规格基础id", dataType = "Long")
    private Long standardId;

}
