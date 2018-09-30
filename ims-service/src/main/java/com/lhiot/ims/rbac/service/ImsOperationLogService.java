package com.lhiot.ims.rbac.service;

import java.util.Arrays;
import java.util.List;
import com.lhiot.ims.rbac.domain.ImsOperationLog;
import com.lhiot.ims.rbac.mapper.ImsOperationLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lhiot.ims.rbac.common.PagerResultObject;

/**
* Description:操作表日志服务类
* @author yijun
* @date 2018/09/29
*/
@Service
@Transactional
public class ImsOperationLogService {

    private final ImsOperationLogMapper imsOperationLogMapper;

    @Autowired
    public ImsOperationLogService(ImsOperationLogMapper imsOperationLogMapper) {
        this.imsOperationLogMapper = imsOperationLogMapper;
    }

    /** 
    * Description:新增操作表日志
    *  
    * @param imsOperationLog
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */  
    public int create(ImsOperationLog imsOperationLog){
        return this.imsOperationLogMapper.create(imsOperationLog);
    }

    /** 
    * Description:根据id修改操作表日志
    *  
    * @param imsOperationLog
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public int updateById(ImsOperationLog imsOperationLog){
        return this.imsOperationLogMapper.updateById(imsOperationLog);
    }

    /** 
    * Description:根据ids删除操作表日志
    *  
    * @param ids
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public int deleteByIds(String ids){
        return this.imsOperationLogMapper.deleteByIds(Arrays.asList(ids.split(",")));
    }
    
    /** 
    * Description:根据id查找操作表日志
    *  
    * @param id
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public ImsOperationLog selectById(Long id){
        return this.imsOperationLogMapper.selectById(id);
    }

    /** 
    * Description: 查询操作表日志总记录数
    *  
    * @param imsOperationLog
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */  
    public long count(ImsOperationLog imsOperationLog){
        return this.imsOperationLogMapper.pageImsOperationLogCounts(imsOperationLog);
    }
    
    /** 
    * Description: 查询操作表日志分页列表
    *  
    * @param imsOperationLog
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */  
    public PagerResultObject<ImsOperationLog> pageList(ImsOperationLog imsOperationLog) {
       long total = 0;
       if (imsOperationLog.getRows() != null && imsOperationLog.getRows() > 0) {
           total = this.count(imsOperationLog);
       }
       return PagerResultObject.of(imsOperationLog, total,
              this.imsOperationLogMapper.pageImsOperationLogs(imsOperationLog));
    }
}

