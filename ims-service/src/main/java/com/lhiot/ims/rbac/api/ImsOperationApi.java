package com.lhiot.ims.rbac.api;

import com.lhiot.ims.rbac.common.PagerResultObject;
import com.lhiot.ims.rbac.domain.ImsOperation;
import com.lhiot.ims.rbac.service.ImsOperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
* Description:功能操作接口类
* @author yijun
* @date 2018/09/29
*/
@Api(description = "功能操作接口")
@Slf4j
@RestController
@RequestMapping("/ims-operation")
public class ImsOperationApi {

    private final ImsOperationService imsOperationService;

    @Autowired
    public ImsOperationApi(ImsOperationService imsOperationService) {
        this.imsOperationService = imsOperationService;
    }

    
    @ApiOperation(value = "根据id查询功能操作", notes = "根据id查询功能操作")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键id", required = true, dataType = "Long")
    @GetMapping("/{id}")
    public ResponseEntity<ImsOperation> findImsOperation(@PathVariable("id") Long id) {

        return ResponseEntity.ok(imsOperationService.selectById(id));
    }
    
    @PostMapping("/page/query")
    @ApiOperation(value = "查询功能操作分页列表")
    @ApiImplicitParam(paramType = "body", name = "imsOperation", value = "功能操作分页参数", required = true, dataType = "ImsOperation")
    public ResponseEntity<PagerResultObject<ImsOperation>> pageQuery(@RequestBody ImsOperation imsOperation){
        log.debug("查询功能操作分页列表\t param:{}",imsOperation);
        
        return ResponseEntity.ok(imsOperationService.pageList(imsOperation));
    }
    
}
