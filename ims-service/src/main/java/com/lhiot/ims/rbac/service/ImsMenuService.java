package com.lhiot.ims.rbac.service;

import com.lhiot.ims.rbac.common.PagerResultObject;
import com.lhiot.ims.rbac.domain.ImsMenu;
import com.lhiot.ims.rbac.domain.ImsRelationRoleMenu;
import com.lhiot.ims.rbac.mapper.ImsMenuMapper;
import com.lhiot.ims.rbac.mapper.ImsOperationMapper;
import com.lhiot.ims.rbac.mapper.ImsRelationRoleMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
* Description:菜单服务类
* @author yijun
* @date 2018/09/29
*/
@Service
@Transactional
public class ImsMenuService {

    private final ImsMenuMapper imsMenuMapper;
    private final ImsRelationRoleMenuMapper relationRoleMenuMapper;
    private final ImsOperationMapper imsOperationMapper;

    @Autowired
    public ImsMenuService(ImsMenuMapper imsMenuMapper, ImsRelationRoleMenuMapper relationRoleMenuMapper, ImsOperationMapper imsOperationMapper) {
        this.imsMenuMapper = imsMenuMapper;
        this.relationRoleMenuMapper = relationRoleMenuMapper;
        this.imsOperationMapper = imsOperationMapper;
    }

    /** 
    * Description:新增菜单
    *  
    * @param imsMenu
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */  
    public int create(ImsMenu imsMenu){
        return this.imsMenuMapper.create(imsMenu);
    }

    /** 
    * Description:根据id修改菜单
    *  
    * @param imsMenu
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public int updateById(ImsMenu imsMenu){
        return this.imsMenuMapper.updateById(imsMenu);
    }

    /** 
    * Description:根据ids删除菜单
    *  
    * @param ids
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public int deleteByIds(String ids){
        //检查是否有关联数据的菜单
        for(String id:ids.split(",")){
            ImsRelationRoleMenu relationRoleMenu=new ImsRelationRoleMenu();
            relationRoleMenu.setMenuId(Long.valueOf(id));
            long count = relationRoleMenuMapper.pageImsRelationRoleMenuCounts(relationRoleMenu);
            //不允许删除有关联数据的菜单
            if(count>0){
                return 0;
            }
        }
        int result =this.imsMenuMapper.deleteByIds(Arrays.asList(ids.split(",")));
        if(result>0){
            //删除菜单对应的操作
            this.imsOperationMapper.deleteByMenuIds(Arrays.asList(ids.split(",")));
        }
        return result;
    }
    
    /** 
    * Description:根据id查找菜单
    *  
    * @param id
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public ImsMenu selectById(Long id){
        return this.imsMenuMapper.selectById(id);
    }

    /** 
    * Description: 查询菜单总记录数
    *  
    * @param imsMenu
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */  
    public long count(ImsMenu imsMenu){
        return this.imsMenuMapper.pageImsMenuCounts(imsMenu);
    }
    
    /** 
    * Description: 查询菜单分页列表
    *  
    * @param imsMenu
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */  
    public PagerResultObject<ImsMenu> pageList(ImsMenu imsMenu) {
       long total = 0;
       if (imsMenu.getRows() != null && imsMenu.getRows() > 0) {
           total = this.count(imsMenu);
       }
       return PagerResultObject.of(imsMenu, total,
              this.imsMenuMapper.pageImsMenus(imsMenu));
    }

    /**
     * 列表查询菜单信息
     * @param id 用户id
     * @return
     */
    public List<ImsMenu> listImsMenus(long id){

        return this.imsMenuMapper.listImsMenus(id);
    }

    /**
     * 列表查询系统信息
     * @param id 用户id
     * @return
     */
    public List<ImsMenu> listImsSystems(long id){

        return this.imsMenuMapper.listImsSystems(id);
    }
}

