package com.lhiot.ims.rbac.mapper;

import com.lhiot.ims.rbac.domain.ImsOperation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* Description:功能操作Mapper类
* @author yijun
* @date 2018/09/29
*/
@Mapper
@Repository
public interface ImsOperationMapper {

    /**
    * Description:新增功能操作
    *
    * @param imsOperation
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int create(ImsOperation imsOperation);

    /**
    * Description:根据id修改功能操作
    *
    * @param imsOperation
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int updateById(ImsOperation imsOperation);

    /**
    * Description:根据ids删除功能操作
    *
    * @param ids
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int deleteByIds(java.util.List<String> ids);

    /**
     * Description:根据菜单ids删除功能操作
     *
     * @param ids
     * @return
     * @author yijun
     * @date 2018/09/29 11:42:57
     */
    int deleteByMenuIds(java.util.List<String> ids);

    /**
    * Description:根据id查找功能操作
    *
    * @param id
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    ImsOperation selectById(Long id);

    /**
    * Description:查询功能操作列表
    *
    * @param imsOperation
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
     List<ImsOperation> pageImsOperations(ImsOperation imsOperation);

     List<ImsOperation> selectAuthority();


    /**
    * Description: 查询功能操作总记录数
    *
    * @param imsOperation
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int pageImsOperationCounts(ImsOperation imsOperation);

    /**
     * Description:依据用户id查询功能操作列表
     *
     * @param id 用户id
     * @return
     */
    List<ImsOperation> listByUserId(long id);

    /**
     * 依据菜单ids查询
     * @param ids 菜单ids
     * @return
     */
    List<ImsOperation> listByMenuIds(java.util.List<String> ids);
}
