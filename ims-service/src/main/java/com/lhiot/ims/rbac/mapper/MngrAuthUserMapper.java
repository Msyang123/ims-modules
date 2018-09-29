package com.lhiot.ims.rbac.mapper;

import com.lhiot.ims.rbac.domain.MngrAuthUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* Description:用户Mapper类
* @author yijun
* @date 2018/09/29
*/
@Mapper
public interface MngrAuthUserMapper {

    /**
    * Description:新增用户
    *
    * @param mngrAuthUser
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int create(MngrAuthUser mngrAuthUser);

    /**
    * Description:根据id修改用户
    *
    * @param mngrAuthUser
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int updateById(MngrAuthUser mngrAuthUser);

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
    MngrAuthUser selectById(Long id);


    /**
     * 依据账户查找个人信息
     * @param account
     * @return
     */
    MngrAuthUser selectByAccount(String account);

    /**
    * Description:查询用户列表
    *
    * @param mngrAuthUser
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
     List<MngrAuthUser> pageMngrAuthUsers(MngrAuthUser mngrAuthUser);


    /**
    * Description: 查询用户总记录数
    *
    * @param mngrAuthUser
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    long pageMngrAuthUserCounts(MngrAuthUser mngrAuthUser);
}
