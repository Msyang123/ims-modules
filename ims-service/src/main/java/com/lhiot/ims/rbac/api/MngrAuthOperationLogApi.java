package com.lhiot.ims.rbac.api;

import com.lhiot.ims.rbac.domain.MngrAuthOperationLog;
import com.lhiot.ims.rbac.service.MngrAuthOperationLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lhiot.ims.rbac.common.PagerResultObject;

/**
* Description:操作表日志接口类
* @author yijun
* @date 2018/09/29
*/
@Api(description = "操作表日志接口")
@Slf4j
@RestController
@RequestMapping("/mngrAuthOperationLog")
public class MngrAuthOperationLogApi {

    private final MngrAuthOperationLogService mngrAuthOperationLogService;

    @Autowired
    public MngrAuthOperationLogApi(MngrAuthOperationLogService mngrAuthOperationLogService) {
        this.mngrAuthOperationLogService = mngrAuthOperationLogService;
    }

    @PostMapping("/create")
    @ApiOperation(value = "添加操作表日志")
    @ApiImplicitParam(paramType = "body", name = "mngrAuthOperationLog", value = "要添加的操作表日志", required = true, dataType = "MngrAuthOperationLog")
    public ResponseEntity<Integer> create(@RequestBody MngrAuthOperationLog mngrAuthOperationLog) {
        log.debug("添加操作表日志\t param:{}",mngrAuthOperationLog);
        
        return ResponseEntity.ok(mngrAuthOperationLogService.create(mngrAuthOperationLog));
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "根据id更新操作表日志")
    @ApiImplicitParam(paramType = "body", name = "mngrAuthOperationLog", value = "要更新的操作表日志", required = true, dataType = "MngrAuthOperationLog")
    public ResponseEntity<Integer> update(@PathVariable("id") Long id,@RequestBody MngrAuthOperationLog mngrAuthOperationLog) {
        log.debug("根据id更新操作表日志\t id:{} param:{}",id,mngrAuthOperationLog);
        mngrAuthOperationLog.setId(id);
        
        return ResponseEntity.ok(mngrAuthOperationLogService.updateById(mngrAuthOperationLog));
    }

    @DeleteMapping("/{ids}")
    @ApiOperation(value = "根据ids删除操作表日志")
    @ApiImplicitParam(paramType = "path", name = "ids", value = "要删除操作表日志的ids,逗号分割", required = true, dataType = "String")
    public ResponseEntity<Integer> deleteByIds(@PathVariable("ids") String ids) {
        log.debug("根据ids删除操作表日志\t param:{}",ids);
        
        return ResponseEntity.ok(mngrAuthOperationLogService.deleteByIds(ids));
    }
    
    @ApiOperation(value = "根据id查询操作表日志", notes = "根据id查询操作表日志")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键id", required = true, dataType = "Long")
    @GetMapping("/{id}")
    public ResponseEntity<MngrAuthOperationLog> findMngrAuthOperationLog(@PathVariable("id") Long id) {

        return ResponseEntity.ok(mngrAuthOperationLogService.selectById(id));
    }
    
    @GetMapping("/page/query")
    @ApiOperation(value = "查询操作表日志分页列表")
    public ResponseEntity<PagerResultObject<MngrAuthOperationLog>> pageQuery(MngrAuthOperationLog mngrAuthOperationLog){
        log.debug("查询操作表日志分页列表\t param:{}",mngrAuthOperationLog);
        
        return ResponseEntity.ok(mngrAuthOperationLogService.pageList(mngrAuthOperationLog));
    }
    
}
