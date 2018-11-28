package com.lhiot.ims.datacenter.feign.entity.type;

import lombok.Getter;

/**
 * @author zhangfeng created in 2018/9/20 12:22
 **/
public enum  AttachmentType {
    MAIN_IMG("主图"),
    SUB_IMG("附图"),
    DETAIL_IMG("详情图"),
    ICON("图标");
    @Getter
    private String description;
    AttachmentType(String description){this.description = description;}




}
