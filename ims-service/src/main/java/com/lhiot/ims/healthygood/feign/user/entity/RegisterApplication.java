package com.lhiot.ims.healthygood.feign.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lhiot.ims.healthygood.feign.user.type.AuditStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * Description:鲜果师申请记录实体类
 *
 * @author hufan
 * @date 2018/07/26
 */
@Data
@ToString(callSuper = true)
@ApiModel
@NoArgsConstructor
public class RegisterApplication {

    @ApiModelProperty(value = "id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "身份证号", dataType = "String")
    private String idcardNo;

    @ApiModelProperty(value = "手机号码", dataType = "String")
    private String phone;

    @ApiModelProperty(value = "验证码", dataType = "String")
    private String verificationCode;

    @ApiModelProperty(value = "身份证正面", dataType = "String")
    private String idcardFront;

    @ApiModelProperty(value = "身份证反面", dataType = "String")
    private String idcardReverse;

    @ApiModelProperty(value = "资质证明", dataType = "String")
    private String credentials;

    @ApiModelProperty(value = "申请失败原因", dataType = "String")
    private String failureReason;

    @ApiModelProperty(value = "审核状态（UNAUDITED待审核  AGREE审核通过  REJECT审核不通过）", dataType = "AuditStatus")
    private AuditStatus auditStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "申请时间", dataType = "Date", example = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "姓名", dataType = "String")
    private String realName;

    @ApiModelProperty(value = "审核时间", dataType = "Date", example = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    @ApiModelProperty(value = "审核人", dataType = "String")
    private String auditUser;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "用户id", dataType = "Long")
    private Long userId;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "推荐人（鲜果师编号）", dataType = "Long")
    private Long refereeId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(notes = "起始创建时间", dataType = "Date", example = "yyyy-MM-dd HH:mm:ss")
    private Date beginCreateAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(notes = "截止创建时间", dataType = "Date", example = "yyyy-MM-dd HH:mm:ss")
    private Date endCreateAt;

    @ApiModelProperty(notes = "每页查询条数(为空或0不分页查所有)", dataType = "Integer")
    private Integer rows;

    @ApiModelProperty(notes = "当前页", dataType = "Integer")
    private Integer page;
}
