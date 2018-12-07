package com.lhiot.ims.datacenter.feign.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lhiot.ims.datacenter.feign.type.ApplicationType;
import com.lhiot.ims.datacenter.feign.type.PositionType;
import com.lhiot.ims.datacenter.type.YesOrNo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiaojian  created in  2018/11/20 18:14
 */
@ApiModel
@Data
public class UiPositionParam {
    @ApiModelProperty(notes = "位置Id", dataType = "Long", readOnly = true)
    @JsonProperty("positionId")
    private Long id;
    @ApiModelProperty(notes = "类别:PRODUCT-商品，ADVERTISEMENT-广告，ARTICLE-文章", dataType = "PositionType")
    private PositionType positionType;
    @ApiModelProperty(notes = "应用类型", dataType = "String", readOnly = true)
    private ApplicationType applicationType;
    @ApiModelProperty(notes = "位置编码", dataType = "String")
    private String codes;
    @ApiModelProperty(notes = "描述", dataType = "String")
    private String description;
    @ApiModelProperty(notes = "是否查询UI关联的板块信息，YES-查询， NO-不查询，默认为NO")
    private YesOrNo includeSection;

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
