package com.lhiot.ims.healthygood.feign.customplan.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author hufan created in 2018/12/8 9:57
 **/
@Data
@ToString(callSuper = true)
@ApiModel
@NoArgsConstructor
public class CustomPlanSpecificationStandardParam {

    @ApiModelProperty(value = "规格基础id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "数量", dataType = "Integer")
    private Integer quantity;

    @ApiModelProperty(value = "描述", dataType = "String")
    private String description;

    @ApiModelProperty(value = "定制说明配图", dataType = "String")
    private String image;

    @ApiModelProperty(notes = "每页查询条数(为空或0不分页查所有)", dataType = "Integer")
    private Integer rows;

    @ApiModelProperty(notes = "当前页", dataType = "Integer")
    private Integer page;
}