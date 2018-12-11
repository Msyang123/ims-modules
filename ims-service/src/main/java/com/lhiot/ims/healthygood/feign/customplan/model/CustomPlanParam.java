package com.lhiot.ims.healthygood.feign.customplan.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lhiot.ims.healthygood.feign.customplan.entity.CustomPlanSpecification;
import com.lhiot.ims.healthygood.feign.customplan.type.ValidOrInvalid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * @author hufan created in 2018/11/26 18:38
 **/
@Data
@ToString(callSuper = true)
@ApiModel
@NoArgsConstructor
public class CustomPlanParam {
    /**
     * 定制计划
     */
    @ApiModelProperty(value = "主键Id", dataType = "Long", readOnly = true)
    private Long id;
    @ApiModelProperty(value = "名称", dataType = "String")
    @NotNull
    private String name;
    @ApiModelProperty(value = "描述", dataType = "String", readOnly = true)
    private String description;
    @ApiModelProperty(value = "banner图片", dataType = "String", readOnly = true)
    private String image;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间", dataType = "Date", readOnly = true)
    private java.util.Date createAt;
    @ApiModelProperty(value = "到期规则", dataType = "String", readOnly = true)
    private String overRule;
    @ApiModelProperty(value = "定制计划状态 有效-VALID 无效-INVALID", dataType = "ValidOrInvalid", readOnly = true)
    private ValidOrInvalid status;
    @ApiModelProperty(value = "创建人", dataType = "String", readOnly = true)
    private String createUser;

    /**
     * 定制计划和定制板块关联
     */
    @ApiModelProperty(value = "定制板块关联定制计划排序，英文逗号分隔", dataType = "String", readOnly = true)
    @NotNull
    private String sorts;
    @ApiModelProperty(value = "定制板块ids，英文逗号分隔", dataType = "String", readOnly = true)
    @NotNull
    private String customPlanSectionIds;

    /**
     * 定制规格
     */
    @ApiModelProperty(value = "定制计划规格集合", dataType = "CustomPlanSpecification", readOnly = true)
    @NotNull
    private List<CustomPlanSpecification> customPlanSpecifications;

    @ApiModelProperty(notes = "每页查询条数(为空或0不分页查所有)", dataType = "Integer")
    private Integer rows;
    @ApiModelProperty(notes = "当前页", dataType = "Integer")
    private Integer page;
}