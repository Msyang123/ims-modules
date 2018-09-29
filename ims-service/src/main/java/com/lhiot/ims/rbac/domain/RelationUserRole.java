package com.lhiot.ims.rbac.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
* Description:用户与角色关联实体类
* @author yijun
* @date 2018/09/29
*/
@Data
@ToString(callSuper = true)
@ApiModel
@NoArgsConstructor
public class RelationUserRole{

    /**
    *id
    */
    @JsonProperty("id")
    @ApiModelProperty(value = "id", dataType = "Long")
    private Long id;

    /**
    *用户id
    */
    @JsonProperty("userId")
    @ApiModelProperty(value = "用户id", dataType = "Long")
    private Long userId;

    /**
    *角色id
    */
    @JsonProperty("roleId")
    @ApiModelProperty(value = "角色id", dataType = "Long")
    private Long roleId;

}
