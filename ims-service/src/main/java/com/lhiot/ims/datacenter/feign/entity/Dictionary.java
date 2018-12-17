package com.lhiot.ims.datacenter.feign.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Leon (234239150@qq.com) created in 11:29 18.10.15
 */
@Data
@ApiModel
public class Dictionary {
    @ApiModelProperty(notes = "主键Id",dataType = "Long")
    private Long id;
    @ApiModelProperty(notes = "父级Id",dataType = "Long")
    private Long parentId;
    @NotNull(message = "字典名称不能为空")
    @ApiModelProperty(notes = "字典名称",dataType = "String")
    private String name;
    @NotNull(message = "字典编码不能为空")
    @ApiModelProperty(notes = "字典编码",dataType = "String")
    private String code;
    @ApiModelProperty(notes = "字典描述",dataType = "String")
    private String description;

    @ApiModelProperty(notes = "子级字典",dataType = "Dictionary")
    private List<Dictionary> children;

    @ApiModelProperty(notes = "字典中的字典项",dataType = "DictionaryEntry")
    private List<DictionaryEntry> entries;
}
