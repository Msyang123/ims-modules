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
@RequestMapping("/mngr-auth-operation-log")
public class MngrAuthOperationLogApi {

    private final MngrAuthOperationLogService mngrAuthOperationLogService;

    @Autowired
    public MngrAuthOperationLogApi(MngrAuthOperationLogService mngrAuthOperationLogService) {
        this.mngrAuthOperationLogService = mngrAuthOperationLogService;
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
