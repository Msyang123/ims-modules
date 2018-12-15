package com.lhiot.ims.rbac.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
* Description:操作表日志实体类
* @author yijun
* @date 2018/09/29
*/
@Data
@ToString(callSuper = true)
@ApiModel
@NoArgsConstructor
public class ImsOperationLog {

    /**
    *id
    */
    @JsonProperty("id")
    @ApiModelProperty(value = "id", dataType = "Long")
    private Long id;

    /**
    *操作内容
    */
    @JsonProperty("content")
    @ApiModelProperty(value = "操作内容", dataType = "String")
    private String content;

    /**
    *创建时间
    */
    @JsonProperty("createAt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间", dataType = "Date")
    private java.util.Date createAt;
    

    /**
    *用户id
    */
    @JsonProperty("userId")
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "用户id", dataType = "Long")
    private Long userId;

    /**
    *操作ip
    */
    @JsonProperty("ip")
    @ApiModelProperty(value = "操作ip", dataType = "String")
    private String ip;

    /**
    *操作描述
    */
    @JsonProperty("description")
    @ApiModelProperty(value = "操作描述", dataType = "String")
    private String description;

    @JsonIgnore
    @JsonProperty("page")
    @ApiModelProperty(value = "当前页,默认值1")
    private Long page = 1L;

    /**
     * 传入-1可不分页
     */
    @JsonIgnore
    @JsonProperty("rows")
    @ApiModelProperty(value = "每页显示条数,默认值10")
    private Long rows = 10L;

    /**
     * 分页的起始行
     */
    @JsonIgnore
    @ApiModelProperty(value = "开始行数(执行sql时用)", hidden = true)
    private Long startRow = 0L;

    public Long getStartRow(){
        return ((rows != null && page != null) ? (page - 1) * rows : 0);
    }

}
