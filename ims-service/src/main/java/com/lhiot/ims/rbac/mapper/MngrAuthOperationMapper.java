package com.lhiot.ims.rbac.mapper;

import com.lhiot.ims.rbac.domain.MngrAuthOperation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* Description:功能操作Mapper类
* @author yijun
* @date 2018/09/29
*/
@Mapper
public interface MngrAuthOperationMapper {

    /**
    * Description:新增功能操作
    *
    * @param mngrAuthOperation
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int create(MngrAuthOperation mngrAuthOperation);

    /**
    * Description:根据id修改功能操作
    *
    * @param mngrAuthOperation
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int updateById(MngrAuthOperation mngrAuthOperation);

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
    * Description:根据id查找功能操作
    *
    * @param id
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    MngrAuthOperation selectById(Long id);

    /**
    * Description:查询功能操作列表
    *
    * @param mngrAuthOperation
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
     List<MngrAuthOperation> pageMngrAuthOperations(MngrAuthOperation mngrAuthOperation);


    /**
    * Description: 查询功能操作总记录数
    *
    * @param mngrAuthOperation
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    long pageMngrAuthOperationCounts(MngrAuthOperation mngrAuthOperation);
}
