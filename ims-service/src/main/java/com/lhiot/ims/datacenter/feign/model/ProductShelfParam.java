package com.lhiot.ims.datacenter.feign.model;

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
 * @author xiaojian  created in  2018/11/16 11:20
 */
@ApiModel
@Data
public class ProductShelfParam {
    @ApiModelProperty(notes = "主键Id", dataType = "Long", readOnly = true)
    private Long id;
    @ApiModelProperty(notes = "上架ID(多个以英文逗号分隔)", dataType = "String", readOnly = true)
    private String ids;
    @NotNull(message = "上架名称不能为空")
    @ApiModelProperty(notes = "上架名称", dataType = "String")
    private String name;
    @ApiModelProperty(notes = "规格ID", dataType = "Long", readOnly = true)
    private Long specificationId;
    @ApiModelProperty(notes = "上架数量", dataType = "BigDecimal", readOnly = true)
    private BigDecimal shelfQty;
    @ApiModelProperty(notes = "特价", dataType = "Integer", readOnly = true)
    private Integer price;
    @ApiModelProperty(notes = "原价", dataType = "Integer", readOnly = true)
    private Integer originalPrice;
    @ApiModelProperty(notes = "上架图片", dataType = "String", readOnly = true)
    private String image;
    @ApiModelProperty(notes = "商品主图", dataType = "String", readOnly = true)
    private String productImage;
    @ApiModelProperty(notes = "上架状态：ON-上架，OFF-下架", dataType = "OnOff")
    private OnOff shelfStatus;
    @ApiModelProperty(notes = "上架类型：NORMAL-普通商品,GIFT-赠品", dataType = "ShelfType")
    private ShelfType shelfType;
    @ApiModelProperty(notes = "创建时间", dataType = "Date", readOnly = true)
    private Date createAt;
    @ApiModelProperty(notes = "描述", dataType = "String", readOnly = true)
    private String description;
    @ApiModelProperty(notes = "排序字段", dataType = "Integer", readOnly = true)
    private Integer sorting;
    @ApiModelProperty(notes = "应用类型", dataType = "String", readOnly = true)
    private ApplicationType applicationType;
    @ApiModelProperty(notes = "名称或条码关键字", dataType = "String")
    private String keyword;
    @ApiModelProperty(notes = "是否加载商品信息(为空则默认为false)", dataType = "Boolean", readOnly = true)
    private Boolean includeProduct;

    @ApiModelProperty(notes = "商品名称", dataType = "String")
    private String productName;
    @ApiModelProperty(notes = "上架规格", dataType = "String", readOnly = true)
    private String productSpecification;
    @ApiModelProperty(notes = "规格条码", dataType = "String", readOnly = true)
    private String barcode;

    @ApiModelProperty(notes = "每页查询条数(为空或0不分页查所有)", dataType = "Integer")
    private Integer rows;
    @ApiModelProperty(notes = "当前页", dataType = "Integer")
    private Integer page;
}
