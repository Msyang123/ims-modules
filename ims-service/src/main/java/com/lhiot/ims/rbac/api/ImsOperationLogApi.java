package com.lhiot.ims.rbac.api;

import com.lhiot.ims.rbac.common.PagerResultObject;
import com.lhiot.ims.rbac.domain.ImsOperationLog;
import com.lhiot.ims.rbac.service.ImsOperationLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Description:操作表日志接口类
 *
 * @author yijun
 * @date 2018/09/29
 */
@Api(description = "操作表日志接口")
@Slf4j
@RestController
@RequestMapping("/ims-operation-log")
public class ImsOperationLogApi {

    private final ImsOperationLogService imsOperationLogService;

    @Autowired
    public ImsOperationLogApi(ImsOperationLogService imsOperationLogService) {
        this.imsOperationLogService = imsOperationLogService;
    }

    @ApiOperation(value = "根据id查询操作表日志", notes = "根据id查询操作表日志")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键id", required = true, dataType = "Long")
    @GetMapping("/{id}")
    public ResponseEntity<ImsOperationLog> findImsOperationLog(@PathVariable("id") Long id) {

        return ResponseEntity.ok(imsOperationLogService.selectById(id));
    }

    @PostMapping("/page/query")
    @ApiImplicitParam(paramType = "body", name = "imsOperationLog", value = "查询操作日志参数", required = true, dataType = "ImsOperationLog")
    @ApiOperation(value = "查询操作表日志分页列表")
    public ResponseEntity<PagerResultObject<ImsOperationLog>> pageQuery(@RequestBody ImsOperationLog imsOperationLog) {
        log.debug("查询操作表日志分页列表\t param:{}", imsOperationLog);

        return ResponseEntity.ok(imsOperationLogService.pageList(imsOperationLog));
    }

}
