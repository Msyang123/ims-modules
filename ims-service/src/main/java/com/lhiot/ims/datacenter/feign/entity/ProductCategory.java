package com.lhiot.ims.datacenter.feign.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author xiaojian  created in  2018/11/16 16:47
 */
@Data
@ApiModel
public class ProductCategory {
    @ApiModelProperty(notes = "主键Id", dataType = "Long", readOnly = true)
    private Long id;
    @ApiModelProperty(notes = "父级ID", dataType = "Long")
    private Long parentId;
    @ApiModelProperty(notes = "分类名", dataType = "String")
    private String groupName;
    @ApiModelProperty(notes = "排序字段", dataType = "Integer")
    private Integer rank;
    @ApiModelProperty(notes = "创建时间", dataType = "Date", readOnly = true)
    private Date createAt;
}
