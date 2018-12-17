package com.lhiot.ims.datacenter.feign.model;

import com.lhiot.ims.datacenter.feign.entity.Advertisement;
import com.lhiot.ims.datacenter.feign.entity.Article;
import com.lhiot.ims.datacenter.feign.entity.ProductSection;
import com.lhiot.ims.datacenter.feign.type.PositionType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hufan created in 2018/12/5 9:24
 **/
@ApiModel
@Data
@NoArgsConstructor
@ToString
public class UiPositionDetail {
    @ApiModelProperty(notes = "主键Id", dataType = "Long", readOnly = true)
    private Long id;
    @ApiModelProperty(notes = "类别:PRODUCT-商品，ADVERTISEMENT-广告，ARTICLE-文章", dataType = "PositionType")
    private PositionType positionType;
    @NotNull(message = "位置编码不能为空")
    @ApiModelProperty(notes = "位置编码", dataType = "String")
    private String code;
    @ApiModelProperty(notes = "描述", dataType = "String")
    private String description;
    @ApiModelProperty(notes = "应用类型", dataType = "String")
    private String applicationType;

    /**
     * 广告信息列表
     */
    @ApiModelProperty(notes = "广告列表", dataType = "List")
    List<Advertisement> advertisementList;

    /**
     * 商品信息列表
     */
    @ApiModelProperty(notes = "商品板块列表（包括上架商品）", dataType = "List")
    List<ProductSection> productSectionList;


    /**
     * 文章信息列表
     */
    List<Article> articleList;

}