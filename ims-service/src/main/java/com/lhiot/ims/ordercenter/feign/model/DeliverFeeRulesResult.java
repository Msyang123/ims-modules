package com.lhiot.ims.ordercenter.feign.model;

import com.lhiot.ims.ordercenter.feign.entity.DeliverFeeRuleDetail;
import com.lhiot.ims.ordercenter.feign.type.DeliverAtType;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author zhangfeng create in 12:04 2018/12/11
 */
@Data
public class DeliverFeeRulesResult {
    @Min(1)
    private Long id;

    @Min(0)
    private Integer minOrderAmount;

    @Min(1)
    private Integer maxOrderAmount;

    @NotNull
    private DeliverAtType deliveryAtType;

    private Date updateAt;

    private String createBy;

    private List<DeliverFeeRuleDetail> detailList;

}
