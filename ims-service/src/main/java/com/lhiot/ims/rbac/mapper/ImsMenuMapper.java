package com.lhiot.ims.rbac.mapper;

import com.lhiot.ims.rbac.domain.ImsMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Description:菜单Mapper类
 *
 * @author yijun
 * @date 2018/09/29
 */
@Mapper
public interface ImsMenuMapper {

    /**
     * Description:新增菜单
     *
     * @param imsMenu
     * @return
     * @author yijun
     * @date 2018/09/29 11:42:57
     */
    int create(ImsMenu imsMenu);

    /**
     * Description:根据id修改菜单
     *
     * @param imsMenu
     * @return
     * @author yijun
     * @date 2018/09/29 11:42:57
     */
    int updateById(ImsMenu imsMenu);

    /**
     * Description:根据ids删除菜单
     *
     * @param ids
     * @return
     * @author yijun
     * @date 2018/09/29 11:42:57
     */
    int deleteByIds(java.util.List<String> ids);

    /**
     * Description:根据id查找菜单
     *
     * @param id
     * @return
     * @author yijun
     * @date 2018/09/29 11:42:57
     */
    ImsMenu selectById(Long id);

    /**
     * Description:查询菜单列表
     *
     * @param imsMenu
     * @return
     * @author yijun
     * @date 2018/09/29 11:42:57
     */
    List<ImsMenu> pageImsMenus(ImsMenu imsMenu);


    /**
     * Description: 查询菜单总记录数
     *
     * @param imsMenu
     * @return
     * @author yijun
     * @date 2018/09/29 11:42:57
     */
    long pageImsMenuCounts(ImsMenu imsMenu);

    /**
     * Description:查询菜单列表(非系统)
     *
     * @param id 用户id
     * @return
     */
    List<ImsMenu> listImsMenus(long id);


    /**
     * Description:查询系统列表
     *
     * @param id 用户id
     * @return
     */
    List<ImsMenu> listImsSystems(long id);

}
