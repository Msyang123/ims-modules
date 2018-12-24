package com.lhiot.ims.datacenter.feign.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.leon.microx.predefine.OnOff;
import com.lhiot.ims.datacenter.feign.type.AdvertiseType;
import com.lhiot.ims.datacenter.feign.type.RelationType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author xiaojian  created in  2018/11/21 10:11
 */
@Data
@ApiModel
public class Advertisement {
    @ApiModelProperty(notes = "主键Id", dataType = "Long", readOnly = true)
    private Long id;
    @ApiModelProperty(notes = "位置Id", dataType = "Long")
    private Long positionId;
    @ApiModelProperty(notes = "位置对象", dataType = "UiPosition", readOnly = true)
    private UiPosition uiPosition;
    @ApiModelProperty(notes = "广告内容 （广告图片或者广告文本）", dataType = "String")
    private String content;
    @ApiModelProperty(notes = "广告类别（IMAGE- 图片广告 TEXT -文字广告）", dataType = "AdvertiseType")
    private AdvertiseType advertiseType;
    @NotNull(message = "广告名不能为空")
    @ApiModelProperty(notes = "广告名", dataType = "String")
    private String advertiseName;
    @ApiModelProperty(notes = "广告关联", dataType = "String")
    private String advertiseRelation;
    @ApiModelProperty(notes = "广告关联类别（PRODUCT_DETAILS- 商品详情 PRODUCT_SECTION-商品版块 CUSTOM_PLAN-定制计划 CUSTOM_PLAN_SECTION-定制版块 ARTICLE_DETAILS-文章详情 STORE_LIVE_TELECAST- 门店直播 MORE_AMUSEMENT- 多娱 EXTERNAL_LINKS- 外部链接）", dataType = "RelationType")
    private RelationType relationType;
    @ApiModelProperty(notes = "广告状态（ON- 开启 OFF-关闭）", dataType = "AdvertiseStatus")
    private OnOff advertiseStatus;
    @ApiModelProperty(notes = "广告序号", dataType = "Integer")
    private Integer sorting;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(notes = "开启时间", dataType = "Date", example = "yyyy-MM-dd HH:mm:ss")
    private Date beginAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(notes = "关闭时间", dataType = "Date", example = "yyyy-MM-dd HH:mm:ss")
    private Date endAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(notes = "创建时间", dataType = "Date", readOnly = true, example = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    @ApiModelProperty(name = "有效期", dataType = "String", readOnly = true)
    private String validityPeriod;
    @ApiModelProperty(notes = "广告关联显示文字（链接目标）", dataType = "String")
    private String advertiseRelationText;
}
