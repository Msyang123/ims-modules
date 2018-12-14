package com.lhiot.ims.datacenter.feign.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author xiaojian  created in  2018/11/17 14:33
 */
@ApiModel
@Data
public class ProductSectionParam {
    @ApiModelProperty(notes = "板块主键Id集合",dataType = "String")
    private String ids;
    @ApiModelProperty(notes = "位置ID(多个以英文逗号分隔)", dataType = "String")
    private String positionIds;
    @ApiModelProperty(notes = "描述", dataType = "String")
    private String description;
    @ApiModelProperty(notes = "板块名称", dataType = "String")
    private String sectionName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(notes = "起始创建时间", dataType = "Date")
    private Date beginCreateAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(notes = "截止创建时间", dataType = "Date")
    private Date endCreateAt;
    @ApiModelProperty(notes = "是否加载版块下商品上架信息(为空则默认为false)", dataType = "Boolean")
    private Boolean includeShelves;
    @ApiModelProperty(notes = "加载商品上架最大条数(includeShelves为true起用，为空则加载所有)", dataType = "Long")
    private Long includeShelvesQty;
    @ApiModelProperty(notes = "是否加载商品信息((includeShelves为true起作用，为空则默认为false)", dataType = "Boolean")
    private Boolean includeProduct;
    @ApiModelProperty(notes = "版块内商品上架ID", dataType = "Long")
    private Long shelfId;
    @ApiModelProperty(notes = "每页查询条数(为空或0不分页查所有)", dataType = "Integer")
    private Integer rows;
    @ApiModelProperty(notes = "当前页", dataType = "Integer")
    private Integer page;
}
