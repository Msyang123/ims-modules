package com.lhiot.ims.rbac.service;

import com.lhiot.ims.rbac.domain.ImsRelationUserRole;
import com.lhiot.ims.rbac.mapper.ImsRelationUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description:用户与角色关联服务类
 *
 * @author yijun
 * @date 2018/09/29
 */
@Service
@Transactional
public class ImsRelationUserRoleService {

    private final ImsRelationUserRoleMapper relationUserRoleMapper;

    @Autowired
    public ImsRelationUserRoleService(ImsRelationUserRoleMapper relationUserRoleMapper) {
        this.relationUserRoleMapper = relationUserRoleMapper;
    }

    /**
     * Description:新增用户与角色关联 先删除掉此用户的所有关联角色，再批量增加
     *
     * @param userId 用户id
     * @param roleIds 角色ids
     * @return
     */
    public int create(Long userId, String roleIds) {
        //有关联角色关系直接删除
        this.relationUserRoleMapper.deleteByUserIds(Arrays.asList(userId.toString()));

        List<ImsRelationUserRole> imsRelationUserRoleList = new ArrayList<>();
        for(String item: roleIds.split(",")){
            ImsRelationUserRole relationUserRole = new ImsRelationUserRole();
            relationUserRole.setRoleId(Long.valueOf(item));
            relationUserRole.setUserId(userId);
            imsRelationUserRoleList.add(relationUserRole);
        }
        //批量新增用户角色关联
        return this.relationUserRoleMapper.batchInsert(imsRelationUserRoleList);
    }

    /**
     * Description:根据id修改用户与角色关联
     *
     * @param relationUserRole
     * @return
     * @author yijun
     * @date 2018/09/29 11:42:57
     */
    public int updateById(ImsRelationUserRole relationUserRole) {
        return this.relationUserRoleMapper.updateById(relationUserRole);
    }

    /**
     * Description:根据ids删除用户与角色关联
     *
     * @param ids
     * @return
     * @author yijun
     * @date 2018/09/29 11:42:57
     */
    public int deleteByIds(String ids) {
        return this.relationUserRoleMapper.deleteByIds(Arrays.asList(ids.split(",")));
    }

    /**
     * Description:根据id查找用户与角色关联
     *
     * @param id
     * @return
     * @author yijun
     * @date 2018/09/29 11:42:57
     */
    public ImsRelationUserRole selectById(Long id) {
        return this.relationUserRoleMapper.selectById(id);
    }


}

