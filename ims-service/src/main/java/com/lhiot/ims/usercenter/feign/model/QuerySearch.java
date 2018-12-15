package com.lhiot.ims.usercenter.feign.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author hufan create in 9:39 2018/12/7
 */
@Data
public class QuerySearch {
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(notes = "用户id", dataType = "Long")
    private Long userId;

    @ApiModelProperty(notes = "手机号", dataType = "String")
    private String phone;

    @ApiModelProperty(notes = "用户昵称", dataType = "String")
    private String nickname;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(notes = "创建时间启", dataType = "Date")
    private Date createAtStart;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(notes = "创建时间止", dataType = "Date")
    private Date createAtEnd;

    @ApiModelProperty(notes = "每页查询条数(为空或0不分页查所有)", dataType = "Integer")
    private Integer rows;

    @ApiModelProperty(notes = "当前页", dataType = "Integer")
    private Integer page;

    @ApiModelProperty(notes = "应用类型", dataType = "String")
    private String applicationType;
}
