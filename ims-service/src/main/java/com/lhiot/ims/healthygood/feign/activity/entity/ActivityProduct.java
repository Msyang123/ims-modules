package com.lhiot.ims.healthygood.feign.activity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lhiot.ims.healthygood.feign.activity.type.ActivityType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
* Description:活动商品实体类
* @author yangjiawen
* @date 2018/11/24
*/
@Data
@ToString(callSuper = true)
@ApiModel
@NoArgsConstructor
public class ActivityProduct {

    /**
     *
     */
    @JsonProperty("id")
    @ApiModelProperty(value = "主键id", dataType = "Long", readOnly = true)
    private Long id;

    /**
    *
    */
    @JsonProperty("activityId")
    @ApiModelProperty(value = "活动id", dataType = "Long", readOnly = true)
    @NotNull(message = "活动id不为空")
    private Long activityId = 1L;


    @JsonProperty("specialProductActivityId")
    @ApiModelProperty(value = "新品尝鲜活动id", dataType = "Long", readOnly = true)
    @NotNull(message = "新品尝鲜活动id不为空")
    private Long specialProductActivityId = 1L;
    /**
    *
    */
    @JsonProperty("activityType")
    @ApiModelProperty(value = "活动类型", dataType = "String", readOnly = true)
    @NotNull(message = "活动类型不为空")
    private ActivityType activityType = ActivityType.NEW_SPECIAL;

    /**
    *
    */
    @JsonProperty("productShelfId")
    @ApiModelProperty(value = "商品上架id", dataType = "Long")
    @NotNull(message = "商品上架id不为空")
    private Long productShelfId;

    /**
    *
    */
    @JsonProperty("activityPrice")
    @ApiModelProperty(value = "活动价", dataType = "Integer")
    private Integer activityPrice;

    /**
     *  序号
     */
    @JsonProperty("sort")
    @ApiModelProperty(value = "序号", dataType = "Integer")
    private Integer sort;
}

