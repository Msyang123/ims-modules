package com.lhiot.ims.rbac.mapper;

import com.lhiot.ims.rbac.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface AdminMapper {

    Admin selectByAccount(String account);

    void updateLastLogin(Map<String, Object> params);
}
