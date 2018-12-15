package com.lhiot.ims.ordercenter.feign.model;

import com.lhiot.ims.ordercenter.feign.type.DeliverAtType;
import lombok.Data;

/**
 * @author zhangfeng create in 8:56 2018/12/12
 */
@Data
public class DeliverFeeSearchParam {
    private Integer minOrderAmount;

    private Integer maxOrderAmount;

    private DeliverAtType deliveryAtType;

    private Integer rows;

    private Integer page;
}
