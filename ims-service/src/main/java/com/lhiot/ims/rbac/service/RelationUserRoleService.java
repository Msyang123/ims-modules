package com.lhiot.ims.rbac.service;

import com.lhiot.ims.rbac.domain.RelationUserRole;
import com.lhiot.ims.rbac.mapper.RelationUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
* Description:用户与角色关联服务类
* @author yijun
* @date 2018/09/29
*/
@Service
@Transactional
public class RelationUserRoleService {

    private final RelationUserRoleMapper relationUserRoleMapper;

    @Autowired
    public RelationUserRoleService(RelationUserRoleMapper relationUserRoleMapper) {
        this.relationUserRoleMapper = relationUserRoleMapper;
    }

    /** 
    * Description:新增用户与角色关联
    *  
    * @param relationUserRole
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */  
    public int create(RelationUserRole relationUserRole){
        return this.relationUserRoleMapper.create(relationUserRole);
    }

    /** 
    * Description:根据id修改用户与角色关联
    *  
    * @param relationUserRole
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public int updateById(RelationUserRole relationUserRole){
        return this.relationUserRoleMapper.updateById(relationUserRole);
    }

    /** 
    * Description:根据ids删除用户与角色关联
    *  
    * @param ids
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public int deleteByIds(String ids){
        return this.relationUserRoleMapper.deleteByIds(Arrays.asList(ids.split(",")));
    }
    
    /** 
    * Description:根据id查找用户与角色关联
    *  
    * @param id
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public RelationUserRole selectById(Long id){
        return this.relationUserRoleMapper.selectById(id);
    }


}

