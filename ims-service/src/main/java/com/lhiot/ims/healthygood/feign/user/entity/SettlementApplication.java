package com.lhiot.ims.healthygood.feign.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhiot.ims.healthygood.feign.user.type.SettlementStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * Description:结算申请实体类
 *
 * @author yijun
 * @date 2018/07/26
 */
@Data
@ToString(callSuper = true)
@ApiModel
@NoArgsConstructor
public class SettlementApplication {

    @ApiModelProperty(value = "id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "申请人（鲜果师编号）", dataType = "Long")
    private Long doctorId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "申请时间", dataType = "Date")
    private java.util.Date createAt;

    @ApiModelProperty(value = "申请提取金额", dataType = "Integer")
    private Integer amount;

    @ApiModelProperty(value = "结算状态(UNSETTLED-未处理  SUCCESS-已成功  EXPIRED-已过期)", dataType = "SettlementStatus")
    private SettlementStatus settlementStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "处理时间", dataType = "Date")
    private java.util.Date dealAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(notes = "起始创建时间(用于搜索)", dataType = "Date")
    private Date beginCreateAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(notes = "截止创建时间(用于搜索)", dataType = "Date")
    private Date endCreateAt;

    @ApiModelProperty(value = "申请人（鲜果师名称）", dataType = "String")
    private String realName;

    @ApiModelProperty(value = "申请人（鲜果师手机号）", dataType = "String")
    private String phone;

    @ApiModelProperty(value = "银行卡卡号", dataType = "String")
    private String cardNo;

    @ApiModelProperty(value = "开户行", dataType = "String")
    private String bankDeposit;

    @ApiModelProperty(value = "持卡人姓名", dataType = "String")
    private String cardUsername;

    @ApiModelProperty(notes = "每页查询条数(为空或0不分页查所有)", dataType = "Integer")
    private Integer rows;

    @ApiModelProperty(notes = "当前页", dataType = "Integer")
    private Integer page;
}
