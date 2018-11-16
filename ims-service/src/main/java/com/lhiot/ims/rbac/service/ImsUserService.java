package com.lhiot.ims.rbac.service;

import com.leon.microx.web.result.Pages;
import com.leon.microx.util.StringUtils;
import com.leon.microx.util.auditing.MD5;
import com.lhiot.ims.rbac.domain.ImsRole;
import com.lhiot.ims.rbac.domain.ImsUser;
import com.lhiot.ims.rbac.mapper.ImsRelationUserRoleMapper;
import com.lhiot.ims.rbac.mapper.ImsUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    public ImsUser create(ImsUser imsUser){
        imsUser.setPassword(MD5.str(imsUser.getPassword()));
        this.imsUserMapper.create(imsUser);
        return imsUser;
    }

    /** 
    * Description:根据id修改用户
    *  
    * @param imsUser
    * @return
    */
    public ImsUser updateById(ImsUser imsUser){
        this.imsUserMapper.updateById(imsUser);
        return imsUser;
    }

    /** 
    * Description:根据ids删除用户
    *  
    * @param ids
    * @return
    */
    public int deleteByIds(String ids){
        //有关联角色关系直接删除
        this.imsRelationUserRoleMapper.deleteByUserIds(Arrays.asList(StringUtils.tokenizeToStringArray(ids, ",")));
        return this.imsUserMapper.deleteByIds(Arrays.asList(StringUtils.tokenizeToStringArray(ids, ",")));
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
    public Pages<ImsUser> pageList(ImsUser imsUser) {
       return Pages.of(this.imsUserMapper.pageImsUserCounts(imsUser),
              this.imsUserMapper.pageImsUsers(imsUser));
    }

    /**
     * Description: 查询用户id查询已关联的角色列表
     *
     * @param id
     * @return
     */
    public List<ImsRole> getRelationRolesById(Long id) {

        return  this.imsUserMapper.getRelationRolesById(id);
    }

    /**
     * Description: 更新用户最后登录时间
     *
     * @param params
     */
    public void updateLastLogin(Map<String, Object> params) {
        this.imsUserMapper.updateLastLogin(params);
    }

}

