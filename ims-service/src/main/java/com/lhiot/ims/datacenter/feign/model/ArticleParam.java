package com.lhiot.ims.datacenter.feign.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhiot.ims.datacenter.feign.type.ArticleStatus;
import com.lhiot.ims.datacenter.feign.type.ArticleType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author xiaojian  created in  2018/11/22 9:33
 */
@ApiModel
@Data
public class ArticleParam {
    @ApiModelProperty(notes = "作者", dataType = "String")
    private String author;
    @ApiModelProperty(notes = "编辑人", dataType = "String")
    private String editor;
    @ApiModelProperty(notes = "版块ID", dataType = "Long")
    private Long sectionId;
    @ApiModelProperty(notes = "文章类型（ORIGINAL-原创  REPRINTED-转载）", dataType = "ArticleType")
    private ArticleType articleType;
    @ApiModelProperty(notes = "发布状态（PUBLISH-发布  UN_PUBLISH-未发布）", dataType = "ArticleStatus")
    private ArticleStatus articleStatus;
    @ApiModelProperty(notes = "文章标题", dataType = "String")
    private String title;
    @ApiModelProperty(notes = "关键字", dataType = "String")
    private String keywords;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(notes = "起始创建时间", dataType = "Date", example = "yyyy-MM-dd HH:mm:ss")
    private Date beginCreateAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(notes = "截止创建时间", dataType = "Date", example = "yyyy-MM-dd HH:mm:ss")
    private Date endCreateAt;
    @ApiModelProperty(notes = "起始发布时间", dataType = "Date", example = "yyyy-MM-dd HH:mm:ss")
    private Date beginPublishAt;
    @ApiModelProperty(notes = "截止发布时间", dataType = "Date", example = "yyyy-MM-dd HH:mm:ss")
    private Date endPublishAt;
    @ApiModelProperty(notes = "每页查询条数(为空或0不分页查所有)", dataType = "Integer")
    private Integer rows;
    @ApiModelProperty(notes = "当前页", dataType = "Integer")
    private Integer page;
}
