package com.lhiot.ims.datacenter.feign.type;

import lombok.Getter;

/**
 * 支付平台类型
 * @author zhangfeng created in 2018/9/22 10:24
 **/
public enum PayPlatformType {
    WE_CHAT("微信支付"),
    ALIPAY("支付宝支付");

    @Getter
    private String description;

    PayPlatformType(String description) {
        this.description = description;
    }
}
