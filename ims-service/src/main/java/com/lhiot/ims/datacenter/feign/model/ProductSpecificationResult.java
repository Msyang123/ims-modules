package com.lhiot.ims.datacenter.feign.model;

import com.leon.microx.predefine.Use;
import com.lhiot.dc.dictionary.HasEntries;
import com.lhiot.ims.datacenter.feign.type.InventorySpecification;
import com.lhiot.ims.datacenter.util.DictionaryCodes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author hufan created in 2018/12/3 11:23
 **/
@Data
@ApiModel
public class ProductSpecificationResult {
    @ApiModelProperty(notes = "主键Id", dataType = "Long", readOnly = true)
    private Long id;
    @NotNull(message = "商品ID不能为空")
    @ApiModelProperty(notes = "商品名称", dataType = "Long")
    private String productName;
    @ApiModelProperty(notes = "商品对象", dataType = "ProductResult", readOnly = true)
    private ProductResult productResult;
    @ApiModelProperty(notes = "商品条码", dataType = "String")
    private String barcode;
    @ApiModelProperty(notes = "打包单位", dataType = "String")
    @HasEntries(from = DictionaryCodes.UNITS, message = "没有找到此规格单位")
    private String packagingUnit;
    @ApiModelProperty(notes = "单份规格商品的重量", dataType = "BigDecimal")
    private BigDecimal weight;
    @ApiModelProperty(notes = "海鼎规格数量", dataType = "BigDecimal")
    private BigDecimal specificationQty;
    @ApiModelProperty(notes = "安全库存", dataType = "Integer")
    private Integer limitInventory;
    @ApiModelProperty(notes = "是否为库存规格：YES-是，NO-否", dataType = "InventorySpecification")
    private InventorySpecification inventorySpecification;
    @ApiModelProperty(notes = "是否可用：ENABLE-可用，DISABLE-不可用", dataType = "Use")
    private Use availableStatus;
    @ApiModelProperty(notes = "创建时间", dataType = "Date", readOnly = true)
    private Date createAt;
    @ApiModelProperty(notes = "规格", dataType = "String")
    private String specification;
}