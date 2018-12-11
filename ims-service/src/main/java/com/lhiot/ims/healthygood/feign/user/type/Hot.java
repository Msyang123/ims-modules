package com.lhiot.ims.healthygood.feign.user.type;

/**
 * 是否为明星鲜果师
 * @author hufan created in 2018/12/11 16:13
 */
public enum Hot {
    NO("普通鲜果师"),
    YES("明星鲜果师");
    private final String displayTag;

    public String getDisplayTag(){
        return this.displayTag;
    }

    Hot(String displayTag){
        this.displayTag = displayTag;
    }
}