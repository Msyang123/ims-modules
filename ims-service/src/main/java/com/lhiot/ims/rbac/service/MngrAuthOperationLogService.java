package com.lhiot.ims.rbac.service;

import java.util.Arrays;
import java.util.List;
import com.lhiot.ims.rbac.domain.MngrAuthOperationLog;
import com.lhiot.ims.rbac.mapper.MngrAuthOperationLogMapper;
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
public class MngrAuthOperationLogService {

    private final MngrAuthOperationLogMapper mngrAuthOperationLogMapper;

    @Autowired
    public MngrAuthOperationLogService(MngrAuthOperationLogMapper mngrAuthOperationLogMapper) {
        this.mngrAuthOperationLogMapper = mngrAuthOperationLogMapper;
    }

    /** 
    * Description:新增操作表日志
    *  
    * @param mngrAuthOperationLog
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */  
    public int create(MngrAuthOperationLog mngrAuthOperationLog){
        return this.mngrAuthOperationLogMapper.create(mngrAuthOperationLog);
    }

    /** 
    * Description:根据id修改操作表日志
    *  
    * @param mngrAuthOperationLog
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public int updateById(MngrAuthOperationLog mngrAuthOperationLog){
        return this.mngrAuthOperationLogMapper.updateById(mngrAuthOperationLog);
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
        return this.mngrAuthOperationLogMapper.deleteByIds(Arrays.asList(ids.split(",")));
    }
    
    /** 
    * Description:根据id查找操作表日志
    *  
    * @param id
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public MngrAuthOperationLog selectById(Long id){
        return this.mngrAuthOperationLogMapper.selectById(id);
    }

    /** 
    * Description: 查询操作表日志总记录数
    *  
    * @param mngrAuthOperationLog
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */  
    public long count(MngrAuthOperationLog mngrAuthOperationLog){
        return this.mngrAuthOperationLogMapper.pageMngrAuthOperationLogCounts(mngrAuthOperationLog);
    }
    
    /** 
    * Description: 查询操作表日志分页列表
    *  
    * @param mngrAuthOperationLog
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */  
    public PagerResultObject<MngrAuthOperationLog> pageList(MngrAuthOperationLog mngrAuthOperationLog) {
       long total = 0;
       if (mngrAuthOperationLog.getRows() != null && mngrAuthOperationLog.getRows() > 0) {
           total = this.count(mngrAuthOperationLog);
       }
       return PagerResultObject.of(mngrAuthOperationLog, total,
              this.mngrAuthOperationLogMapper.pageMngrAuthOperationLogs(mngrAuthOperationLog));
    }
}

