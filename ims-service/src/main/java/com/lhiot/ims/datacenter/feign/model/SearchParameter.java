package com.lhiot.ims.datacenter.feign.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author Leon (234239150@qq.com) created in 15:58 18.10.15
 */
@Data
@ApiModel
public class SearchParameter {

    private boolean includeChildren;

    private String dictionaryCode;

    private String entryCode;

    private Long page;

    private Long rows;

}
