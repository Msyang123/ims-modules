package com.lhiot.ims.rbac.service;

import com.leon.microx.support.result.Pages;
import com.leon.microx.util.StringUtils;
import com.lhiot.ims.rbac.domain.ImsOperation;
import com.lhiot.ims.rbac.mapper.ImsOperationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
* Description:功能操作服务类
* @author yijun
* @date 2018/09/29
*/
@Service
@Transactional
public class ImsOperationService {

    private final ImsOperationMapper imsOperationMapper;

    @Autowired
    public ImsOperationService(ImsOperationMapper imsOperationMapper) {
        this.imsOperationMapper = imsOperationMapper;
    }

    /** 
    * Description:新增功能操作
    *  
    * @param imsOperation
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */  
    public ImsOperation create(ImsOperation imsOperation){
        this.imsOperationMapper.create(imsOperation);
        return imsOperation;
    }

    /** 
    * Description:根据id修改功能操作
    *  
    * @param imsOperation
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public ImsOperation updateById(ImsOperation imsOperation){
        this.imsOperationMapper.updateById(imsOperation);
        return imsOperation;
    }

    /** 
    * Description:根据ids删除功能操作
    *  
    * @param ids
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public int deleteByIds(String ids){
        return this.imsOperationMapper.deleteByIds(Arrays.asList(StringUtils.tokenizeToStringArray(ids, ",")));
    }
    
    /** 
    * Description:根据id查找功能操作
    *  
    * @param id
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public ImsOperation selectById(Long id){
        return this.imsOperationMapper.selectById(id);
    }

    /** 
    * Description: 查询功能操作分页列表
    *  
    * @param imsOperation
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */  
    public Pages<ImsOperation> pageList(ImsOperation imsOperation) {

       return Pages.of(this.imsOperationMapper.pageImsOperationCounts(imsOperation),
              this.imsOperationMapper.pageImsOperations(imsOperation));
    }

    /**
     * Description: 查询功能操作分页列表
     *
     * @param id 用户id
     * @return
     * @author yijun
     * @date 2018/09/29 11:42:57
     */
    public List<ImsOperation> listByUserId(long id) {
        return this.imsOperationMapper.listByUserId(id);
    }
}

