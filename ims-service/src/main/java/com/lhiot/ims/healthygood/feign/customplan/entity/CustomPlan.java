package com.lhiot.ims.healthygood.feign.customplan.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhiot.ims.healthygood.feign.customplan.type.ValidOrInvalid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Description:定制计划实体类
 *
 * @author zhangs
 * @date 2018/11/22
 */
@Data
@ToString(callSuper = true)
@ApiModel
@NoArgsConstructor
public class CustomPlan {

    @ApiModelProperty(value = "主键Id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "名称", dataType = "String")
    private String name;

    @ApiModelProperty(value = "描述", dataType = "String")
    private String description;

    @ApiModelProperty(value = "banner图片", dataType = "String")
    private String image;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间", dataType = "Date", readOnly = true, example = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date createAt;

    @ApiModelProperty(value = "到期规则", dataType = "String")
    private String overRule;

    @ApiModelProperty(value = "是否有效 VALID-有效 INVALID-无效", dataType = "ValidOrInvalid")
    private ValidOrInvalid status;

    @ApiModelProperty(value = "创建人", dataType = "String")
    private String createUser;

    @ApiModelProperty(value = "定制计划关联排序", dataType = "Long")
    private Long relationSort;
}
