package com.lhiot.ims.rbac.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
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
     'meta': {
     'title': '组件',
     'icon': 'logo-buffer'
     }
     }
     */
    /**
     * id
     */
    public MenuDisplay(Long id, Long parentid, String code, String title, String icon) {
        this.id = id;
        this.parentid = parentid;
        this.code = code;
        this.meta = new Meta(title, icon);
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
     * 菜单附加参数
     */
    @JsonProperty("meta")
    @ApiModelProperty(value = "菜单附加参数", dataType = "Meta")
    private Meta meta;

    @Data
    public class Meta {
        public Meta(String title, String icon) {
            this.title = title;
            this.icon = icon;
        }

        private String title;
        private String icon;
    }


}
