package com.lhiot.ims.healthygood.feign.activity.entity;

import com.lhiot.ims.datacenter.type.YesOrNo;
import com.lhiot.ims.healthygood.feign.activity.type.ActivityType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * Description:活动商品实体类
 *
 * @author yangjiawen
 * @date 2018/11/24
 */
@Data
@ToString(callSuper = true)
@ApiModel
@NoArgsConstructor
public class ActivityProduct {
    @ApiModelProperty(value = "主键id", dataType = "Long", readOnly = true)
    private Long id;
    @ApiModelProperty(value = "活动id", dataType = "Long", readOnly = true)
    @NotNull(message = "活动id不为空")
    private Long activityId = 1L;
    @ApiModelProperty(value = "新品尝鲜活动id", dataType = "Long", readOnly = true)
    @NotNull(message = "新品尝鲜活动id不为空")
    private Long specialProductActivityId = 1L;
    @ApiModelProperty(value = "活动类型  NEW_SPECIAL-新品尝鲜活动", dataType = "ActivityType", readOnly = true)
    @NotNull(message = "活动类型不为空")
    private ActivityType activityType = ActivityType.NEW_SPECIAL;
    @ApiModelProperty(value = "商品上架id", dataType = "Long")
    @NotNull(message = "商品上架id不为空")
    private Long shelfId;
    @ApiModelProperty(value = "活动价", dataType = "Integer")
    private Integer activityPrice;
    @ApiModelProperty(value = "序号", dataType = "Integer")
    private Integer sort;

    @ApiModelProperty(value = "是否关联新品尝鲜板块", dataType = "YesOrNo")
    private YesOrNo relationSection;
}

