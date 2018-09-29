package com.lhiot.ims.rbac.api;

import com.lhiot.ims.rbac.domain.RelationUserRole;
import com.lhiot.ims.rbac.service.RelationUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
* Description:用户与角色关联接口类
* @author yijun
* @date 2018/09/29
*/
@Api(description = "用户与角色关联接口")
@Slf4j
@RestController
@RequestMapping("/relationUserRole")
public class RelationUserRoleApi {

    private final RelationUserRoleService relationUserRoleService;

    @Autowired
    public RelationUserRoleApi(RelationUserRoleService relationUserRoleService) {
        this.relationUserRoleService = relationUserRoleService;
    }

    @PostMapping("/create")
    @ApiOperation(value = "添加用户与角色关联")
    @ApiImplicitParam(paramType = "body", name = "relationUserRole", value = "要添加的用户与角色关联", required = true, dataType = "RelationUserRole")
    public ResponseEntity<Integer> create(@RequestBody RelationUserRole relationUserRole) {
        log.debug("添加用户与角色关联\t param:{}",relationUserRole);
        
        return ResponseEntity.ok(relationUserRoleService.create(relationUserRole));
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "根据id更新用户与角色关联")
    @ApiImplicitParam(paramType = "body", name = "relationUserRole", value = "要更新的用户与角色关联", required = true, dataType = "RelationUserRole")
    public ResponseEntity<Integer> update(@PathVariable("id") Long id,@RequestBody RelationUserRole relationUserRole) {
        log.debug("根据id更新用户与角色关联\t id:{} param:{}",id,relationUserRole);
        relationUserRole.setId(id);
        
        return ResponseEntity.ok(relationUserRoleService.updateById(relationUserRole));
    }

    @DeleteMapping("/{ids}")
    @ApiOperation(value = "根据ids删除用户与角色关联")
    @ApiImplicitParam(paramType = "path", name = "ids", value = "要删除用户与角色关联的ids,逗号分割", required = true, dataType = "String")
    public ResponseEntity<Integer> deleteByIds(@PathVariable("ids") String ids) {
        log.debug("根据ids删除用户与角色关联\t param:{}",ids);
        
        return ResponseEntity.ok(relationUserRoleService.deleteByIds(ids));
    }
    
    @ApiOperation(value = "根据id查询用户与角色关联", notes = "根据id查询用户与角色关联")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键id", required = true, dataType = "Long")
    @GetMapping("/{id}")
    public ResponseEntity<RelationUserRole> findRelationUserRole(@PathVariable("id") Long id) {

        return ResponseEntity.ok(relationUserRoleService.selectById(id));
    }
    
}
