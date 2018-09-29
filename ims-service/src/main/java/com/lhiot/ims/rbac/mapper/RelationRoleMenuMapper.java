package com.lhiot.ims.rbac.mapper;

import com.lhiot.ims.rbac.domain.RelationRoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* Description:角色与菜单关联Mapper类
* @author yijun
* @date 2018/09/29
*/
@Mapper
public interface RelationRoleMenuMapper {

    /**
    * Description:新增角色与菜单关联
    *
    * @param relationRoleMenu
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int create(RelationRoleMenu relationRoleMenu);

    /**
    * Description:根据id修改角色与菜单关联
    *
    * @param relationRoleMenu
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int updateById(RelationRoleMenu relationRoleMenu);

    /**
    * Description:根据ids删除角色与菜单关联
    *
    * @param ids
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int deleteByIds(java.util.List<String> ids);

    /**
    * Description:根据id查找角色与菜单关联
    *
    * @param id
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    RelationRoleMenu selectById(Long id);

    /**
    * Description:查询角色与菜单关联列表
    *
    * @param relationRoleMenu
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
     List<RelationRoleMenu> pageRelationRoleMenus(RelationRoleMenu relationRoleMenu);


    /**
    * Description: 查询角色与菜单关联总记录数
    *
    * @param relationRoleMenu
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    long pageRelationRoleMenuCounts(RelationRoleMenu relationRoleMenu);
}
