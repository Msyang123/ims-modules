package com.lhiot.ims.healthygood.feign.customplan.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lhiot.ims.healthygood.feign.customplan.entity.CustomPlan;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@ToString(callSuper = true)
@ApiModel
@NoArgsConstructor
public class CustomPlanSectionResultAdmin {
    /**
     *
     */
    @JsonProperty("id")
    @ApiModelProperty(value = "定制板块id", dataType = "Long")
    private Long id;
    /**
     *
     */
    @JsonProperty("sectionImage")
    @ApiModelProperty(value = "定制板块图片", dataType = "String")
    private String sectionImage;
    /**
     *
     */
    @JsonProperty("url")
    @ApiModelProperty(value = "板块链接url", dataType = "String")
    private String url;
    /**
     *
     */
    @JsonProperty("sectionName")
    @ApiModelProperty(value = "定制板块名称", dataType = "String")
    @NotNull
    private String sectionName;

    /**
     *
     */
    @JsonProperty("sectionCode")
    @ApiModelProperty(value = "定制板块编码", dataType = "String")
    @NotNull
    private String sectionCode;
    /**
     *
     */
    @JsonProperty("sort")
    @ApiModelProperty(value = "排序", dataType = "Integer")
    private Integer sort;
    /**
     *
     */
    @JsonProperty("createAt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间", dataType = "Date", readOnly = true)
    private Date createAt;

    @JsonProperty("customPlanList")
    @ApiModelProperty(value = "定制计划集合", dataType = "List")
    private List<CustomPlan> customPlanList;


    @ApiModelProperty(value = "定制计划和定制板块关联排序集合", dataType = "List")
    private List<Long> relationSorts;

}
