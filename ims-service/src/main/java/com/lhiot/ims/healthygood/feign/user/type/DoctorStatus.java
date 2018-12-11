package com.lhiot.ims.healthygood.feign.user.type;

/**
 * 鲜果师状态
 * @author hufan created in 2018/12/11 16:13
 */
public enum DoctorStatus {
    VALID("正常"),
    INVALID("已停用");
    private final String displayTag;

    public String getDisplayTag(){
        return this.displayTag;
    }

    DoctorStatus(String displayTag){
        this.displayTag = displayTag;
    }
}