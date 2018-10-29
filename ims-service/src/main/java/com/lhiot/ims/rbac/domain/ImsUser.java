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
 * Description:用户实体类
 * @author yijun
 * @date 2018/09/29
 */
@Data
@ToString(callSuper = true)
@ApiModel
@NoArgsConstructor
public class ImsUser{

    /**
     *id
     */
    @JsonProperty("id")
    @ApiModelProperty(value = "id", dataType = "Long")
    private Long id;

    /**
     *姓名
     */
    @JsonProperty("name")
    @ApiModelProperty(value = "姓名", dataType = "String")
    private String name;

    /**
     *账号
     */
    @JsonProperty("account")
    @ApiModelProperty(value = "账号", dataType = "String")
    private String account;

    /**
     *密码
     */
    @JsonProperty("password")
    @ApiModelProperty(value = "密码", dataType = "String")
    private String password;

    /**
     *电话
     */
    @JsonProperty("tel")
    @ApiModelProperty(value = "电话", dataType = "String")
    private String tel;

    /**
     *用户头像url
     */
    @JsonProperty("avatarUrl")
    @ApiModelProperty(value = "用户头像url", dataType = "String")
    private String avatarUrl;

    /**
     *用户状态
     */
    @JsonProperty("status")
    @ApiModelProperty(value = "用户状态", dataType = "String")
    private String status;

    /**
     *创建时间
     */
    @JsonProperty("createAt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间", dataType = "Date")
    private java.util.Date createAt;


    /**
     *最后登录时间
     */
    @JsonProperty("lastLoginAt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "最后登录时间", dataType = "Date")
    private java.util.Date lastLoginAt;


    /**
     *备注
     */
    @JsonProperty("remark")
    @ApiModelProperty(value = "备注", dataType = "String")
    private String remark;

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
