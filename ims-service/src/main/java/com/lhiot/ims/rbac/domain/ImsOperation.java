package com.lhiot.ims.rbac.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lhiot.ims.rbac.common.PagerRequestObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
* Description:功能操作实体类
* @author yijun
* @date 2018/09/29
*/
@Data
@ToString(callSuper = true)
@ApiModel
@NoArgsConstructor
public class ImsOperation extends PagerRequestObject {

    /**
    *id
    */
    @JsonProperty("id")
    @ApiModelProperty(value = "id", dataType = "Long")
    private Long id;

    /**
    *菜单id
    */
    @JsonProperty("menuId")
    @ApiModelProperty(value = "菜单id", dataType = "Long")
    private Long menuId;

    /**
    *操作名称
    */
    @JsonProperty("name")
    @ApiModelProperty(value = "操作名称", dataType = "String")
    private String name;

    /**
    *拦截地址
    */
    @JsonProperty("antUrl")
    @ApiModelProperty(value = "拦截地址", dataType = "String")
    private String antUrl;

    /**
    *操作类型
    */
    @JsonProperty("type")
    @ApiModelProperty(value = "操作类型", dataType = "String")
    private String type;

}
