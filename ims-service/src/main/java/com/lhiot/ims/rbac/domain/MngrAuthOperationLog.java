package com.lhiot.ims.rbac.domain;

import com.lhiot.ims.rbac.common.PagerRequestObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true)
public class MngrAuthOperationLog extends PagerRequestObject{

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

}
