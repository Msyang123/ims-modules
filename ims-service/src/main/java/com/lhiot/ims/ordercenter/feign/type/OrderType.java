package com.lhiot.ims.ordercenter.feign.type;

import lombok.Getter;

/**
 * @author hufan created in 2018/12/13 9:28
 **/
public enum OrderType {
    NORMAL("普通订单"),
    CUSTOM("定制订单"),
    TEAM_BUY("团购订单");

    @Getter
    private String description;

    OrderType(String description) {
        this.description = description;
    }
}