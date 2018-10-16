package com.lhiot.ims.rbac.mapper;

import com.lhiot.ims.rbac.domain.ImsRelationRoleOperation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* Description:角色与操作关联Mapper类
* @author yijun
* @date 2018/09/29
*/
@Mapper
public interface ImsRelationRoleOperationMapper {

    /**
    * Description:批量新增角色与操作关联
    *
    * @param relationRoleOperationList
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int batchInsert(List<ImsRelationRoleOperation> relationRoleOperationList);

    /**
    * Description:根据id修改角色与操作关联
    *
    * @param relationRoleOperation
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int updateById(ImsRelationRoleOperation relationRoleOperation);

    /**
    * Description:根据ids删除角色与操作关联
    *
    * @param ids
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int deleteByIds(java.util.List<String> ids);

    /**
     * Description:根据角色ids删除角色与操作关联
     *
     * @param ids
     * @return
     * @author yijun
     * @date 2018/09/29 11:42:57
     */
    int deleteByRoleIds(java.util.List<String> ids);

    /**
    * Description:根据id查找角色与操作关联
    *
    * @param id
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    ImsRelationRoleOperation selectById(Long id);

    /**
    * Description:查询角色与操作关联列表
    *
    * @param relationRoleOperation
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
     List<ImsRelationRoleOperation> pageImsRelationRoleOperations(ImsRelationRoleOperation relationRoleOperation);


    /**
    * Description: 查询角色与操作关联总记录数
    *
    * @param relationRoleOperation
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int pageImsRelationRoleOperationCounts(ImsRelationRoleOperation relationRoleOperation);
}
