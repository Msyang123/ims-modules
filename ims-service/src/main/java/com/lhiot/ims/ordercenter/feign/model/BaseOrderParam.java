package com.lhiot.ims.ordercenter.feign.model;

import com.lhiot.ims.ordercenter.feign.type.OrderStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiaojian  created in  2018/12/8 11:28
 */
@ApiModel
@Data
public class BaseOrderParam {
    @ApiModelProperty(notes = "用户ID(多个以英文逗号分隔)", dataType = "String")
    private String userIds;
    @ApiModelProperty(notes = "订单类型", dataType = "String")
    private String orderType;
    @ApiModelProperty(notes = "订单状态", dataType = "OrderStatus")
    private OrderStatus orderStatus;
    @ApiModelProperty(notes = "每页查询条数(为空或0不分页查所有)", dataType = "Integer")
    private Integer rows;
    @ApiModelProperty(notes = "当前页", dataType = "Integer")
    private Integer page;
}
