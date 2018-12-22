package com.lhiot.ims.healthygood.feign.user.type;

/**
 * 薪资管理结算状态
 * @author hufan created in 2018/12/11 16:13
 */
public enum SettlementStatus {
    UNSETTLED("未处理"),
    SUCCESS("已成功"),
    EXPIRED("已过期");
    private final String displayTag;

    public String getDisplayTag(){
        return this.displayTag;
    }

    SettlementStatus(String displayTag){
        this.displayTag = displayTag;
    }
}