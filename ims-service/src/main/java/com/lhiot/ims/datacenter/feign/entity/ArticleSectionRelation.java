package com.lhiot.ims.datacenter.feign.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xiaojian  created in  2018/11/22 10:30
 */
@Data
@ApiModel
public class ArticleSectionRelation {
    @ApiModelProperty(notes = "主键Id", dataType = "Long", readOnly = true)
    private Long id;
    @NotNull(message = "板块id不能为空")
    @ApiModelProperty(notes = "板块id", dataType = "Long")
    private Long sectionId;
    @NotNull(message = "文章id不能为空")
    @ApiModelProperty(notes = "文章id", dataType = "Long")
    private Long articleId;
    @ApiModelProperty(notes = "序号", dataType = "Integer")
    private Integer sorting;
}
