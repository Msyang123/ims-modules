package com.lhiot.ims.rbac.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ImsRelationUserRole{

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

    /** 分页sql获取起始行 */
    public Long getStartRow(){
        return ((rows != null && page != null) ? (page - 1) * rows : 0);
    }

}
