package com.lhiot.ims.ordercenter.feign.type;

import lombok.Getter;

/**
 * @author hufan created in 2018/12/13 9:28
 **/
public enum UpdateWay {
    INSERT("新增"),
    UPDATE("修改")
    ;

    @Getter
    private String description;

    UpdateWay(String description) {
        this.description = description;
    }
}
