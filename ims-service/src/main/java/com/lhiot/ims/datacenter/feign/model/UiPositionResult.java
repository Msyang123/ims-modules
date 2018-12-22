package com.lhiot.ims.datacenter.feign.model;

import com.lhiot.ims.datacenter.feign.entity.ProductSection;
import com.lhiot.ims.datacenter.feign.type.PositionType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hufan created in 2018/12/4 18:45
 **/
@Data
@ApiModel
public class UiPositionResult {
    @ApiModelProperty(notes = "主键Id", dataType = "Long", readOnly = true)
    private Long id;
    @ApiModelProperty(notes = "类别:PRODUCT-商品，ADVERTISEMENT-广告，ARTICLE-文章", dataType = "PositionType")
    private PositionType positionType;
    @NotNull(message = "位置编码不能为空")
    @ApiModelProperty(notes = "位置编码", dataType = "String")
    private String code;
    @ApiModelProperty(notes = "描述", dataType = "String")
    private String description;
    @ApiModelProperty(notes = "应用类型", dataType = "String")
    private String applicationType;

    @ApiModelProperty(notes = "关联的板块信息", dataType = "List")
    private List<ProductSection> productSections;
}