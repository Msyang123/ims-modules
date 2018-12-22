package com.lhiot.ims.datacenter.feign.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.leon.microx.predefine.Use;
import com.lhiot.dc.dictionary.HasEntries;
import com.lhiot.ims.datacenter.feign.type.InventorySpecification;
import com.lhiot.util.DictionaryCodes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author xiaojian  created in  2018/11/19 8:40
 */
@ApiModel
@Data
public class ProductSpecificationParam {
    @ApiModelProperty(notes = "商品ID", dataType = "Long")
    private Long productId;
    @ApiModelProperty(notes = "商品条码", dataType = "String")
    private String barCodes;
    @ApiModelProperty(notes = "打包单位", dataType = "String")
    @HasEntries(from = DictionaryCodes.UNITS, message = "没有找到此规格单位")
    private String packagingUnit;
    @ApiModelProperty(notes = "是否为库存规格：YES-是，NO-否", dataType = "InventorySpecification")
    private InventorySpecification inventorySpecification;
    @ApiModelProperty(notes = "是否可用：ENABLE-可用，DISABLE-不可用", dataType = "Use")
    private Use availableStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(notes = "起始创建时间", dataType = "Date")
    private Date beginCreateAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(notes = "截止创建时间", dataType = "Date")
    private Date endCreateAt;
    @ApiModelProperty(notes = "名称或条码关键字", dataType = "String")
    private String keyword;

    @ApiModelProperty(notes = "每页查询条数(为空或0不分页查所有)", dataType = "Integer")
    private Integer rows;
    @ApiModelProperty(notes = "当前页", dataType = "Integer")
    private Integer page;
}
