package com.lhiot.ims.rbac.api;

import com.leon.microx.support.session.Sessions;
import com.leon.microx.support.swagger.ApiParamType;
import com.lhiot.ims.rbac.domain.MngrAuthMenu;
import com.lhiot.ims.rbac.service.MngrAuthMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lhiot.ims.rbac.common.PagerResultObject;

/**
* Description:菜单接口类
* @author yijun
* @date 2018/09/29
*/
@Api(description = "菜单接口")
@Slf4j
@RestController
@RequestMapping("/mngr-auth-menu")
public class MngrAuthMenuApi {

    private final MngrAuthMenuService mngrAuthMenuService;

    @Autowired
    public MngrAuthMenuApi(MngrAuthMenuService mngrAuthMenuService) {
        this.mngrAuthMenuService = mngrAuthMenuService;
    }

    @PostMapping("/create")
    @ApiOperation(value = "添加菜单")
    @ApiImplicitParam(paramType = "body", name = "mngrAuthMenu", value = "要添加的菜单", required = true, dataType = "MngrAuthMenu")
    public ResponseEntity<Integer> create(@RequestBody MngrAuthMenu mngrAuthMenu) {
        log.debug("添加菜单\t param:{}",mngrAuthMenu);
        
        return ResponseEntity.ok(mngrAuthMenuService.create(mngrAuthMenu));
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "根据id更新菜单")
    @ApiImplicitParam(paramType = "body", name = "mngrAuthMenu", value = "要更新的菜单", required = true, dataType = "MngrAuthMenu")
    public ResponseEntity<Integer> update(@PathVariable("id") Long id,@RequestBody MngrAuthMenu mngrAuthMenu) {
        log.debug("根据id更新菜单\t id:{} param:{}",id,mngrAuthMenu);
        mngrAuthMenu.setId(id);
        
        return ResponseEntity.ok(mngrAuthMenuService.updateById(mngrAuthMenu));
    }

    @DeleteMapping("/{ids}")
    @ApiOperation(value = "根据ids删除菜单")
    @ApiImplicitParam(paramType = "path", name = "ids", value = "要删除菜单的ids,逗号分割", required = true, dataType = "String")
    public ResponseEntity<Integer> deleteByIds(@PathVariable("ids") String ids) {
        log.debug("根据ids删除菜单\t param:{}",ids);
        
        return ResponseEntity.ok(mngrAuthMenuService.deleteByIds(ids));
    }
    
    @ApiOperation(value = "根据id查询菜单", notes = "根据id查询菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.HEADER, name = Sessions.HTTP_HEADER_NAME, value = "session-id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "path", name = "id", value = "主键id", required = true, dataType = "Long")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MngrAuthMenu> findMngrAuthMenu(@PathVariable("id") Long id) {

        return ResponseEntity.ok(mngrAuthMenuService.selectById(id));
    }
    
    @GetMapping("/page/query")
    @ApiOperation(value = "查询菜单分页列表")
    public ResponseEntity<PagerResultObject<MngrAuthMenu>> pageQuery(MngrAuthMenu mngrAuthMenu){
        log.debug("查询菜单分页列表\t param:{}",mngrAuthMenu);
        
        return ResponseEntity.ok(mngrAuthMenuService.pageList(mngrAuthMenu));
    }
    
}
