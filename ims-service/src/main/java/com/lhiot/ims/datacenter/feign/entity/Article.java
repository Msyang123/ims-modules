package com.lhiot.ims.datacenter.feign.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhiot.ims.datacenter.feign.type.ArticleStatus;
import com.lhiot.ims.datacenter.feign.type.ArticleType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author xiaojian  created in  2018/11/21 17:07
 */
@Data
@ApiModel
public class Article {
    @ApiModelProperty(notes = "主键Id", dataType = "Long", readOnly = true)
    private Long id;
    @ApiModelProperty(notes = "作者", dataType = "String")
    private String author;
    @ApiModelProperty(notes = "编辑人", dataType = "String")
    private String editor;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(notes = "创建时间", dataType = "Date", readOnly = true, example = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;
    @ApiModelProperty(notes = "文章类型（ORIGINAL-原创  REPRINTED-转载）", dataType = "ArticleType")
    private ArticleType articleType;
    @ApiModelProperty(notes = "发布状态（PUBLISH-发布  UN_PUBLISH-未发布）", dataType = "ArticleStatus")
    private ArticleStatus articleStatus;
    @NotNull(message = "文章内容不能为空")
    @ApiModelProperty(notes = "文章内容", dataType = "String")
    private String content;
    @NotNull(message = "文章标题不能为空")
    @ApiModelProperty(notes = "文章标题", dataType = "String")
    private String title;
    @ApiModelProperty(notes = "内容简介图片url", dataType = "String")
    private String contentImage;
    @ApiModelProperty(notes = "内容首部图片url", dataType = "String")
    private String headImage;
    @ApiModelProperty(notes = "文章链接地址", dataType = "String")
    private String url;
    @ApiModelProperty(notes = "文章简介", dataType = "String")
    private String introduce;
    @ApiModelProperty(notes = "审核人", dataType = "String")
    private String auditor;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(notes = "编辑时间", dataType = "Date", example = "yyyy-MM-dd HH:mm:ss")
    private Date editAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(notes = "发布时间", dataType = "Date", example = "yyyy-MM-dd HH:mm:ss")
    private Date publishAt;
    @ApiModelProperty(notes = "关键字", dataType = "String")
    private String keywords;
    @ApiModelProperty(notes = "阅读量", dataType = "Long")
    private Long readAmount;
    @ApiModelProperty(notes = "jobTitle", dataType = "String")
    private String jobTitle;

}
