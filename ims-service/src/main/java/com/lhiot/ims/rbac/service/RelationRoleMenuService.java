package com.lhiot.ims.rbac.service;

import java.util.Arrays;
import java.util.List;
import com.lhiot.ims.rbac.domain.RelationRoleMenu;
import com.lhiot.ims.rbac.mapper.RelationRoleMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lhiot.ims.rbac.common.PagerResultObject;

/**
* Description:角色与菜单关联服务类
* @author yijun
* @date 2018/09/29
*/
@Service
@Transactional
public class RelationRoleMenuService {

    private final RelationRoleMenuMapper relationRoleMenuMapper;

    @Autowired
    public RelationRoleMenuService(RelationRoleMenuMapper relationRoleMenuMapper) {
        this.relationRoleMenuMapper = relationRoleMenuMapper;
    }

    /** 
    * Description:新增角色与菜单关联
    *  
    * @param relationRoleMenu
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */  
    public int create(RelationRoleMenu relationRoleMenu){
        return this.relationRoleMenuMapper.create(relationRoleMenu);
    }

    /** 
    * Description:根据id修改角色与菜单关联
    *  
    * @param relationRoleMenu
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public int updateById(RelationRoleMenu relationRoleMenu){
        return this.relationRoleMenuMapper.updateById(relationRoleMenu);
    }

    /** 
    * Description:根据ids删除角色与菜单关联
    *  
    * @param ids
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public int deleteByIds(String ids){
        return this.relationRoleMenuMapper.deleteByIds(Arrays.asList(ids.split(",")));
    }
    
    /** 
    * Description:根据id查找角色与菜单关联
    *  
    * @param id
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public RelationRoleMenu selectById(Long id){
        return this.relationRoleMenuMapper.selectById(id);
    }

}

