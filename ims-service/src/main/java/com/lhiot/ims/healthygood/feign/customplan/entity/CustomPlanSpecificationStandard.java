package com.lhiot.ims.healthygood.feign.customplan.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Description:定制计划规格基础数据实体类
 *
 * @author hufan
 * @date 2018/12/08
 */
@Data
@ToString(callSuper = true)
@ApiModel
@NoArgsConstructor
public class CustomPlanSpecificationStandard {

    @ApiModelProperty(value = "规格基础id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "数量", dataType = "Integer")
    private Integer quantity;

    @ApiModelProperty(value = "描述", dataType = "String")
    private String description;

    @ApiModelProperty(value = "定制说明配图", dataType = "String")
    private String image;

}
