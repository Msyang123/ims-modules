package com.lhiot.ims.rbac.domain;

import com.leon.microx.util.auditing.MD5;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Leon (234239150@qq.com) created in 12:20 18.8.26
 */
@Data
@ApiModel
@ToString
public class AdminLogin {

    @NotNull
    @ApiModelProperty(notes = "登录帐号", dataType = "String", required = true)
    @Pattern(regexp = "^[a-zA-Z0-9_]{6,16}$", message = "用户名6到16位（字母，数字，下划线）")
    private String account;

    @NotNull
    @ApiModelProperty(notes = "登录密码", dataType = "String", required = true)
    @Pattern(regexp = "^.*(?=.{6,16})(?=.*\\d)(?=.*[A-Z])(?=.*[a-z]).*$", message = "密码强度6到16位，包括至少1个大写字母，1个小写字母，1个数字")
    private String password;

}
