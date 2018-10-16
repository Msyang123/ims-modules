package com.lhiot.ims.rbac.mapper;

import com.lhiot.ims.rbac.domain.ImsMenu;
import com.lhiot.ims.rbac.domain.ImsRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* Description:角色Mapper类
* @author yijun
* @date 2018/09/29
*/
@Mapper
public interface ImsRoleMapper {

    /**
    * Description:新增角色
    *
    * @param imsRole
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int create(ImsRole imsRole);

    /**
    * Description:根据id修改角色
    *
    * @param imsRole
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int updateById(ImsRole imsRole);

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
    ImsRole selectById(Long id);

    /**
    * Description:查询角色列表
    *
    * @param imsRole
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
     List<ImsRole> pageImsRoles(ImsRole imsRole);


    /**
    * Description: 查询角色总记录数
    *
    * @param imsRole
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int pageImsRoleCounts(ImsRole imsRole);

    /**
     * Description: 查询角色id查询已关联的菜单列表
     *
     * @param id
     * @return
     */
    List<ImsMenu> getRelationMenusById(Long id);
}
