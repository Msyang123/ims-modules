package com.lhiot.ims.healthygood.feign.user.type;

/**
 * 鲜果师审核状态
 * @author hufan created in 2018/12/11 16:13
 */
public enum AuditStatus {
    UNAUDITED("待审核"),
    AGREE("审核通过"),
    REJECT("审核不通过");
    private final String displayTag;

    public String getDisplayTag(){
        return this.displayTag;
    }

    AuditStatus(String displayTag){
        this.displayTag = displayTag;
    }
}