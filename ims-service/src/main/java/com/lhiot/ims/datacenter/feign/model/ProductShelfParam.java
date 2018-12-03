package com.lhiot.ims.datacenter.feign.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leon.microx.predefine.OnOff;
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
    @ApiModelProperty(notes = "创建时间", dataType = "Date", readOnly = true)
    private Date createAt;
    @ApiModelProperty(notes = "描述", dataType = "String")
    private String description;
    @ApiModelProperty(notes = "排序字段", dataType = "Integer")
    private Integer sorting;
    @ApiModelProperty(notes = "应用类型", dataType = "String")
    private String applicationType;

    @ApiModelProperty(notes = "商品名称", dataType = "String")
    private String productName;
    @ApiModelProperty(notes = "上架规格", dataType = "String")
    private String productSpecification;
    @ApiModelProperty(notes = "规格条码", dataType = "String")
    private String barcode;

    @ApiModelProperty(notes = "每页查询条数(为空或0不分页查所有)", dataType = "Integer")
    private Integer rows;
    @ApiModelProperty(notes = "当前页", dataType = "Integer")
    private Integer page;

    @ApiModelProperty(hidden = true)
    private Integer startRow;

    @JsonIgnore
    public Integer getStartRow() {
        if (this.rows != null && this.rows > 0) {
            return (this.page != null && this.page > 0 ? this.page - 1 : 0) * this.rows;
        }
        return null;
    }

}
