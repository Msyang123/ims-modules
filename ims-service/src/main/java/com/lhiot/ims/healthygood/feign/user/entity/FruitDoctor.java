package com.lhiot.ims.healthygood.feign.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lhiot.ims.healthygood.feign.user.type.DoctorStatus;
import com.lhiot.ims.healthygood.feign.user.type.Hot;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Description:鲜果师成员实体类
 *
 * @author yijun
 * @date 2018/07/26
 */
@Data
@ToString(callSuper = true)
@ApiModel
@NoArgsConstructor
public class FruitDoctor {

    @ApiModelProperty(value = "id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "鲜果师姓名", dataType = "String")
    private String realName;

    @ApiModelProperty(value = "鲜果师职业照", dataType = "String")
    private String photo;

    @ApiModelProperty(value = "鲜果师邀请码", dataType = "String")
    private String inviteCode;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "鲜果师关联用户编号", dataType = "Long")
    private Long userId;

    @ApiModelProperty(value = "鲜果师等级 培训中TRAINING、初级PRIMARY 中高级 SENIOR", dataType = "String")
    private String doctorLevel;

    @ApiModelProperty(value = "鲜果师等级列表 培训中TRAINING、初级PRIMARY 中高级 SENIOR", dataType = "String[]")
    private String[] doctorLevels;

    @ApiModelProperty(value = "鲜果师状态 VALID正常  INVALID已停用", dataType = "DoctorStatus")
    private DoctorStatus doctorStatus;

    @ApiModelProperty(value = "鲜果师头衔", dataType = "String")
    private String jobTitle;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "注册时间", dataType = "Date")
    private java.util.Date createAt;

    @ApiModelProperty(value = "推荐人（鲜果师编号）", dataType = "Long")
    private Long refereeId;

    @ApiModelProperty(value = "个人简介", dataType = "String")
    private String profile;

    @ApiModelProperty(value = "银行卡卡号", dataType = "String")
    private String cardNo;

    @ApiModelProperty(value = "开户行", dataType = "String")
    private String bankDeposit;

    @ApiModelProperty(value = "持卡人姓名", dataType = "String")
    private String cardUsername;

    @ApiModelProperty(value = "明星鲜果师 NO-普通鲜果师，YES-明星鲜果师", dataType = "Hot")
    private Hot hot;

    @ApiModelProperty(value = "剩余可结算金额", dataType = "Integer")
    private Integer balance;

    @ApiModelProperty(value = "当月销售额", dataType = "Integer")
    private Integer amountOfMonth;

    @ApiModelProperty(value = "头像", dataType = "String")
    private String avatar;

    @ApiModelProperty(value = "鲜果师半身照", dataType = "String")
    private String upperbodyPhoto;

    @ApiModelProperty(value = "审核通过的手机号", dataType = "String")
    private String phone;

    @ApiModelProperty(value = "审核通过记录id", dataType = "Long")
    private Long applicationId;

    @ApiModelProperty(notes = "每页查询条数(为空或0不分页查所有)", dataType = "Integer")
    private Integer rows;

    @ApiModelProperty(notes = "当前页", dataType = "Integer")
    private Integer page;
}
