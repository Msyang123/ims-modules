package com.lhiot.ims.rbac.mapper;

import com.lhiot.ims.rbac.domain.ImsOperationLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* Description:操作表日志Mapper类
* @author yijun
* @date 2018/09/29
*/
@Mapper
public interface ImsOperationLogMapper {

    /**
    * Description:新增操作表日志
    *
    * @param imsOperationLog
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int create(ImsOperationLog imsOperationLog);

    /**
    * Description:根据id修改操作表日志
    *
    * @param imsOperationLog
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int updateById(ImsOperationLog imsOperationLog);

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
    ImsOperationLog selectById(Long id);

    /**
    * Description:查询操作表日志列表
    *
    * @param imsOperationLog
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
     List<ImsOperationLog> pageImsOperationLogs(ImsOperationLog imsOperationLog);


    /**
    * Description: 查询操作表日志总记录数
    *
    * @param imsOperationLog
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */
    int pageImsOperationLogCounts(ImsOperationLog imsOperationLog);
}
