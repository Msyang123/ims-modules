package com.lhiot.ims.datacenter.feign.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Leon (234239150@qq.com) created in 14:57 18.10.15
 */
@Data
@ApiModel
public class DictionaryEntry {
    @ApiModelProperty(notes = "主键Id",dataType = "Long")
    private Long id;
    @ApiModelProperty(notes = "字典编码",dataType = "String")
    private String dictCode;
    @ApiModelProperty(notes = "字典名称",dataType = "String")
    private String name;
    @ApiModelProperty(notes = "字典项编码",dataType = "String")
    private String code;
    @ApiModelProperty(notes = "排序序号",dataType = "Integer")
    private Integer sort;
    @ApiModelProperty(notes = "附加参数",dataType = "String")
    private String attach;
}
