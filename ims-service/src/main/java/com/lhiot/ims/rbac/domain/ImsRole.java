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
* Description:角色实体类
* @author yijun
* @date 2018/09/29
*/
@Data
@ToString(callSuper = true)
@ApiModel
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ImsRole extends PagerRequestObject{

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

}
