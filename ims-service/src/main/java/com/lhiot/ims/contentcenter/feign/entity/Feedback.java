package com.lhiot.ims.contentcenter.feign.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lhiot.ims.contentcenter.feign.type.FeedbackStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
* 描述：用户反馈
* @author yijun
* @date 2018-07-21
*/
@Data
@ToString
@ApiModel
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Feedback implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(notes = "", dataType = "Long")
    private Long id;

    @ApiModelProperty(notes = "标题", dataType = "String")
    private String title;

    @ApiModelProperty(notes = "内容", dataType = "String")
    private String content;

    @ApiModelProperty(notes = "客服反馈", dataType = "String")
    private String backMessage;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(notes = "创建时间", dataType = "Date")
    private Date createAt;

    @ApiModelProperty(notes = "反馈时间", dataType = "Date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date feedbackAt;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(notes = "反馈用户", dataType = "Long")
    private Long userId;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(notes = "应用类型", dataType = "String")
    private String applicationType;

    @ApiModelProperty(notes = "回复人", dataType = "String")
    private String backEditor;

    @ApiModelProperty(notes = "反馈状态(UNREPLY-未回复，REPLY-已回复且用户未读，READED-已读')", dataType = "FeedbackStatusEnum")
    private FeedbackStatusEnum status;

    @ApiModelProperty(notes = "每页查询条数(为空或0不分页查所有)", dataType = "Integer")
    private Integer rows;
    @ApiModelProperty(notes = "当前页", dataType = "Integer")
    private Integer page;


}
