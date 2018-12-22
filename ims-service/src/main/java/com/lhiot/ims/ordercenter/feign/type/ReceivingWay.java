package com.lhiot.ims.ordercenter.feign.type;

import lombok.Getter;

/**
 * @author hufan created in 2018/12/13 9:28
 **/
public enum ReceivingWay {
    TO_THE_STORE("门店自提"),
    TO_THE_HOME("送货上门");

    @Getter
    private String description;

    ReceivingWay(String description) {
        this.description = description;
    }
}
