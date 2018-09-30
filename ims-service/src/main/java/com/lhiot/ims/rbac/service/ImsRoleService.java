package com.lhiot.ims.rbac.service;

import java.util.Arrays;
import java.util.List;

import com.lhiot.ims.rbac.domain.ImsRelationUserRole;
import com.lhiot.ims.rbac.domain.ImsRole;
import com.lhiot.ims.rbac.mapper.ImsRelationRoleMenuMapper;
import com.lhiot.ims.rbac.mapper.ImsRelationRoleOperationMapper;
import com.lhiot.ims.rbac.mapper.ImsRelationUserRoleMapper;
import com.lhiot.ims.rbac.mapper.ImsRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lhiot.ims.rbac.common.PagerResultObject;

/**
* Description:角色服务类
* @author yijun
* @date 2018/09/29
*/
@Service
@Transactional
public class ImsRoleService {

    private final ImsRoleMapper imsRoleMapper;
    private final ImsRelationUserRoleMapper imsRelationUserRoleMapper;
    private final ImsRelationRoleMenuMapper imsRelationRoleMenuMapper;
    private final ImsRelationRoleOperationMapper imsRelationRoleOperationMapper;

    @Autowired
    public ImsRoleService(ImsRoleMapper imsRoleMapper, ImsRelationUserRoleMapper imsRelationUserRoleMapper, ImsRelationRoleMenuMapper imsRelationRoleMenuMapper, ImsRelationRoleOperationMapper imsRelationRoleOperationMapper) {
        this.imsRoleMapper = imsRoleMapper;
        this.imsRelationUserRoleMapper = imsRelationUserRoleMapper;
        this.imsRelationRoleMenuMapper = imsRelationRoleMenuMapper;
        this.imsRelationRoleOperationMapper = imsRelationRoleOperationMapper;
    }

    /** 
    * Description:新增角色
    *  
    * @param imsRole
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */  
    public int create(ImsRole imsRole){
        return this.imsRoleMapper.create(imsRole);
    }

    /** 
    * Description:根据id修改角色
    *  
    * @param imsRole
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public int updateById(ImsRole imsRole){
        return this.imsRoleMapper.updateById(imsRole);
    }

    /** 
    * Description:根据ids删除角色 如果角色已经有对应用户关系存在，则不允许删除，否则能删除，删除时同时删除角色与菜单对应关系以及与操作对应关系
    *  
    * @param ids
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public int deleteByIds(String ids){

        //检查是否有关联数据的角色
        for(String id:ids.split(",")){
            ImsRelationUserRole relationUserRole=new ImsRelationUserRole();
            relationUserRole.setRoleId(Long.valueOf(id));
            long count = imsRelationUserRoleMapper.pageImsRelationUserRoleCounts(relationUserRole);
            //不允许删除有关联用户数据的角色
            if(count>0){
                return 0;
            }
        }
        List<String> idArr = Arrays.asList(ids.split(","));
        int result =this.imsRoleMapper.deleteByIds(idArr);
        if(result>0){
            //删除时同时删除角色与菜单对应关系以及与操作对应关系
            this.imsRelationRoleMenuMapper.deleteByRoleIds(idArr);
            this.imsRelationRoleOperationMapper.deleteByRoleIds(idArr);
        }
        return result;
    }
    
    /** 
    * Description:根据id查找角色
    *  
    * @param id
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public ImsRole selectById(Long id){
        return this.imsRoleMapper.selectById(id);
    }

    /** 
    * Description: 查询角色总记录数
    *  
    * @param imsRole
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */  
    public long count(ImsRole imsRole){
        return this.imsRoleMapper.pageImsRoleCounts(imsRole);
    }
    
    /** 
    * Description: 查询角色分页列表
    *  
    * @param imsRole
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */  
    public PagerResultObject<ImsRole> pageList(ImsRole imsRole) {
       long total = 0;
       if (imsRole.getRows() != null && imsRole.getRows() > 0) {
           total = this.count(imsRole);
       }
       return PagerResultObject.of(imsRole, total,
              this.imsRoleMapper.pageImsRoles(imsRole));
    }
}
