package com.lhiot.ims.datacenter.feign.type;

import lombok.Getter;

/**
 * 是否为库存规格
 * @author zhangfeng created in 2018/9/20 15:34
 **/
public enum InventorySpecification {
    YES("是"),
    NO("否");

    @Getter
    private String description;

    InventorySpecification(String description) {
        this.description = description;
    }
}
