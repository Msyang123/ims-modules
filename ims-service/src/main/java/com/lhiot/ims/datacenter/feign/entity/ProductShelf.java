package com.lhiot.ims.datacenter.feign.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.leon.microx.predefine.OnOff;
import com.lhiot.ims.datacenter.feign.type.ApplicationType;
import com.lhiot.ims.datacenter.feign.type.ShelfType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhangfeng create in 15:38 2018/11/8
 */
@Data
@ApiModel
public class ProductShelf {
    @ApiModelProperty(notes = "主键Id", dataType = "Long")
    private Long id;
    @NotNull(message = "上架名称不能为空")
    @ApiModelProperty(notes = "上架名称", dataType = "String")
    private String name;
    @ApiModelProperty(notes = "规格ID", dataType = "Long")
    private Long specificationId;
    @ApiModelProperty(notes = "上架数量", dataType = "BigDecimal")
    private BigDecimal shelfQty;
    @ApiModelProperty(notes = "特价", dataType = "Integer")
    private Integer price;
    @ApiModelProperty(notes = "原价", dataType = "Integer")
    private Integer originalPrice;
    @ApiModelProperty(notes = "上架图片", dataType = "String")
    private String image;
    @ApiModelProperty(notes = "商品主图", dataType = "String")
    private String productImage;
    @ApiModelProperty(notes = "上架状态：ON-上架，OFF-下架", dataType = "OnOff")
    private OnOff shelfStatus;
    @ApiModelProperty(notes = "上架类型：NORMAL-普通商品,GIFT-赠品", dataType = "ShelfType")
    private ShelfType shelfType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(notes = "创建时间", dataType = "Date", readOnly = true, example = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;
    @ApiModelProperty(notes = "描述", dataType = "String")
    private String description;
    @ApiModelProperty(notes = "排序字段", dataType = "Integer")
    private Integer sorting;
    @ApiModelProperty(notes = "应用类型", dataType = "ApplicationType")
    private ApplicationType applicationType;

    @ApiModelProperty(notes = "规格对象", dataType = "ProductSpecification", readOnly = true)
    private ProductSpecification productSpecification;


    @ApiModelProperty(notes = "商品名称", dataType = "String", readOnly = true)
    private String productName;
    @ApiModelProperty(notes = "上架规格(1*1份)", dataType = "String", readOnly = true)
    private String shelfSpecification;
    @ApiModelProperty(notes = "规格条码", dataType = "String", readOnly = true)
    private String barcode;
    @ApiModelProperty(notes = "板块ids", dataType = "String")
    private String sectionIds;

    @ApiModelProperty(notes = "规格信息（排毒养颜套餐  1盒  [00815]）", dataType = "String", readOnly = true)
    private String specificationInfo;

//    /**
//     * 已关联的板块信息
//     */
//    @ApiModelProperty(notes = "已关联的板块信息", dataType = "List")
//    @Ignore
//    private SelectedSection selected;

}
