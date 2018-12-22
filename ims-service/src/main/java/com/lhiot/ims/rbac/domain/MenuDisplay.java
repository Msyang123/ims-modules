package com.lhiot.ims.rbac.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lhiot.ims.datacenter.feign.entity.ProductCategory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MenuDisplay {
    /**
     * {
     'id': '5',
     'parentid': 0,
     'code': 'components',
     'path': '/components',
     'title': '组件',
     'icon': 'logo-buffer'
     'meta': {
     'title': '组件',
     'icon': 'logo-buffer'
     },
     'type': 'PARENT'
     }
     */
    /**
     * id
     */
    public MenuDisplay(Long id, Long parentid, String code, String title, String icon,String type) {
        this.id = id;
        this.parentid = parentid;
        this.code = code;
        this.title = title;
        this.icon = icon;
        this.meta = new Meta(title, icon);
        this.type = type;
    }

    public MenuDisplay(ImsMenu menu) {
        this.id = menu.getId();
        this.parentid = menu.getPId();
        this.code = menu.getCode();
        this.title = menu.getName();
        this.icon = menu.getIcon();
        this.meta = new Meta(menu.getName(), menu.getIcon());
        this.type = menu.getType();
    }

    public MenuDisplay(ProductCategory productCategory) {
        this.id = productCategory.getId();
        this.parentid = productCategory.getParentId();
        this.code = null;
        this.title = productCategory.getGroupName();
        this.icon = null;
        this.meta = new Meta(productCategory.getGroupName(), null);
        this.type = productCategory.getParentId() == 0 ? "PARENT" : "SON";
    }

    @JsonProperty("id")
    @ApiModelProperty(value = "id", dataType = "Long")
    private Long id;

    /**
     * 父菜单id
     */
    @JsonProperty("parentid")
    @ApiModelProperty(value = "父菜单id", dataType = "Long")
    private Long parentid;

    /**
     * 菜单标识
     */
    @JsonProperty("code")
    @ApiModelProperty(value = "菜单标识", dataType = "String")
    private String code;

    /**
     * 菜单标题
     */
    @JsonProperty("title")
    @ApiModelProperty(value = "菜单标题", dataType = "String")
    private String title;

    /**
     * 图标
     */
    @JsonProperty("icon")
    @ApiModelProperty(value = "图标", dataType = "String")
    private String icon;

    /**
     * 父子级菜单标识
     */
    @JsonProperty("type")
    @ApiModelProperty(value = "父子级菜单标识", dataType = "String")
    private String type;


    /**
     * 菜单附加参数
     */
    @JsonProperty("meta")
    @ApiModelProperty(value = "菜单附加参数", dataType = "Meta")
    private Meta meta;

    @Data
    public static class Meta {
        public Meta(String title, String icon) {
            this.title = title;
            this.icon = icon;
        }

        private String title;
        private String icon;
    }


}
