package com.lhiot.ims.datacenter.feign.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.leon.microx.predefine.OnOff;
import com.lhiot.ims.datacenter.feign.type.AdvertiseType;
import com.lhiot.ims.datacenter.feign.type.RelationType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author xiaojian  created in  2018/11/21 12:21
 */
@ApiModel
@Data
public class AdvertisementParam {
    @ApiModelProperty(notes = "位置ID(多个以英文逗号分隔)", dataType = "String")
    private String positionIds;
    @ApiModelProperty(notes = "广告名", dataType = "String")
    private String advertiseName;
    @ApiModelProperty(notes = "广告类别（IMAGE- 图片广告 TEXT -文字广告）", dataType = "AdvertiseType")
    private AdvertiseType advertiseType;
    @ApiModelProperty(notes = "广告关联类别（PRODUCT_DETAILS- 商品详情 PRODUCT_SECTION-商品版块 CUSTOM_PLAN-定制计划 CUSTOM_PLAN_SECTION-定制版块 ARTICLE_DETAILS-文章详情 STORE_LIVE_TELECAST- 门店直播 MORE_AMUSEMENT- 多娱 EXTERNAL_LINKS- 外部链接）", dataType = "RelationType")
    private RelationType relationType;
    @ApiModelProperty(notes = "广告状态（ON- 开启 OFF-关闭）", dataType = "AdvertiseStatus")
    private OnOff advertiseStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(notes = "起始创建时间", dataType = "Date", example = "yyyy-MM-dd HH:mm:ss")
    private Date beginCreateAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(notes = "截止创建时间", dataType = "Date", example = "yyyy-MM-dd HH:mm:ss")
    private Date endCreateAt;
    @ApiModelProperty(notes = "每页查询条数(为空或0不分页查所有)", dataType = "Integer")
    private Integer rows;
    @ApiModelProperty(notes = "当前页", dataType = "Integer")
    private Integer page;
}
