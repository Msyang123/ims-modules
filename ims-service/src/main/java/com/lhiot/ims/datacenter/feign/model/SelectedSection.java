package com.lhiot.ims.datacenter.feign.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author hufan created in 2018/12/5 11:42
 **/

public class SelectedSection {
    @ApiModelProperty(notes = "主键Id", dataType = "Long", readOnly = true)
    private Long id;
    @ApiModelProperty(notes = "板块名称", dataType = "String")
    private String sectionName;
}