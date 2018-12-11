package com.lhiot.ims.usercenter.feign.type;

/**
 * 用户锁定状态
 * @author Leon (234239150@qq.com) created in 9:05 18.9.7
 */
public enum LockStatus {
    LOCK("锁定"),
    UNLOCKED("未锁定");

    private String description;

    LockStatus(String description) {
        this.description = description;
    }
}
