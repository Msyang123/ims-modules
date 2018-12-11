package com.lhiot.ims.usercenter.feign.type;

/**
 * 免密支付权限
 * @author Leon (234239150@qq.com) created in 9:06 18.9.7
 */

public enum SwitchStatus {
    OPEN("打开"),
    CLOSE("关闭");

    private String description;

    SwitchStatus(String description) {
        this.description = description;
    }
}
