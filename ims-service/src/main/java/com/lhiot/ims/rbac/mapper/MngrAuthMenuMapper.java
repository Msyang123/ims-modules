package com.lhiot.ims.rbac.mapper;

import com.lhiot.ims.rbac.domain.MngrAuthMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* Description:菜单Mapper类
* @author yijun
* @date 2018/09/29
*/
@Mapper
public interface MngrAuthMenuMapper {

    /**
    * Description:新增菜单
    *
    * @param mngrAuthMenu
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int create(MngrAuthMenu mngrAuthMenu);

    /**
    * Description:根据id修改菜单
    *
    * @param mngrAuthMenu
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int updateById(MngrAuthMenu mngrAuthMenu);

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
    MngrAuthMenu selectById(Long id);

    /**
    * Description:查询菜单列表
    *
    * @param mngrAuthMenu
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
     List<MngrAuthMenu> pageMngrAuthMenus(MngrAuthMenu mngrAuthMenu);


    /**
    * Description: 查询菜单总记录数
    *
    * @param mngrAuthMenu
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    long pageMngrAuthMenuCounts(MngrAuthMenu mngrAuthMenu);
}
