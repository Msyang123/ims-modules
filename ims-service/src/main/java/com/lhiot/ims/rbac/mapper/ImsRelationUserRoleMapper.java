package com.lhiot.ims.rbac.mapper;

import com.lhiot.ims.rbac.domain.ImsRelationUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:用户与角色关联Mapper类
 *
 * @author yijun
 * @date 2018/09/29
 */
@Mapper
@Repository
public interface ImsRelationUserRoleMapper {

    /**
     * Description:批量新增用户与角色关联
     *
     * @param relationUserRoleList
     * @return
     * @author yijun
     * @date 2018/09/29 11:42:57
     */
    int batchInsert(List<ImsRelationUserRole> relationUserRoleList);

    /**
     * Description:根据id修改用户与角色关联
     *
     * @param relationUserRole
     * @return
     * @author yijun
     * @date 2018/09/29 11:42:57
     */
    int updateById(ImsRelationUserRole relationUserRole);

    /**
     * Description:根据ids删除用户与角色关联
     *
     * @param ids
     * @return
     * @author yijun
     * @date 2018/09/29 11:42:57
     */
    int deleteByIds(java.util.List<String> ids);

    /**
     * Description:根据用户ids删除用户与角色关联
     *
     * @param ids
     * @return
     * @author yijun
     * @date 2018/09/29 11:42:57
     */
    int deleteByUserIds(java.util.List<String> ids);


    /**
     * Description:根据id查找用户与角色关联
     *
     * @param id
     * @return
     * @author yijun
     * @date 2018/09/29 11:42:57
     */
    ImsRelationUserRole selectById(Long id);

    /**
     * Description:查询用户与角色关联列表
     *
     * @param relationUserRole
     * @return
     * @author yijun
     * @date 2018/09/29 11:42:57
     */
    List<ImsRelationUserRole> pageImsRelationUserRoles(ImsRelationUserRole relationUserRole);


    /**
     * Description: 查询用户与角色关联总记录数
     *
     * @param relationUserRole
     * @return
     * @author yijun
     * @date 2018/09/29 11:42:57
     */
    int pageImsRelationUserRoleCounts(ImsRelationUserRole relationUserRole);
}
