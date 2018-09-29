package com.lhiot.ims.rbac.mapper;

import com.lhiot.ims.rbac.domain.MngrAuthRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* Description:角色Mapper类
* @author yijun
* @date 2018/09/29
*/
@Mapper
public interface MngrAuthRoleMapper {

    /**
    * Description:新增角色
    *
    * @param mngrAuthRole
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int create(MngrAuthRole mngrAuthRole);

    /**
    * Description:根据id修改角色
    *
    * @param mngrAuthRole
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int updateById(MngrAuthRole mngrAuthRole);

    /**
    * Description:根据ids删除角色
    *
    * @param ids
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int deleteByIds(java.util.List<String> ids);

    /**
    * Description:根据id查找角色
    *
    * @param id
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    MngrAuthRole selectById(Long id);

    /**
    * Description:查询角色列表
    *
    * @param mngrAuthRole
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
     List<MngrAuthRole> pageMngrAuthRoles(MngrAuthRole mngrAuthRole);


    /**
    * Description: 查询角色总记录数
    *
    * @param mngrAuthRole
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    long pageMngrAuthRoleCounts(MngrAuthRole mngrAuthRole);
}
