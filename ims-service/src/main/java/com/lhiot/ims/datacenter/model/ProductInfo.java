package com.lhiot.ims.datacenter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leon.microx.predefine.Use;
import com.lhiot.ims.datacenter.feign.entity.ProductAttachment;
import com.lhiot.ims.datacenter.feign.entity.ProductSpecification;
import com.lhiot.ims.datacenter.feign.entity.type.InventorySpecification;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author xiaojian  created in  2018/11/17 12:18
 */
@ApiModel
@Data
public class ProductInfo {
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

    @ApiModelProperty(notes = "商品基础规格信息", dataType = "ProductSpecification")
    private ProductSpecification productSpecification;
}