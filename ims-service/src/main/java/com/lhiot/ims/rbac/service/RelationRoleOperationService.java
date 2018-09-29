package com.lhiot.ims.rbac.service;

import com.lhiot.ims.rbac.domain.RelationRoleOperation;
import com.lhiot.ims.rbac.mapper.RelationRoleOperationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
* Description:角色与操作关联服务类
* @author yijun
* @date 2018/09/29
*/
@Service
@Transactional
public class RelationRoleOperationService {

    private final RelationRoleOperationMapper relationRoleOperationMapper;

    @Autowired
    public RelationRoleOperationService(RelationRoleOperationMapper relationRoleOperationMapper) {
        this.relationRoleOperationMapper = relationRoleOperationMapper;
    }

    /** 
    * Description:新增角色与操作关联
    *  
    * @param relationRoleOperation
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */  
    public int create(RelationRoleOperation relationRoleOperation){
        return this.relationRoleOperationMapper.create(relationRoleOperation);
    }

    /** 
    * Description:根据id修改角色与操作关联
    *  
    * @param relationRoleOperation
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public int updateById(RelationRoleOperation relationRoleOperation){
        return this.relationRoleOperationMapper.updateById(relationRoleOperation);
    }

    /** 
    * Description:根据ids删除角色与操作关联
    *  
    * @param ids
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public int deleteByIds(String ids){
        return this.relationRoleOperationMapper.deleteByIds(Arrays.asList(ids.split(",")));
    }
    
    /** 
    * Description:根据id查找角色与操作关联
    *  
    * @param id
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public RelationRoleOperation selectById(Long id){
        return this.relationRoleOperationMapper.selectById(id);
    }

}

