package com.lhiot.ims.healthygood.feign.customplan.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Description:定制计划关联商品实体类
 *
 * @author zhangs
 * @date 2018/11/26
 */
@Data
@ToString(callSuper = true)
@ApiModel
@NoArgsConstructor
public class CustomPlanProduct {

    @ApiModelProperty(value = "id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "定制计划id", dataType = "Long")
    private Long planId;

    @ApiModelProperty(value = "上架Id", dataType = "Long")
    private Long shelfId;

    @ApiModelProperty(value = "第x天", dataType = "Integer")
    private Integer dayOfPeriod;

    @ApiModelProperty(value = "定制计划周期（周、月）", dataType = "Integer")
    private Integer planPeriod;

    @ApiModelProperty(value = "序号", dataType = "Integer")
    private Integer sort;

}
