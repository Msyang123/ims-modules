package com.lhiot.ims.rbac.api;

import com.lhiot.ims.rbac.domain.MngrAuthUser;
import com.lhiot.ims.rbac.service.MngrAuthUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lhiot.ims.rbac.common.PagerResultObject;

/**
* Description:用户接口类
* @author yijun
* @date 2018/09/29
*/
@Api(description = "用户接口")
@Slf4j
@RestController
@RequestMapping("/mngrAuthUser")
public class MngrAuthUserApi {

    private final MngrAuthUserService mngrAuthUserService;

    @Autowired
    public MngrAuthUserApi(MngrAuthUserService mngrAuthUserService) {
        this.mngrAuthUserService = mngrAuthUserService;
    }

    @PostMapping("/create")
    @ApiOperation(value = "添加用户")
    @ApiImplicitParam(paramType = "body", name = "mngrAuthUser", value = "要添加的用户", required = true, dataType = "MngrAuthUser")
    public ResponseEntity<Integer> create(@RequestBody MngrAuthUser mngrAuthUser) {
        log.debug("添加用户\t param:{}",mngrAuthUser);
        
        return ResponseEntity.ok(mngrAuthUserService.create(mngrAuthUser));
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "根据id更新用户")
    @ApiImplicitParam(paramType = "body", name = "mngrAuthUser", value = "要更新的用户", required = true, dataType = "MngrAuthUser")
    public ResponseEntity<Integer> update(@PathVariable("id") Long id,@RequestBody MngrAuthUser mngrAuthUser) {
        log.debug("根据id更新用户\t id:{} param:{}",id,mngrAuthUser);
        mngrAuthUser.setId(id);
        
        return ResponseEntity.ok(mngrAuthUserService.updateById(mngrAuthUser));
    }

    @DeleteMapping("/{ids}")
    @ApiOperation(value = "根据ids删除用户")
    @ApiImplicitParam(paramType = "path", name = "ids", value = "要删除用户的ids,逗号分割", required = true, dataType = "String")
    public ResponseEntity<Integer> deleteByIds(@PathVariable("ids") String ids) {
        log.debug("根据ids删除用户\t param:{}",ids);
        
        return ResponseEntity.ok(mngrAuthUserService.deleteByIds(ids));
    }
    
    @ApiOperation(value = "根据id查询用户", notes = "根据id查询用户")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键id", required = true, dataType = "Long")
    @GetMapping("/{id}")
    public ResponseEntity<MngrAuthUser> findMngrAuthUser(@PathVariable("id") Long id) {

        return ResponseEntity.ok(mngrAuthUserService.selectById(id));
    }
    
    @GetMapping("/page/query")
    @ApiOperation(value = "查询用户分页列表")
    public ResponseEntity<PagerResultObject<MngrAuthUser>> pageQuery(MngrAuthUser mngrAuthUser){
        log.debug("查询用户分页列表\t param:{}",mngrAuthUser);
        
        return ResponseEntity.ok(mngrAuthUserService.pageList(mngrAuthUser));
    }
    
}
