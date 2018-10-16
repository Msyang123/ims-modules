package com.lhiot.ims.rbac.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ImsRelationRoleMenu{

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

    @JsonIgnore
    @ApiModelProperty(value = "当前页,默认值1")
    private Long page = 1L;

    /**
     * 传入-1可不分页
     */
    @JsonIgnore
    @ApiModelProperty(value = "每页显示条数,默认值10")
    private Long rows = 10L;
}
