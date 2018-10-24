package com.lhiot.ims.rbac.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
* Description:角色实体类
* @author yijun
* @date 2018/09/29
*/
@Data
@ToString(callSuper = true)
@ApiModel
@NoArgsConstructor
public class ImsRole {

    /**
    *id
    */
    @JsonProperty("id")
    @ApiModelProperty(value = "id", dataType = "Long")
    private Long id;

    /**
    *角色名
    */
    @JsonProperty("name")
    @ApiModelProperty(value = "角色名", dataType = "String")
    private String name;

    /**
    *角色状态
    */
    @JsonProperty("status")
    @ApiModelProperty(value = "角色状态", dataType = "String")
    private String status;

    /**
     *角色描述
     */
    @JsonProperty("roleDesc")
    @ApiModelProperty(value = "角色描述", dataType = "String")
    private String roleDesc;


    /**
     *创建人
     */
    @JsonProperty("createBy")
    @ApiModelProperty(value = "创建人", dataType = "String")
    private String createBy;


    /**
     *创建时间
     */
    @JsonProperty("createAt")
    @ApiModelProperty(value = "创建时间", dataType = "Date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private java.util.Date createAt;

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

    /** 分页sql获取起始行 */
    public Long getStartRow(){
        return ((rows != null && page != null) ? (page - 1) * rows : 0);
    }
}
