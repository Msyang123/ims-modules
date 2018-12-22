package com.lhiot.ims.datacenter.feign.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangfeng create in 11:56 2018/11/9
 */
@ApiModel
@Data
public class StoreSearchParam {
    @ApiModelProperty(notes = "门店Id集合", dataType = "String")
    private String storeIds;
    @ApiModelProperty(notes = "门店名称", dataType = "String")
    private String name;
    @ApiModelProperty(notes = "门店编码", dataType = "String")
    private String code;

    @ApiModelProperty(notes = "应用类型", dataType = "String")
    private String applicationType;
    @ApiModelProperty(notes = "距离", dataType = "String")
    private Double distance = 0D;
    @ApiModelProperty(notes = "纬度", dataType = "Double")
    private Double lat;
    @ApiModelProperty(notes = "经度", dataType = "Double")
    private Double lng;
    @ApiModelProperty(notes = "查询条数", dataType = "Integer")
    private Integer rows;
    @ApiModelProperty(notes = "当前页", dataType = "Integer")
    private Integer page;
}
