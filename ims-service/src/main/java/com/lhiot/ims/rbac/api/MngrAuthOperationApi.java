package com.lhiot.ims.rbac.api;

import com.lhiot.ims.rbac.domain.MngrAuthOperation;
import com.lhiot.ims.rbac.service.MngrAuthOperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lhiot.ims.rbac.common.PagerResultObject;

/**
* Description:功能操作接口类
* @author yijun
* @date 2018/09/29
*/
@Api(description = "功能操作接口")
@Slf4j
@RestController
@RequestMapping("/mngr-auth-operation")
public class MngrAuthOperationApi {

    private final MngrAuthOperationService mngrAuthOperationService;

    @Autowired
    public MngrAuthOperationApi(MngrAuthOperationService mngrAuthOperationService) {
        this.mngrAuthOperationService = mngrAuthOperationService;
    }

    @PostMapping("/create")
    @ApiOperation(value = "添加功能操作")
    @ApiImplicitParam(paramType = "body", name = "mngrAuthOperation", value = "要添加的功能操作", required = true, dataType = "MngrAuthOperation")
    public ResponseEntity<Integer> create(@RequestBody MngrAuthOperation mngrAuthOperation) {
        log.debug("添加功能操作\t param:{}",mngrAuthOperation);
        
        return ResponseEntity.ok(mngrAuthOperationService.create(mngrAuthOperation));
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "根据id更新功能操作")
    @ApiImplicitParam(paramType = "body", name = "mngrAuthOperation", value = "要更新的功能操作", required = true, dataType = "MngrAuthOperation")
    public ResponseEntity<Integer> update(@PathVariable("id") Long id,@RequestBody MngrAuthOperation mngrAuthOperation) {
        log.debug("根据id更新功能操作\t id:{} param:{}",id,mngrAuthOperation);
        mngrAuthOperation.setId(id);
        
        return ResponseEntity.ok(mngrAuthOperationService.updateById(mngrAuthOperation));
    }

    @DeleteMapping("/{ids}")
    @ApiOperation(value = "根据ids删除功能操作")
    @ApiImplicitParam(paramType = "path", name = "ids", value = "要删除功能操作的ids,逗号分割", required = true, dataType = "String")
    public ResponseEntity<Integer> deleteByIds(@PathVariable("ids") String ids) {
        log.debug("根据ids删除功能操作\t param:{}",ids);
        
        return ResponseEntity.ok(mngrAuthOperationService.deleteByIds(ids));
    }
    
    @ApiOperation(value = "根据id查询功能操作", notes = "根据id查询功能操作")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键id", required = true, dataType = "Long")
    @GetMapping("/{id}")
    public ResponseEntity<MngrAuthOperation> findMngrAuthOperation(@PathVariable("id") Long id) {

        return ResponseEntity.ok(mngrAuthOperationService.selectById(id));
    }
    
    @GetMapping("/page/query")
    @ApiOperation(value = "查询功能操作分页列表")
    public ResponseEntity<PagerResultObject<MngrAuthOperation>> pageQuery(MngrAuthOperation mngrAuthOperation){
        log.debug("查询功能操作分页列表\t param:{}",mngrAuthOperation);
        
        return ResponseEntity.ok(mngrAuthOperationService.pageList(mngrAuthOperation));
    }
    
}
