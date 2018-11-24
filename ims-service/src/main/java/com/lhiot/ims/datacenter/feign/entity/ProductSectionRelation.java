package com.lhiot.ims.datacenter.feign.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiaojian  created in  2018/11/16 9:13
 */
@Data
@ApiModel
public class ProductSectionRelation {
    @ApiModelProperty(notes = "主键Id", dataType = "Long", readOnly = true)
    private Long id;
    @ApiModelProperty(notes = "商品上架ID", dataType = "Long")
    private Long shelfId;
    @ApiModelProperty(notes = "商品板块ID", dataType = "Long")
    private Long sectionId;

}
