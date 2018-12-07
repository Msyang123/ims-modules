package com.lhiot.ims.datacenter.feign.type;

import lombok.Getter;

/**
 * @author hufan created in 2018/12/7 16:44
 **/
public enum AdvertiseType {
    IMAGE("图片广告"),
    TEXT("文字广告");

    @Getter
    private String description;

    AdvertiseType(String description) {
        this.description = description;
    }
}
