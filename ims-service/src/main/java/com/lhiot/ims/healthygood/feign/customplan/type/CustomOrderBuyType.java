package com.lhiot.ims.healthygood.feign.customplan.type;

/**
 * 定制订单配送方式
 */
public enum CustomOrderBuyType {
    MANUAL("手动"),
    AUTO("自动");
    private final String displayTag;

    public String getDisplayTag(){
        return this.displayTag;
    }

    CustomOrderBuyType(String displayTag){
        this.displayTag = displayTag;
    }
}