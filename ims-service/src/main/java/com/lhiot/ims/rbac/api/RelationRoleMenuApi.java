package com.lhiot.ims.rbac.api;

import com.lhiot.ims.rbac.domain.RelationRoleMenu;
import com.lhiot.ims.rbac.service.RelationRoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
* Description:角色与菜单关联接口类
* @author yijun
* @date 2018/09/29
*/
@Api(description = "角色与菜单关联接口")
@Slf4j
@RestController
@RequestMapping("/relationRoleMenu")
public class RelationRoleMenuApi {

    private final RelationRoleMenuService relationRoleMenuService;

    @Autowired
    public RelationRoleMenuApi(RelationRoleMenuService relationRoleMenuService) {
        this.relationRoleMenuService = relationRoleMenuService;
    }

    @PostMapping("/create")
    @ApiOperation(value = "添加角色与菜单关联")
    @ApiImplicitParam(paramType = "body", name = "relationRoleMenu", value = "要添加的角色与菜单关联", required = true, dataType = "RelationRoleMenu")
    public ResponseEntity<Integer> create(@RequestBody RelationRoleMenu relationRoleMenu) {
        log.debug("添加角色与菜单关联\t param:{}",relationRoleMenu);
        
        return ResponseEntity.ok(relationRoleMenuService.create(relationRoleMenu));
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "根据id更新角色与菜单关联")
    @ApiImplicitParam(paramType = "body", name = "relationRoleMenu", value = "要更新的角色与菜单关联", required = true, dataType = "RelationRoleMenu")
    public ResponseEntity<Integer> update(@PathVariable("id") Long id,@RequestBody RelationRoleMenu relationRoleMenu) {
        log.debug("根据id更新角色与菜单关联\t id:{} param:{}",id,relationRoleMenu);
        relationRoleMenu.setId(id);
        
        return ResponseEntity.ok(relationRoleMenuService.updateById(relationRoleMenu));
    }

    @DeleteMapping("/{ids}")
    @ApiOperation(value = "根据ids删除角色与菜单关联")
    @ApiImplicitParam(paramType = "path", name = "ids", value = "要删除角色与菜单关联的ids,逗号分割", required = true, dataType = "String")
    public ResponseEntity<Integer> deleteByIds(@PathVariable("ids") String ids) {
        log.debug("根据ids删除角色与菜单关联\t param:{}",ids);
        
        return ResponseEntity.ok(relationRoleMenuService.deleteByIds(ids));
    }
    
    @ApiOperation(value = "根据id查询角色与菜单关联", notes = "根据id查询角色与菜单关联")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键id", required = true, dataType = "Long")
    @GetMapping("/{id}")
    public ResponseEntity<RelationRoleMenu> findRelationRoleMenu(@PathVariable("id") Long id) {

        return ResponseEntity.ok(relationRoleMenuService.selectById(id));
    }

}
