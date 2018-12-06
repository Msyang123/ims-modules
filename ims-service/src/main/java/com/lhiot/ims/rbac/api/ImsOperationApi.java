package com.lhiot.ims.rbac.api;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.rbac.aspect.LogCollection;
import com.lhiot.ims.rbac.domain.ImsOperation;
import com.lhiot.ims.rbac.service.ImsOperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @PostMapping("/")
    @ApiOperation(value = "添加的操作")
    @ApiImplicitParam(paramType = "body", name = "imsOperation", value = "要添加的操作", required = true, dataType = "ImsOperation")
    public ResponseEntity<ImsOperation> create(@RequestBody ImsOperation imsOperation) {
        log.debug("添加的操作\t param:{}", imsOperation);

        return ResponseEntity.ok(imsOperationService.create(imsOperation));
    }

    @DeleteMapping("/{ids}")
    @ApiOperation(value = "根据ids删除功能操作")
    @ApiImplicitParam(paramType = "path", name = "ids", value = "要删除功能操作的ids,逗号分割", required = true, dataType = "String")
    public ResponseEntity<Integer> deleteByIds(@PathVariable("ids") String ids) {
        log.debug("根据ids删除功能操作\t param:{}", ids);

        return ResponseEntity.ok(imsOperationService.deleteByIds(ids));
    }

    @ApiOperation(value = "根据id查询功能操作", notes = "根据id查询功能操作")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键id", required = true, dataType = "Long")
    @GetMapping("/{id}")
    public ResponseEntity<ImsOperation> findImsOperation(@PathVariable("id") Long id) {

        return ResponseEntity.ok(imsOperationService.selectById(id));
    }
    
    @PostMapping("/pages")
    @ApiOperation(value = "查询功能操作分页列表")
    @ApiImplicitParam(paramType = "body", name = "imsOperation", value = "功能操作分页参数", required = true, dataType = "ImsOperation")
    public ResponseEntity<Pages<ImsOperation>> pageQuery(@RequestBody ImsOperation imsOperation){
        log.debug("查询功能操作分页列表\t param:{}",imsOperation);
        
        return ResponseEntity.ok(imsOperationService.pageList(imsOperation));
    }

    @GetMapping("/list/{menuIds}")
    @ApiOperation(value = "根据菜单ids查询操作权限列表", notes = "根据菜单ids查询操作权限列表")
    public ResponseEntity<List<ImsOperation>> listByMenuIds(@PathVariable("menuIds") String menuIds) {

        return ResponseEntity.ok(imsOperationService.listByMenuIds(menuIds));
    }
}
