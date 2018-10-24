
package com.lhiot.ims.rbac.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description: 分页请求参数
 *
 * @author Limiaojun
 * @date: 2018-04-19 14:24:23
 * @version 1.0
 * @since JDK 1.8
 */
@Data
public class PagerRequestObject {

    public static final Long DEFAULT_PAGE = 1L;
    public static final Long DEFAULT_ROWS = 10L;
    public static final String DEFAULT_SORD = "asc";
    public static final Long DEFAULT_START_ROW = 0L;

    @JsonIgnore
    @JsonProperty("rows")
    @ApiModelProperty(value = "当前页,默认值1")
    private Long page = DEFAULT_PAGE;

    /** 传入-1可不分页 */
    @JsonIgnore
    @JsonProperty("rows")
    @ApiModelProperty(value = "每页显示条数,默认值10")
    private Long rows = DEFAULT_ROWS;

    @JsonIgnore
    @JsonProperty("rows")
    @ApiModelProperty(value = "排序字段")
    private String sidx;

    @JsonIgnore
    @ApiModelProperty(value = "排序类型(asc or desc),默认值asc", allowableValues = "asc,desc")
    private String sord = DEFAULT_SORD;

    @JsonIgnore
    @ApiModelProperty(value = "开始行数(执行sql时用)", hidden = true)
    private Long startRow = DEFAULT_START_ROW;

    /** 分页sql获取起始行 */
    public Long getStartRow() {
        return ((rows != null && page != null) ? (page - 1) * rows : 0);
    }
}
