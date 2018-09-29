package com.lhiot.ims.rbac.api;

import com.lhiot.ims.rbac.domain.RelationRoleOperation;
import com.lhiot.ims.rbac.service.RelationRoleOperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
* Description:角色与操作关联接口类
* @author yijun
* @date 2018/09/29
*/
@Api(description = "角色与操作关联接口")
@Slf4j
@RestController
@RequestMapping("/relationRoleOperation")
public class RelationRoleOperationApi {

    private final RelationRoleOperationService relationRoleOperationService;

    @Autowired
    public RelationRoleOperationApi(RelationRoleOperationService relationRoleOperationService) {
        this.relationRoleOperationService = relationRoleOperationService;
    }

    @PostMapping("/create")
    @ApiOperation(value = "添加角色与操作关联")
    @ApiImplicitParam(paramType = "body", name = "relationRoleOperation", value = "要添加的角色与操作关联", required = true, dataType = "RelationRoleOperation")
    public ResponseEntity<Integer> create(@RequestBody RelationRoleOperation relationRoleOperation) {
        log.debug("添加角色与操作关联\t param:{}",relationRoleOperation);
        
        return ResponseEntity.ok(relationRoleOperationService.create(relationRoleOperation));
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "根据id更新角色与操作关联")
    @ApiImplicitParam(paramType = "body", name = "relationRoleOperation", value = "要更新的角色与操作关联", required = true, dataType = "RelationRoleOperation")
    public ResponseEntity<Integer> update(@PathVariable("id") Long id,@RequestBody RelationRoleOperation relationRoleOperation) {
        log.debug("根据id更新角色与操作关联\t id:{} param:{}",id,relationRoleOperation);
        relationRoleOperation.setId(id);
        
        return ResponseEntity.ok(relationRoleOperationService.updateById(relationRoleOperation));
    }

    @DeleteMapping("/{ids}")
    @ApiOperation(value = "根据ids删除角色与操作关联")
    @ApiImplicitParam(paramType = "path", name = "ids", value = "要删除角色与操作关联的ids,逗号分割", required = true, dataType = "String")
    public ResponseEntity<Integer> deleteByIds(@PathVariable("ids") String ids) {
        log.debug("根据ids删除角色与操作关联\t param:{}",ids);
        
        return ResponseEntity.ok(relationRoleOperationService.deleteByIds(ids));
    }
    
    @ApiOperation(value = "根据id查询角色与操作关联", notes = "根据id查询角色与操作关联")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键id", required = true, dataType = "Long")
    @GetMapping("/{id}")
    public ResponseEntity<RelationRoleOperation> findRelationRoleOperation(@PathVariable("id") Long id) {

        return ResponseEntity.ok(relationRoleOperationService.selectById(id));
    }
    
}
