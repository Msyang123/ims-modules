package com.lhiot.ims.rbac.domain;

import lombok.Getter;

/**
 * @author Leon (234239150@qq.com) created in 13:22 18.5.27
 */
public enum StatusEnum {
    INITIAL("初始化"),
    AVAILABLE("可用"),
    UNAVAILABLE("不可用"),
    LOCK("锁定，暂不可用"),
    DELETE("删除，永不可用"),
    UNKNOWN("未知")
    ;
    @Getter
    private String message;

    StatusEnum(String message) {
        this.message = message;
    }

    class Enum{
        String name;
    }

    class Test extends Enum{
        final Enum AAA = new Test();
        final Enum BBB = new Test();
        final Enum CCC = new Test();
    }
}
