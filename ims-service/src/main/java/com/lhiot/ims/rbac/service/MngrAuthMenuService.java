package com.lhiot.ims.rbac.service;

import java.util.Arrays;
import java.util.List;
import com.lhiot.ims.rbac.domain.MngrAuthMenu;
import com.lhiot.ims.rbac.mapper.MngrAuthMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lhiot.ims.rbac.common.PagerResultObject;

/**
* Description:菜单服务类
* @author yijun
* @date 2018/09/29
*/
@Service
@Transactional
public class MngrAuthMenuService {

    private final MngrAuthMenuMapper mngrAuthMenuMapper;

    @Autowired
    public MngrAuthMenuService(MngrAuthMenuMapper mngrAuthMenuMapper) {
        this.mngrAuthMenuMapper = mngrAuthMenuMapper;
    }

    /** 
    * Description:新增菜单
    *  
    * @param mngrAuthMenu
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */  
    public int create(MngrAuthMenu mngrAuthMenu){
        return this.mngrAuthMenuMapper.create(mngrAuthMenu);
    }

    /** 
    * Description:根据id修改菜单
    *  
    * @param mngrAuthMenu
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public int updateById(MngrAuthMenu mngrAuthMenu){
        return this.mngrAuthMenuMapper.updateById(mngrAuthMenu);
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
        return this.mngrAuthMenuMapper.deleteByIds(Arrays.asList(ids.split(",")));
    }
    
    /** 
    * Description:根据id查找菜单
    *  
    * @param id
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public MngrAuthMenu selectById(Long id){
        return this.mngrAuthMenuMapper.selectById(id);
    }

    /** 
    * Description: 查询菜单总记录数
    *  
    * @param mngrAuthMenu
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */  
    public long count(MngrAuthMenu mngrAuthMenu){
        return this.mngrAuthMenuMapper.pageMngrAuthMenuCounts(mngrAuthMenu);
    }
    
    /** 
    * Description: 查询菜单分页列表
    *  
    * @param mngrAuthMenu
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */  
    public PagerResultObject<MngrAuthMenu> pageList(MngrAuthMenu mngrAuthMenu) {
       long total = 0;
       if (mngrAuthMenu.getRows() != null && mngrAuthMenu.getRows() > 0) {
           total = this.count(mngrAuthMenu);
       }
       return PagerResultObject.of(mngrAuthMenu, total,
              this.mngrAuthMenuMapper.pageMngrAuthMenus(mngrAuthMenu));
    }
}

