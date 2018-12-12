package com.lhiot.ims.rbac.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lhiot.ims.datacenter.feign.type.ApplicationType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Description:菜单实体类
 *
 * @author yijun
 * @date 2018/09/29
 */
@Data
@ToString(callSuper = true)
@ApiModel
@NoArgsConstructor
public class ImsMenu {

    /**
     * id
     */
    @JsonProperty("id")
    @ApiModelProperty(value = "id", dataType = "Long")
    private Long id;

    /**
     * 父菜单id
     */
    @JsonProperty("parentid")
    @ApiModelProperty(value = "父菜单id", dataType = "Long")
    private Long pId;

    /**
     * 菜单名
     */
    @JsonProperty("name")
    @ApiModelProperty(value = "菜单名", dataType = "String")
    private String name;

    /**
     * 菜单标识
     */
    @JsonProperty("code")
    @ApiModelProperty(value = "菜单标识", dataType = "String")
    private String code;

    /**
     * 菜单类型  PARENT-父级菜单  SON-叶子菜单
     */
    @JsonProperty("type")
    @ApiModelProperty(value = "菜单类型  PARENT-父级菜单  SON-叶子菜单", dataType = "String")
    private String type;

    /**
     * 菜单排序
     */
    @JsonProperty("sort")
    @ApiModelProperty(value = "菜单排序", dataType = "Integer")
    private Integer sort;

    /**
     * 菜单图标
     */
    @JsonProperty("icon")
    @ApiModelProperty(value = "菜单图标", dataType = "String")
    private String icon;

    /**
     * 应用类型
     */
    @JsonProperty("applicationType")
    @ApiModelProperty(value = "应用类型", dataType = "ApplicationType")
    private ApplicationType applicationType;

    @JsonIgnore
    @JsonProperty("page")
    @ApiModelProperty(value = "当前页,默认值1")
    private Long page = 1L;

    /**
     * 传入-1可不分页
     */
    @JsonIgnore
    @JsonProperty("rows")
    @ApiModelProperty(value = "每页显示条数,默认值10")
    private Long rows = 10L;

    /**
     * 分页的起始行
     */
    @JsonIgnore
    @ApiModelProperty(value = "开始行数(执行sql时用)", hidden = true)
    private Long startRow = 0L;

    public Long getStartRow() {
        return ((rows != null && page != null) ? (page - 1) * rows : 0);
    }

}
