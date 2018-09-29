package com.lhiot.ims.rbac.service;

import java.util.Arrays;
import java.util.List;
import com.lhiot.ims.rbac.domain.MngrAuthOperation;
import com.lhiot.ims.rbac.mapper.MngrAuthOperationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lhiot.ims.rbac.common.PagerResultObject;

/**
* Description:功能操作服务类
* @author yijun
* @date 2018/09/29
*/
@Service
@Transactional
public class MngrAuthOperationService {

    private final MngrAuthOperationMapper mngrAuthOperationMapper;

    @Autowired
    public MngrAuthOperationService(MngrAuthOperationMapper mngrAuthOperationMapper) {
        this.mngrAuthOperationMapper = mngrAuthOperationMapper;
    }

    /** 
    * Description:新增功能操作
    *  
    * @param mngrAuthOperation
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */  
    public int create(MngrAuthOperation mngrAuthOperation){
        return this.mngrAuthOperationMapper.create(mngrAuthOperation);
    }

    /** 
    * Description:根据id修改功能操作
    *  
    * @param mngrAuthOperation
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public int updateById(MngrAuthOperation mngrAuthOperation){
        return this.mngrAuthOperationMapper.updateById(mngrAuthOperation);
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
        return this.mngrAuthOperationMapper.deleteByIds(Arrays.asList(ids.split(",")));
    }
    
    /** 
    * Description:根据id查找功能操作
    *  
    * @param id
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */ 
    public MngrAuthOperation selectById(Long id){
        return this.mngrAuthOperationMapper.selectById(id);
    }

    /** 
    * Description: 查询功能操作总记录数
    *  
    * @param mngrAuthOperation
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */  
    public long count(MngrAuthOperation mngrAuthOperation){
        return this.mngrAuthOperationMapper.pageMngrAuthOperationCounts(mngrAuthOperation);
    }
    
    /** 
    * Description: 查询功能操作分页列表
    *  
    * @param mngrAuthOperation
    * @return
    * @author yijun
    * @date 2018/09/29 11:42:57
    */  
    public PagerResultObject<MngrAuthOperation> pageList(MngrAuthOperation mngrAuthOperation) {
       long total = 0;
       if (mngrAuthOperation.getRows() != null && mngrAuthOperation.getRows() > 0) {
           total = this.count(mngrAuthOperation);
       }
       return PagerResultObject.of(mngrAuthOperation, total,
              this.mngrAuthOperationMapper.pageMngrAuthOperations(mngrAuthOperation));
    }
}

