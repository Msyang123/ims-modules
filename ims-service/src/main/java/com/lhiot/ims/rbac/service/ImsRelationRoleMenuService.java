package com.lhiot.ims.rbac.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.leon.microx.util.StringUtils;
import com.lhiot.ims.rbac.domain.ImsOperation;
import com.lhiot.ims.rbac.domain.ImsRelationRoleMenu;
import com.lhiot.ims.rbac.domain.ImsRelationRoleOperation;
import com.lhiot.ims.rbac.mapper.ImsOperationMapper;
import com.lhiot.ims.rbac.mapper.ImsRelationRoleMenuMapper;
import com.lhiot.ims.rbac.mapper.ImsRelationRoleOperationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * Description:角色与菜单关联服务类
 *
 * @author yijun
 * @date 2018/09/29
 */
@SuppressWarnings("ALL")
@Service
@Transactional(rollbackFor = Exception.class)
public class ImsRelationRoleMenuService {

    private final ImsRelationRoleMenuMapper relationRoleMenuMapper;
    private final ImsRelationRoleOperationMapper relationRoleOperationMapper;
    private final ImsOperationMapper operationMapper;

    @Autowired
    public ImsRelationRoleMenuService(ImsRelationRoleMenuMapper relationRoleMenuMapper, ImsRelationRoleOperationMapper relationRoleOperationMapper, ImsOperationMapper operationMapper) {
        this.relationRoleMenuMapper = relationRoleMenuMapper;
        this.relationRoleOperationMapper = relationRoleOperationMapper;
        this.operationMapper = operationMapper;
    }

    /**
     * Description:新增角色与菜单关联
     *
     * @param roleId  角色id
     * @param menuIds 菜单列表
     * @return
     * @author yijun
     * @date 2018/09/29 11:42:57
     */
    public int create(Long roleId, String menuIds) {
        //没有传递menuIds 则只删除
        if (Objects.equals("-1",menuIds)){
            //删除角色与菜单关联关系
            int result = this.relationRoleMenuMapper.deleteByRoleIds(Arrays.asList(roleId.toString()));
            //删除角色与操作关联关系
            this.relationRoleOperationMapper.deleteByRoleIds(Arrays.asList(roleId.toString()));
            return result;
        }else {
            //删除角色与菜单关联关系
            this.relationRoleMenuMapper.deleteByRoleIds(Arrays.asList(roleId.toString()));
            //删除角色与操作关联关系
            this.relationRoleOperationMapper.deleteByRoleIds(Arrays.asList(roleId.toString()));

            List<ImsRelationRoleMenu> imsRelationRoleMenuList=new ArrayList<>();
            for(String item:menuIds.split(",")){
                ImsRelationRoleMenu relationRoleMenu = new ImsRelationRoleMenu();
                relationRoleMenu.setRoleId(roleId);
                relationRoleMenu.setMenuId(Long.valueOf(item));
                imsRelationRoleMenuList.add(relationRoleMenu);
            }
            //查询所有菜单对应的关联操作
            List<ImsOperation> operationList = operationMapper.listByMenuIds(Arrays.asList(StringUtils.tokenizeToStringArray(menuIds, ",")));
            if(!CollectionUtils.isEmpty(operationList)) {
                //构造角色与操作关联关系列表
                List<ImsRelationRoleOperation> relationRoleOperationList = new ArrayList<>(operationList.size());
                operationList.forEach(item -> {
                    ImsRelationRoleOperation imsRelationRoleOperation = new ImsRelationRoleOperation();
                    imsRelationRoleOperation.setOperationId(item.getId());
                    imsRelationRoleOperation.setRoleId(roleId);
                    relationRoleOperationList.add(imsRelationRoleOperation);
                });
                //批量新增角色与操作关联
                this.relationRoleOperationMapper.batchInsert(relationRoleOperationList);
            }

            //批量新增角色与菜单关联
            return this.relationRoleMenuMapper.batchInsert(imsRelationRoleMenuList);
        }
    }

    /**
     * Description:根据id修改角色与菜单关联
     *
     * @param relationRoleMenu
     * @return
     * @author yijun
     * @date 2018/09/29 11:42:57
     */
    public int updateById(ImsRelationRoleMenu relationRoleMenu) {
        return this.relationRoleMenuMapper.updateById(relationRoleMenu);
    }

    /**
     * Description:根据ids删除角色与菜单关联
     *
     * @param ids
     * @return
     * @author yijun
     * @date 2018/09/29 11:42:57
     */
    public int deleteByIds(String ids) {
        return this.relationRoleMenuMapper.deleteByIds(Arrays.asList(StringUtils.tokenizeToStringArray(ids, ",")));
    }

    /**
     * Description:根据id查找角色与菜单关联
     *
     * @param id
     * @return
     * @author yijun
     * @date 2018/09/29 11:42:57
     */
    public ImsRelationRoleMenu selectById(Long id) {
        return this.relationRoleMenuMapper.selectById(id);
    }

}

