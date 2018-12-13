package com.lhiot.ims.ordercenter.feign.type;


public enum DeliverType {

    FENGNIAO("蜂鸟配送"),

    DADA("达达配送"),

    MEITUAN("美团配送"),

    @Deprecated
    OWN("自己配送");

    private String description;

    DeliverType(String description) {
        this.description = description;
    }

}
