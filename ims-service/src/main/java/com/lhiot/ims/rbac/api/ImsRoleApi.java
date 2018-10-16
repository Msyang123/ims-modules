package com.lhiot.ims.rbac.api;

import com.leon.microx.support.result.Pages;
import com.lhiot.ims.rbac.domain.ImsRole;
import com.lhiot.ims.rbac.service.ImsRelationRoleMenuService;
import com.lhiot.ims.rbac.service.ImsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
* Description:角色接口类
* @author yijun
* @date 2018/09/29
*/
@Api(description = "角色接口")
@Slf4j
@RestController
@RequestMapping("/ims-role")
public class ImsRoleApi {

    private final ImsRoleService imsRoleService;
    private final ImsRelationRoleMenuService imsRelationRoleMenuService;

    @Autowired
    public ImsRoleApi(ImsRoleService imsRoleService, ImsRelationRoleMenuService imsRelationRoleMenuService) {
        this.imsRoleService = imsRoleService;
        this.imsRelationRoleMenuService = imsRelationRoleMenuService;
    }

    @PostMapping("/")
    @ApiOperation(value = "添加角色")
    @ApiImplicitParam(paramType = "body", name = "imsRole", value = "要添加的角色", required = true, dataType = "ImsRole")
    public ResponseEntity<ImsRole> create(@RequestBody ImsRole imsRole) {
        log.debug("添加角色\t param:{}",imsRole);
        
        return ResponseEntity.ok(imsRoleService.create(imsRole));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "根据id更新角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "要更新的角色id", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "body", name = "imsRole", value = "要更新的角色", required = true, dataType = "ImsRole")
    })
    public ResponseEntity<ImsRole> update(@PathVariable("id") Long id,@RequestBody ImsRole imsRole) {
        log.debug("根据id更新角色\t id:{} param:{}",id,imsRole);
        imsRole.setId(id);
        
        return ResponseEntity.ok(imsRoleService.updateById(imsRole));
    }

    @PutMapping("/relation/{id}/{menuIds}")
    @ApiOperation(value = "根据id更新角色与菜单关联（包括与操作关联）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "要更新的角色id", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "path", name = "menuIds", value = "要更新的菜单ids", required = true, dataType = "String")
    })
    public ResponseEntity<Integer> update(@PathVariable("id") Long id,@PathVariable("menuIds") String menuIds) {
        log.debug("根据id更新角色与菜单关联（包括与操作关联）\t id:{} param:{}",id,menuIds);
        return ResponseEntity.ok(imsRelationRoleMenuService.create(id,menuIds));
    }

    @DeleteMapping("/{ids}")
    @ApiOperation(value = "根据ids删除角色")
    @ApiImplicitParam(paramType = "path", name = "ids", value = "要删除角色的ids,逗号分割", required = true, dataType = "String")
    public ResponseEntity<Integer> deleteByIds(@PathVariable("ids") String ids) {
        log.debug("根据ids删除角色\t param:{}",ids);
        
        return ResponseEntity.ok(imsRoleService.deleteByIds(ids));
    }
    
    @ApiOperation(value = "根据id查询角色", notes = "根据id查询角色")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键id", required = true, dataType = "Long")
    @GetMapping("/{id}")
    public ResponseEntity<ImsRole> findImsRole(@PathVariable("id") Long id) {

        return ResponseEntity.ok(imsRoleService.selectById(id));
    }
    
    @PostMapping("/pages")
    @ApiOperation(value = "查询角色分页列表")
    @ApiImplicitParam(paramType = "body", name = "imsRole", value = "角色查询参数", required = true, dataType = "ImsRole")
    public ResponseEntity<Pages<ImsRole>> pageQuery(@RequestBody ImsRole imsRole){
        log.debug("查询角色分页列表\t param:{}",imsRole);
        
        return ResponseEntity.ok(imsRoleService.pageList(imsRole));
    }
    
}
