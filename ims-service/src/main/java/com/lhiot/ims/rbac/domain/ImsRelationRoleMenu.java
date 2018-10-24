package com.lhiot.ims.rbac.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lhiot.ims.rbac.common.PagerRequestObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
* Description:角色与菜单关联实体类
* @author yijun
* @date 2018/09/29
*/
@Data
@ToString(callSuper = true)
@ApiModel
@NoArgsConstructor
public class ImsRelationRoleMenu extends PagerRequestObject {

    /**
    *id
    */
    @JsonProperty("id")
    @ApiModelProperty(value = "id", dataType = "Long")
    private Long id;

    /**
    *角色id
    */
    @JsonProperty("roleId")
    @ApiModelProperty(value = "角色id", dataType = "Long")
    private Long roleId;

    /**
    *菜单id
    */
    @JsonProperty("menuId")
    @ApiModelProperty(value = "菜单id", dataType = "Long")
    private Long menuId;

}
