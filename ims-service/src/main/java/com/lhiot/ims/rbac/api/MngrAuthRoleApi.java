package com.lhiot.ims.rbac.api;

import com.lhiot.ims.rbac.domain.MngrAuthRole;
import com.lhiot.ims.rbac.service.MngrAuthRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lhiot.ims.rbac.common.PagerResultObject;

/**
* Description:角色接口类
* @author yijun
* @date 2018/09/29
*/
@Api(description = "角色接口")
@Slf4j
@RestController
@RequestMapping("/mngrAuthRole")
public class MngrAuthRoleApi {

    private final MngrAuthRoleService mngrAuthRoleService;

    @Autowired
    public MngrAuthRoleApi(MngrAuthRoleService mngrAuthRoleService) {
        this.mngrAuthRoleService = mngrAuthRoleService;
    }

    @PostMapping("/create")
    @ApiOperation(value = "添加角色")
    @ApiImplicitParam(paramType = "body", name = "mngrAuthRole", value = "要添加的角色", required = true, dataType = "MngrAuthRole")
    public ResponseEntity<Integer> create(@RequestBody MngrAuthRole mngrAuthRole) {
        log.debug("添加角色\t param:{}",mngrAuthRole);
        
        return ResponseEntity.ok(mngrAuthRoleService.create(mngrAuthRole));
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "根据id更新角色")
    @ApiImplicitParam(paramType = "body", name = "mngrAuthRole", value = "要更新的角色", required = true, dataType = "MngrAuthRole")
    public ResponseEntity<Integer> update(@PathVariable("id") Long id,@RequestBody MngrAuthRole mngrAuthRole) {
        log.debug("根据id更新角色\t id:{} param:{}",id,mngrAuthRole);
        mngrAuthRole.setId(id);
        
        return ResponseEntity.ok(mngrAuthRoleService.updateById(mngrAuthRole));
    }

    @DeleteMapping("/{ids}")
    @ApiOperation(value = "根据ids删除角色")
    @ApiImplicitParam(paramType = "path", name = "ids", value = "要删除角色的ids,逗号分割", required = true, dataType = "String")
    public ResponseEntity<Integer> deleteByIds(@PathVariable("ids") String ids) {
        log.debug("根据ids删除角色\t param:{}",ids);
        
        return ResponseEntity.ok(mngrAuthRoleService.deleteByIds(ids));
    }
    
    @ApiOperation(value = "根据id查询角色", notes = "根据id查询角色")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键id", required = true, dataType = "Long")
    @GetMapping("/{id}")
    public ResponseEntity<MngrAuthRole> findMngrAuthRole(@PathVariable("id") Long id) {

        return ResponseEntity.ok(mngrAuthRoleService.selectById(id));
    }
    
    @GetMapping("/page/query")
    @ApiOperation(value = "查询角色分页列表")
    public ResponseEntity<PagerResultObject<MngrAuthRole>> pageQuery(MngrAuthRole mngrAuthRole){
        log.debug("查询角色分页列表\t param:{}",mngrAuthRole);
        
        return ResponseEntity.ok(mngrAuthRoleService.pageList(mngrAuthRole));
    }
    
}
