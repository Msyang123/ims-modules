package com.lhiot.ims.rbac.service;

import com.leon.microx.support.result.Pages;
import com.leon.microx.util.StringUtils;
import com.lhiot.ims.rbac.domain.ImsOperationLog;
import com.lhiot.ims.rbac.mapper.ImsOperationLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

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
        return this.imsOperationLogMapper.deleteByIds(Arrays.asList(StringUtils.tokenizeToStringArray(ids, ",")));
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
    * Description: 查询操作表日志分页列表
    *  
    * @param imsOperationLog
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */  
    public Pages<ImsOperationLog> pageList(ImsOperationLog imsOperationLog) {
       return Pages.of(this.imsOperationLogMapper.pageImsOperationLogCounts(imsOperationLog),
              this.imsOperationLogMapper.pageImsOperationLogs(imsOperationLog));
    }
}

