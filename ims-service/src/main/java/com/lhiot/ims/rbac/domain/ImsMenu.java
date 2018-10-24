package com.lhiot.ims.rbac.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lhiot.ims.rbac.common.PagerRequestObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
* Description:菜单实体类
* @author yijun
* @date 2018/09/29
*/
@Data
@ToString(callSuper = true)
@ApiModel
@NoArgsConstructor
public class ImsMenu extends PagerRequestObject {

    /**
    *id
    */
    @JsonProperty("id")
    @ApiModelProperty(value = "id", dataType = "Long")
    private Long id;

    /**
    *父菜单id
    */
    @JsonProperty("parentid")
    @ApiModelProperty(value = "父菜单id", dataType = "Long")
    private Long pId;

    /**
    *菜单名
    */
    @JsonProperty("name")
    @ApiModelProperty(value = "菜单名", dataType = "String")
    private String name;

    /**
    *菜单标识
    */
    @JsonProperty("code")
    @ApiModelProperty(value = "菜单标识", dataType = "String")
    private String code;

    /**
    *菜单类型  PARENT-父级菜单  SON-叶子菜单
    */
    @JsonProperty("type")
    @ApiModelProperty(value = "菜单类型  PARENT-父级菜单  SON-叶子菜单", dataType = "String")
    private String type;

    /**
    *菜单排序
    */
    @JsonProperty("sort")
    @ApiModelProperty(value = "菜单排序", dataType = "Integer")
    private Integer sort;

    /**
    *菜单图标
    */
    @JsonProperty("icon")
    @ApiModelProperty(value = "菜单图标", dataType = "String")
    private String icon;

}
