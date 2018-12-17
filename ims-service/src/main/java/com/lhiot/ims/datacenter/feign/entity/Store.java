package com.lhiot.ims.datacenter.feign.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lhiot.dc.dictionary.HasEntries;
import com.lhiot.ims.datacenter.feign.type.CoordinateType;
import com.lhiot.ims.datacenter.feign.type.StoreStatus;
import com.lhiot.ims.datacenter.feign.type.StoreType;
import com.lhiot.util.DictionaryCodes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Description:门店实体类
 */
@Data
@ApiModel
public class Store {

    @ApiModelProperty(notes = "门店id", dataType = "Long")
    private Long id;

    @ApiModelProperty(notes = "门店编码", dataType = "String")
    private String code;

    @ApiModelProperty(notes = "门店名称", dataType = "String")
    private String name;

    @ApiModelProperty(notes = "门店地址", dataType = "String")
    private String address;

    @ApiModelProperty(notes = "联系方式", dataType = "String")
    private String phone;

    @ApiModelProperty(notes = "门店图片", dataType = "String")
    private String image;

    @ApiModelProperty(notes = "所属区域", dataType = "String")
    private String area;

    @ApiModelProperty(notes = "门店状态 ENABLED(营业),DISABLED(未营业);", dataType = "StoreStatusEnum")
    private StoreStatus status;

    @ApiModelProperty(notes = "旗舰店ID", dataType = "Long")
    private Long flagShip;

    /**
     * 门店类型：00-普通门店  01-旗舰店
     */
    @ApiModelProperty(notes = "门店类型：ORDINARY_STORE(普通门店),FLAGSHIP_STORE (旗舰店);", dataType = "StoreTypeEnum")
    private StoreType storeType;

    /**
     * 门店视频
     */
    @ApiModelProperty(notes = "门店视频", dataType = "String")
    private String videoUrl;

    /**
     * 直播开始时间
     */
    @ApiModelProperty(notes = "直播开始时间", dataType = "String")
    private String beginAt;

    /**
     * 直播结束时间
     */
    @ApiModelProperty(notes = "直播结束时间", dataType = "String")
    private String endAt;

    /**
     * 录播地址
     */
    @ApiModelProperty(notes = "录播地址", dataType = "String")
    private String tapeUrl;

    @ApiModelProperty(notes = "纬度", dataType = "BigDecimal")
    private BigDecimal latitude;


    @ApiModelProperty(notes = "经度", dataType = "BigDecimal")
    private BigDecimal longitude;

    @ApiModelProperty(notes = "启用该门店的应用类型", dataType = "String")
    @HasEntries(from = DictionaryCodes.APPLICATION_TYPE, message = "没有找到此应用类型")
    private String applicationType;


    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Double distance;

    @ApiModelProperty(notes = "坐标系", dataType = "CoordinateType")
    private CoordinateType coordinateType;
}
