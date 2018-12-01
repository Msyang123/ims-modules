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

/**
 * @author hufan created in 2018/11/26 18:38
 **/
@Data
@ToString(callSuper = true)
@ApiModel
@NoArgsConstructor
public class CustomPlanSectionResultAdmin {
    /**
     *
     */
    @JsonProperty("id")
    @ApiModelProperty(value = "", dataType = "Long", readOnly = true)
    private Long id;
    /**
     *
     */
    @JsonProperty("sectionImage")
    @ApiModelProperty(value = "", dataType = "String")
    private String sectionImage;
    /**
     *
     */
    @JsonProperty("url")
    @ApiModelProperty(value = "", dataType = "String")
    private String url;
    /**
     *
     */
    @JsonProperty("sectionName")
    @ApiModelProperty(value = "", dataType = "String")
    @NotNull
    private String sectionName;

    /**
     *
     */
    @JsonProperty("sectionCode")
    @ApiModelProperty(value = "", dataType = "String")
    @NotNull
    private String sectionCode;
    /**
     *
     */
    @JsonProperty("sort")
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer sort;
    /**
     *
     */
    @JsonProperty("createAt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "", dataType = "Date", readOnly = true)
    private Date createAt;

    @JsonProperty("customPlanList")
    @ApiModelProperty(value = "", dataType = "Pages")
    private List<CustomPlan> customPlanList;


    @ApiModelProperty(value = "定制计划和定制板块关联排序", dataType = "List")
    private List<Long> relationSorts;

}
