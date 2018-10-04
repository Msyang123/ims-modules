package com.lhiot.ims.rbac.service;

import java.util.Arrays;

import com.leon.microx.util.auditing.MD5;
import com.lhiot.ims.rbac.domain.ImsUser;
import com.lhiot.ims.rbac.mapper.ImsRelationUserRoleMapper;
import com.lhiot.ims.rbac.mapper.ImsUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lhiot.ims.rbac.common.PagerResultObject;

/**
* Description:用户服务类
* @author yijun
* @date 2018/09/29
*/
@Service
@Transactional
public class ImsUserService {

    private final ImsUserMapper imsUserMapper;
    private final ImsRelationUserRoleMapper imsRelationUserRoleMapper;

    @Autowired
    public ImsUserService(ImsUserMapper imsUserMapper, ImsRelationUserRoleMapper imsRelationUserRoleMapper) {
        this.imsUserMapper = imsUserMapper;
        this.imsRelationUserRoleMapper = imsRelationUserRoleMapper;
    }

    /** 
    * Description:新增用户
    *  
    * @param imsUser
    * @return
    */
    public int create(ImsUser imsUser){
        imsUser.setPassword(MD5.str(imsUser.getPassword()));
        return this.imsUserMapper.create(imsUser);
    }

    /** 
    * Description:根据id修改用户
    *  
    * @param imsUser
    * @return
    */
    public int updateById(ImsUser imsUser){
        return this.imsUserMapper.updateById(imsUser);
    }

    /** 
    * Description:根据ids删除用户
    *  
    * @param ids
    * @return
    */
    public int deleteByIds(String ids){
        //有关联角色关系直接删除
        this.imsRelationUserRoleMapper.deleteByUserIds(Arrays.asList(ids.split(",")));
        return this.imsUserMapper.deleteByIds(Arrays.asList(ids.split(",")));
    }
    
    /** 
    * Description:根据id查找用户
    *  
    * @param id
    * @return
    */
    public ImsUser selectById(Long id){
        return this.imsUserMapper.selectById(id);
    }

    /**
     * 依据用户账户查询用户信息
     * @param account
     * @return
     */
    public ImsUser selectByAccount(String account){
        return this.imsUserMapper.selectByAccount(account);
    }


    /** 
    * Description: 查询用户总记录数
    *  
    * @param imsUser
    * @return
    */
    public long count(ImsUser imsUser){
        return this.imsUserMapper.pageImsUserCounts(imsUser);
    }
    
    /** 
    * Description: 查询用户分页列表
    *  
    * @param imsUser
    * @return
    */
    public PagerResultObject<ImsUser> pageList(ImsUser imsUser) {
       long total = 0;
       if (imsUser.getRows() != null && imsUser.getRows() > 0) {
           total = this.count(imsUser);
       }
       return PagerResultObject.of(imsUser, total,
              this.imsUserMapper.pageImsUsers(imsUser));
    }
}

