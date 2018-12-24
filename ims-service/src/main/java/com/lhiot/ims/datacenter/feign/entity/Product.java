package com.lhiot.ims.datacenter.feign.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
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
    @NotNull(message = "商品编码不能为空")
    @ApiModelProperty(notes = "商品编码", dataType = "String")
    private String code;
    @NotNull(message = "商品名称不能为空")
    private String name;
    @ApiModelProperty(notes = "分类ID", dataType = "Long")
    private Long categoryId;
    @ApiModelProperty(notes = "产地ID", dataType = "String")
    private String sourceCode;
    @ApiModelProperty(notes = "商品描述", dataType = "String")
    private String description;
    @ApiModelProperty(notes = "商品益处", dataType = "String")
    private String benefit;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(notes = "创建时间", dataType = "Date", readOnly = true, example = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    @ApiModelProperty(notes = "商品图片", dataType = "productImage")
    private String productImage;


    @ApiModelProperty(notes = "商品附件信息", dataType = "ProductAttachment", readOnly = true)
    private List<ProductAttachment> attachments;
}
