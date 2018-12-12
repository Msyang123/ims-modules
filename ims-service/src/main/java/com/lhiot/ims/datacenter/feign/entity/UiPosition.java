package com.lhiot.ims.datacenter.feign.entity;

import com.lhiot.dc.dictionary.HasEntries;
import com.lhiot.ims.datacenter.feign.type.PositionType;
import com.lhiot.ims.datacenter.util.DictionaryCodes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * @author xiaojian  created in  2018/11/20 15:58
 */
@Data
@ApiModel
public class UiPosition {
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

}
