package com.lhiot.ims.rbac.api;

import com.leon.microx.support.result.Multiple;
import com.leon.microx.support.session.Sessions;
import com.lhiot.ims.rbac.common.PagerResultObject;
import com.lhiot.ims.rbac.domain.ImsMenu;
import com.lhiot.ims.rbac.service.ImsMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Description:菜单接口类
 *
 * @author yijun
 * @date 2018/09/29
 */
@Api(description = "菜单接口")
@Slf4j
@RestController
@RequestMapping("/ims-menu")
public class ImsMenuApi {

    private final ImsMenuService imsMenuService;

    @Autowired
    public ImsMenuApi(ImsMenuService imsMenuService) {
        this.imsMenuService = imsMenuService;
    }

    @PostMapping("/create")
    @ApiOperation(value = "添加菜单")
    @ApiImplicitParam(paramType = "body", name = "imsMenu", value = "要添加的菜单", required = true, dataType = "ImsMenu")
    public ResponseEntity<Integer> create(@RequestBody ImsMenu imsMenu) {
        log.debug("添加菜单\t param:{}", imsMenu);

        return ResponseEntity.ok(imsMenuService.create(imsMenu));
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "根据id更新菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id",value = "菜单id",required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "body", name = "imsMenu", value = "要更新的菜单", required = true, dataType = "ImsMenu")
    })
    public ResponseEntity<Integer> update(@PathVariable("id") Long id, @RequestBody ImsMenu imsMenu) {
        log.debug("根据id更新菜单\t id:{} param:{}", id, imsMenu);
        imsMenu.setId(id);

        return ResponseEntity.ok(imsMenuService.updateById(imsMenu));
    }

    @DeleteMapping("/{ids}")
    @ApiOperation(value = "根据ids删除菜单")
    @ApiImplicitParam(paramType = "path", name = "ids", value = "要删除菜单的ids,逗号分割", required = true, dataType = "String")
    public ResponseEntity<Integer> deleteByIds(@PathVariable("ids") String ids) {
        log.debug("根据ids删除菜单\t param:{}", ids);

        return ResponseEntity.ok(imsMenuService.deleteByIds(ids));
    }

    @ApiOperation(value = "根据id查询菜单", notes = "根据id查询菜单")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键id", required = true, dataType = "Long")
    @GetMapping("/{id}")
    public ResponseEntity<ImsMenu> findImsMenu(@PathVariable("id") Long id) {

        return ResponseEntity.ok(imsMenuService.selectById(id));
    }

    @PostMapping("/pages/query")
    @ApiOperation(value = "查询菜单分页列表")
    @ApiImplicitParam(paramType = "body", name = "imsMenu", value = "菜单分页查询参数", required = true, dataType = "ImsMenu")
    public ResponseEntity<PagerResultObject<ImsMenu>> pageQuery(@RequestBody ImsMenu imsMenu) {
        log.debug("查询菜单分页列表\t param:{}", imsMenu);

        return ResponseEntity.ok(imsMenuService.pageList(imsMenu));
    }


    @GetMapping("/menus")
    @ApiOperation(value = "查询菜单列表(非系统)")
    public ResponseEntity<Multiple<ImsMenu>> listImsMenus(Sessions.User user) {
        log.debug("查询菜单列表\t param:");
        //通过session获取用户id
        Long id = (Long) user.getUser().get("id");
        return ResponseEntity.ok(Multiple.of(imsMenuService.listImsMenus(id)));
    }

    @GetMapping("/systems")
    @ApiOperation(value = "查询菜单列表(系统)")
    public ResponseEntity<Multiple<ImsMenu>> listImsSystems(Sessions.User user) {
        log.debug("查询菜单列表(系统)");
        //通过session获取用户id
        Long id = (Long) user.getUser().get("id");
        return ResponseEntity.ok(Multiple.of(imsMenuService.listImsSystems(id)));
    }

}
