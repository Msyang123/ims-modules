package com.lhiot.ims.healthygood.feign.customplan.type;

import lombok.Getter;

/**
 * 是否有效
 */
public enum ValidOrInvalid {
    VALID("有效"),
    INVALID("无效");

    @Getter
    private String decription;

    ValidOrInvalid(String decription) {
        this.decription = decription;
    }
}
