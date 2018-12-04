package com.lhiot.ims.healthygood.feign.activity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author hufan created in 2018/12/3 16:15
 **/
@Data
@ToString(callSuper = true)
@ApiModel
@NoArgsConstructor
public class ActivityProductParam {

    @ApiModelProperty(value = "活动商品id", dataType = "Long", readOnly = true)
    private Long id;
    @ApiModelProperty(value = "活动id", dataType = "Long", readOnly = true)
    private Long activityId;
    @ApiModelProperty(value = "新品尝鲜活动id", dataType = "Long", readOnly = true)
    private Long specialProductActivityId;
    @ApiModelProperty(value = "活动类型", dataType = "String", readOnly = true)
    private String activityType;
    @ApiModelProperty(value = "商品上架id", dataType = "Long", readOnly = true)
    private Long productShelfId;
    @ApiModelProperty(value = "活动价", dataType = "Integer", readOnly = true)
    private Integer activityPrice;
    @ApiModelProperty(value = "序号", dataType = "Integer", readOnly = true)
    private Integer sort;

    @ApiModelProperty(notes = "上架名称", dataType = "String", readOnly = true)
    private String name;
    @ApiModelProperty(notes = "上架图片", dataType = "String", readOnly = true)
    private String image;
    @ApiModelProperty(notes = "上架规格", dataType = "String", readOnly = true)
    private String specification;
    @ApiModelProperty(notes = "规格条码", dataType = "String", readOnly = true)
    private String barcode;
    @ApiModelProperty(notes = "特价", dataType = "Integer", readOnly = true)
    private Integer price;
    @ApiModelProperty(notes = "原价", dataType = "Integer", readOnly = true)
    private Integer originalPrice;

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