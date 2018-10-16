package com.lhiot.ims.rbac.mapper;

import com.lhiot.ims.rbac.domain.ImsRole;
import com.lhiot.ims.rbac.domain.ImsUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* Description:用户Mapper类
* @author yijun
* @date 2018/09/29
*/
@Mapper
public interface ImsUserMapper {

    /**
    * Description:新增用户
    *
    * @param imsUser
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int create(ImsUser imsUser);

    /**
    * Description:根据id修改用户
    *
    * @param imsUser
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int updateById(ImsUser imsUser);

    /**
    * Description:根据ids删除用户
    *
    * @param ids
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int deleteByIds(java.util.List<String> ids);

    /**
    * Description:根据id查找用户
    *
    * @param id
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    ImsUser selectById(Long id);


    /**
     * 依据账户查找个人信息
     * @param account
     * @return
     */
    ImsUser selectByAccount(String account);

    /**
    * Description:查询用户列表
    *
    * @param imsUser
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
     List<ImsUser> pageImsUsers(ImsUser imsUser);


    /**
    * Description: 查询用户总记录数
    *
    * @param imsUser
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int pageImsUserCounts(ImsUser imsUser);

    /**
     * Description: 查询用户id查询已关联的角色列表
     *
     * @param id
     * @return
     */
    List<ImsRole> getRelationRolesById(Long id);
}
