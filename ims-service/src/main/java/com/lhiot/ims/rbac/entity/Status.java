package com.lhiot.ims.rbac.entity;

import lombok.Getter;

/**
 * @author Leon (234239150@qq.com) created in 13:22 18.5.27
 */
public enum Status {
    INITIAL("初始化"),
    AVAILABLE("可用"),
    UNAVAILABLE("不可用"),
    LOCK("锁定，暂不可用"),
    DELETE("删除，永不可用"),
    UNKNOWN("未知");
    @Getter
    private String message;

    Status(String message) {
        this.message = message;
    }
}
