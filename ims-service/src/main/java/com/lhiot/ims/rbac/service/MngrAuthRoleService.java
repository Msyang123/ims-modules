package com.lhiot.ims.rbac.service;

import java.util.Arrays;
import java.util.List;
import com.lhiot.ims.rbac.domain.MngrAuthRole;
import com.lhiot.ims.rbac.mapper.MngrAuthRoleMapper;
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
public class MngrAuthRoleService {

    private final MngrAuthRoleMapper mngrAuthRoleMapper;

    @Autowired
    public MngrAuthRoleService(MngrAuthRoleMapper mngrAuthRoleMapper) {
        this.mngrAuthRoleMapper = mngrAuthRoleMapper;
    }

    /** 
    * Description:新增角色
    *  
    * @param mngrAuthRole
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */  
    public int create(MngrAuthRole mngrAuthRole){
        return this.mngrAuthRoleMapper.create(mngrAuthRole);
    }

    /** 
    * Description:根据id修改角色
    *  
    * @param mngrAuthRole
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public int updateById(MngrAuthRole mngrAuthRole){
        return this.mngrAuthRoleMapper.updateById(mngrAuthRole);
    }

    /** 
    * Description:根据ids删除角色
    *  
    * @param ids
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public int deleteByIds(String ids){
        return this.mngrAuthRoleMapper.deleteByIds(Arrays.asList(ids.split(",")));
    }
    
    /** 
    * Description:根据id查找角色
    *  
    * @param id
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public MngrAuthRole selectById(Long id){
        return this.mngrAuthRoleMapper.selectById(id);
    }

    /** 
    * Description: 查询角色总记录数
    *  
    * @param mngrAuthRole
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */  
    public long count(MngrAuthRole mngrAuthRole){
        return this.mngrAuthRoleMapper.pageMngrAuthRoleCounts(mngrAuthRole);
    }
    
    /** 
    * Description: 查询角色分页列表
    *  
    * @param mngrAuthRole
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */  
    public PagerResultObject<MngrAuthRole> pageList(MngrAuthRole mngrAuthRole) {
       long total = 0;
       if (mngrAuthRole.getRows() != null && mngrAuthRole.getRows() > 0) {
           total = this.count(mngrAuthRole);
       }
       return PagerResultObject.of(mngrAuthRole, total,
              this.mngrAuthRoleMapper.pageMngrAuthRoles(mngrAuthRole));
    }
}

