package com.lhiot.ims.healthygood.feign.customplan.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.leon.microx.web.result.Pages;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
* Description:定制计划板块实体类
* @author zhangs
* @date 2018/11/22
*/
@Data
@ToString(callSuper = true)
@ApiModel
@NoArgsConstructor
public class CustomPlanSection{
    @ApiModelProperty(value = "主键id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "板块图片", dataType = "String")
    private String sectionImage;

    @ApiModelProperty(value = "板块链接url", dataType = "String")
    private String url;

    @ApiModelProperty(value = "板块名称", dataType = "String")
    private String sectionName;

    @ApiModelProperty(value = "板块编码", dataType = "String")
    private String sectionCode;

    @ApiModelProperty(value = "排序", dataType = "Integer")
    private Integer sort;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间", dataType = "Date", readOnly = true, example = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;


    @JsonProperty("customPlanList")
    @ApiModelProperty(value = "定制计划集合", dataType = "List")
    private List<CustomPlan> customPlanList;

}
