package com.lhiot.ims.healthygood.feign.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    /**
     * id
     */
    @JsonProperty("id")
    @ApiModelProperty(value = "id", dataType = "Long")
    private Long id;

    /**
     * 申请人（鲜果师编号）
     */
    @JsonProperty("doctorId")
    @ApiModelProperty(value = "申请人（鲜果师编号）", dataType = "Long")
    private Long doctorId;

    /**
     * 申请时间
     */
    @JsonProperty("createAt")
    @ApiModelProperty(value = "申请时间", dataType = "Date")
    private java.util.Date createAt;

    /**
     * 申请提取金额
     */
    @JsonProperty("amount")
    @ApiModelProperty(value = "申请提取金额", dataType = "Integer")
    private Integer amount;

    /**
     * 结算状态(UNSETTLED-未处理  SUCCESS-已成功  EXPIRED-已过期)
     */
    @JsonProperty("settlementStatus")
    @ApiModelProperty(value = "结算状态(UNSETTLED-未处理  SUCCESS-已成功  EXPIRED-已过期)", dataType = "SettlementStatus")
    private SettlementStatus settlementStatus;

    /**
     * 处理时间
     */
    @JsonProperty("dealAt")
    @ApiModelProperty(value = "处理时间", dataType = "Date")
    private java.util.Date dealAt;

    /**
     *起始创建时间
     */
    @JsonProperty("beginCreateAt")
    @ApiModelProperty(notes = "起始创建时间(用于搜索)", dataType = "Date")
    private Date beginCreateAt;

    /**
     *截止创建时间
     */
    @JsonProperty("endCreateAt")
    @ApiModelProperty(notes = "截止创建时间(用于搜索)", dataType = "Date")
    private Date endCreateAt;

    /**
     * 申请人（鲜果师编号）
     */
    @JsonProperty("realName")
    @ApiModelProperty(value = "申请人（鲜果师名称）", dataType = "String")
    private String realName;

    @ApiModelProperty(notes = "每页查询条数(为空或0不分页查所有)", dataType = "Integer")
    private Integer rows;
    @ApiModelProperty(notes = "当前页", dataType = "Integer")
    private Integer page;

    @ApiModelProperty(hidden = true)
    private Integer startRow;

    @JsonIgnore
    public Integer getStartRow() {
        if (this.rows != null && this.rows > 0) {
            return (this.page != null && this.page > 0 ? this.page - 1 : 0) * this.rows;
        }
        return null;
    }

}
