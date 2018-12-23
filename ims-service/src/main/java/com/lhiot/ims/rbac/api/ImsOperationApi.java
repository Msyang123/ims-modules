package com.lhiot.ims.rbac.api;

import com.leon.microx.web.result.Tuple;
import com.leon.microx.web.session.Sessions;
import com.leon.microx.web.swagger.ApiHideBodyProperty;
import com.leon.microx.web.swagger.ApiParamType;
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

/**
 * Description:功能操作接口类
 *
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


    @LogCollection
    @PostMapping("/")
    @ApiOperation(value = "添加的操作", response = ImsOperation.class)
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "imsOperation", value = "要添加的操作", required = true, dataType = "ImsOperation")
    public ResponseEntity create(@RequestBody ImsOperation imsOperation) {
        log.debug("添加的操作\t param:{}", imsOperation);

        return ResponseEntity.ok(imsOperationService.create(imsOperation));
    }

    @LogCollection
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "根据ids删除功能操作")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "ids", value = "要删除功能操作的ids,英文逗号分割", required = true, dataType = "String")
    public ResponseEntity deleteByIds(@PathVariable("ids") String ids) {
        log.debug("根据ids删除功能操作\t param:{}", ids);

        return imsOperationService.deleteByIds(ids) > 0 ? ResponseEntity.noContent().build() : ResponseEntity.badRequest().body("根据ids删除功能操作失败");
    }

    @ApiOperation(value = "根据id查询功能操作", notes = "根据id查询功能操作", response = ImsOperation.class)
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "主键id", required = true, dataType = "Long")
    @GetMapping("/{id}")
    public ResponseEntity findImsOperation(@PathVariable("id") Long id) {
        log.debug("根据id查询功能操作\t param:{}", id);

        return ResponseEntity.ok(imsOperationService.selectById(id));
    }

    @PostMapping("/pages")
    @ApiOperation(value = "查询功能操作分页列表", response = ImsOperation.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "imsOperation", value = "功能操作分页参数", required = true, dataType = "ImsOperation")
    public ResponseEntity pageQuery(@RequestBody ImsOperation imsOperation) {
        log.debug("查询功能操作分页列表\t param:{}", imsOperation);

        return ResponseEntity.ok(imsOperationService.pageList(imsOperation));
    }

    @Sessions.Uncheck
    @ApiOperation(value = "查询和色果膳用户Sesseion权限限制访问接口", response = ImsOperation.class, responseContainer = "List")
    @GetMapping("/mall-user")
    public ResponseEntity selectAuthority(){
        return ResponseEntity.ok(imsOperationService.selectAuthority());
    }

    @GetMapping("/list/{menuIds}")
    @ApiOperation(value = "根据菜单ids查询操作权限列表", notes = "根据菜单ids查询操作权限列表，用英文逗号分隔", response = ImsOperation.class, responseContainer = "List")
    public ResponseEntity listByMenuIds(@PathVariable("menuIds") String menuIds) {
        log.debug("根据菜单ids查询操作权限列表\t param:{}", menuIds);

        return ResponseEntity.ok(Tuple.of(imsOperationService.listByMenuIds(menuIds)));
    }
}
