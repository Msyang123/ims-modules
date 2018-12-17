package com.lhiot.ims.datacenter.feign.type;

public enum CoordinateType {
    GCJ02("高德坐标系/火星坐标系"),
    BD09("百度坐标系"),
    WGS84("GPS坐标系")
    ;

    private String description;

    CoordinateType(String description) {
        this.description = description;
    }
}
