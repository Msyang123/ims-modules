package com.lhiot.ims.rbac.mapper;

import com.lhiot.ims.rbac.domain.MngrAuthOperationLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* Description:操作表日志Mapper类
* @author yijun
* @date 2018/09/29
*/
@Mapper
public interface MngrAuthOperationLogMapper {

    /**
    * Description:新增操作表日志
    *
    * @param mngrAuthOperationLog
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int create(MngrAuthOperationLog mngrAuthOperationLog);

    /**
    * Description:根据id修改操作表日志
    *
    * @param mngrAuthOperationLog
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int updateById(MngrAuthOperationLog mngrAuthOperationLog);

    /**
    * Description:根据ids删除操作表日志
    *
    * @param ids
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int deleteByIds(java.util.List<String> ids);

    /**
    * Description:根据id查找操作表日志
    *
    * @param id
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    MngrAuthOperationLog selectById(Long id);

    /**
    * Description:查询操作表日志列表
    *
    * @param mngrAuthOperationLog
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
     List<MngrAuthOperationLog> pageMngrAuthOperationLogs(MngrAuthOperationLog mngrAuthOperationLog);


    /**
    * Description: 查询操作表日志总记录数
    *
    * @param mngrAuthOperationLog
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    long pageMngrAuthOperationLogCounts(MngrAuthOperationLog mngrAuthOperationLog);
}
