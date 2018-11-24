package com.lhiot.ims.datacenter.feign.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * @author xiaojian created in 2018/11/12 17:09
 **/
@Data
@ApiModel
public class Product {
    @ApiModelProperty(notes = "主键Id", dataType = "Long", readOnly = true)
    private Long id;
    @ApiModelProperty(notes = "商品编码", dataType = "String")
    private String code;
    @ApiModelProperty(notes = "商品名称", dataType = "String")
    private String name;
    @ApiModelProperty(notes = "分类ID", dataType = "Long")
    private Long categoryId;
    @ApiModelProperty(notes = "产地ID", dataType = "String")
    private String sourceCode;
    @ApiModelProperty(notes = "商品描述", dataType = "String")
    private String description;
    @ApiModelProperty(notes = "创建时间", dataType = "Date", readOnly = true)
    private Date createAt;
    @ApiModelProperty(notes = "商品附件信息", dataType = "ProductAttachment")
    private List<ProductAttachment> attachments;

}
