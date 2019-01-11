package com.lhiot.ims.healthygood.feign.customplan.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhiot.ims.healthygood.feign.customplan.type.ValidOrInvalid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ToString(callSuper = true)
@ApiModel
@NoArgsConstructor
public class CustomPlanDetailResult {

    @ApiModelProperty(value = "定制计划id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "状态 VALID-有效, INVALID-无效;", dataType = "ValidOrInvalid")
    private ValidOrInvalid status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间", dataType = "Date", example = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date createAt;

    @ApiModelProperty(value = "到期规则", dataType = "String")
    private String overRule;

    @ApiModelProperty(value = "创建人", dataType = "String")
    private String createUser;

    @ApiModelProperty(value = "banner图片", dataType = "String")
    private String image;

    @ApiModelProperty(value = "名称", dataType = "String")
    private String name;

    @ApiModelProperty(value = "描述", dataType = "String")
    private String description;

    @ApiModelProperty(value = "定制计划中定制规格最低价格", dataType = "Long")
    private Long price;

    @ApiModelProperty(value = "周期类型", dataType = "List")
    @NotNull(message = "定制计划规格和定制计划商品不能为空")
    private List<CustomPlanPeriodResult> periodList;

    /**
     * 关联的定制板块
     */
    @ApiModelProperty(value = "定制板块ids，英文逗号分隔", dataType = "List")
    @NotNull(message = "关联板块不能为空")
    private List<Long> customPlanSectionIds;
}
