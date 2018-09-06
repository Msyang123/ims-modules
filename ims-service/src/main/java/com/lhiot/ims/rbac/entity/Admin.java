package com.lhiot.ims.rbac.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.Instant;

/**
 * @author Leon (234239150@qq.com) created in 12:22 18.8.26
 */
@Data
@ToString
public class Admin implements Serializable {
    private String id;
    private String account;
    private String password;
    private String tel;
    private String avatarUrl;
    private Status status;
    private Instant createAt;
    private Instant lastLoginAt;
    private String remark;
}
