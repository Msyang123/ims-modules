package com.lhiot.ims.healthygood.feign.customplan.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Description:定制计划板块关联定制计划实体类
 *
 * @author zhangs
 * @date 2018/11/22
 */
@Data
@ToString(callSuper = true)
@ApiModel
@NoArgsConstructor
public class CustomPlanSectionRelation {


    @ApiModelProperty(value = "id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "定制计划板块id", dataType = "Long")
    private Long sectionId;

    @ApiModelProperty(value = "定制计划id", dataType = "Long")
    private Long planId;

    @ApiModelProperty(value = "定制计划排序", dataType = "Long")
    private Long sort;

}
