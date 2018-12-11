package com.lhiot.ims.healthygood.feign.activity.type;

import lombok.Getter;

/**
 * 活动类型
 * @author hufan created in 2018/12/3 17:24
 **/
public enum ActivityType {
    NEW_SPECIAL("新品尝鲜活动");

    @Getter
    private String description;

    ActivityType(String description) {
        this.description = description;
    }
}
