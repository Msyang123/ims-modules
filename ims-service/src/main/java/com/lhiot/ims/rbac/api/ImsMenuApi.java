package com.lhiot.ims.rbac.api;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tuple;
import com.leon.microx.web.session.Sessions;
import com.lhiot.ims.datacenter.feign.type.ApplicationType;
import com.lhiot.ims.rbac.aspect.LogCollection;
import com.lhiot.ims.rbac.domain.ImsMenu;
import com.lhiot.ims.rbac.domain.MenuDisplay;
import com.lhiot.ims.rbac.service.ImsMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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


    @LogCollection
    @PostMapping("/")
    @ApiOperation(value = "添加菜单")
    @ApiImplicitParam(paramType = "body", name = "imsMenu", value = "要添加的菜单", required = true, dataType = "ImsMenu")
    public ResponseEntity<ImsMenu> create(@RequestBody ImsMenu imsMenu) {
        log.debug("添加菜单\t param:{}", imsMenu);

        return ResponseEntity.ok(imsMenuService.create(imsMenu));
    }


    @LogCollection
    @PutMapping("/{id}")
    @ApiOperation(value = "根据id更新菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id",value = "菜单id",required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "body", name = "imsMenu", value = "要更新的菜单", required = true, dataType = "ImsMenu")
    })
    public ResponseEntity<ImsMenu> update(@PathVariable("id") Long id, @RequestBody ImsMenu imsMenu) {
        log.debug("根据id更新菜单\t id:{} param:{}", id, imsMenu);
        imsMenu.setId(id);

        return ResponseEntity.ok(imsMenuService.updateById(imsMenu));
    }


    @LogCollection
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

    @PostMapping("/pages")
    @ApiOperation(value = "查询菜单分页列表")
    @ApiImplicitParam(paramType = "body", name = "imsMenu", value = "菜单分页查询参数", required = true, dataType = "ImsMenu")
    public ResponseEntity<Pages<ImsMenu>> pageQuery(@RequestBody ImsMenu imsMenu) {
        log.debug("查询菜单分页列表\t param:{}", imsMenu);

        return ResponseEntity.ok(imsMenuService.pageList(imsMenu));
    }


    @GetMapping("/user/left-tree")
    @ApiOperation(value = "查询菜单列表(非系统)")
    public ResponseEntity<Tuple<MenuDisplay>> listImsMenus(Sessions.User user) {
        log.debug("查询菜单列表\t param:");
        //通过session获取用户id
        Long id = (Long) user.getUser().get("id");

        List<MenuDisplay> menuDisplayList = Objects.requireNonNull(imsMenuService.listImsMenus(id)).stream()
                .map(MenuDisplay::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(Tuple.of(menuDisplayList));
    }

    @GetMapping("/list/all")
    @ApiOperation(value = "查询菜单列表(包括系统)")
    public ResponseEntity<Tuple<MenuDisplay>> listIncludeSystemImsMenus() {
        log.debug("查询菜单列表\t param:");
        List<MenuDisplay> menuDisplayList = imsMenuService.listIncludeSystemImsMenus().stream().map(MenuDisplay::new).collect(Collectors.toList());
        return ResponseEntity.ok(Tuple.of(menuDisplayList));
    }

    @GetMapping("/list/pid")
    @ApiOperation(value = "依据父id查询菜单列表(非系统)")
    public ResponseEntity<Tuple<MenuDisplay>> listImsMenus(@RequestParam("pid") long pid, Sessions.User user) {
        log.debug("查询菜单列表\t param:");
        //通过session获取用户id
        Long id = (Long) user.getUser().get("id");
        List<MenuDisplay> menuDisplayList = Objects.requireNonNull(imsMenuService.listImsMenus(pid,id)).stream()
                .map(MenuDisplay::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(Tuple.of(menuDisplayList));
    }

    @GetMapping("/list/systems")
    @ApiOperation(value = "查询菜单列表(系统)")
    public ResponseEntity<Tuple<ImsMenu>> listImsSystems(Sessions.User user) {
        log.debug("查询菜单列表(系统)");
        //通过session获取用户id
        Long id = (Long) user.getUser().get("id");
        List<ImsMenu> imsMenus = imsMenuService.listImsSystems(id);
        imsMenus.forEach(imsMenu -> {
            if (Objects.isNull(imsMenu.getPId())){
                switch (imsMenu.getName()) {
                    case "后台管理系统":
                        imsMenu.setApplicationType(ApplicationType.HEALTH_GOOD);
                        break;
                    default:
                        break;
                }
            }
        });
        return ResponseEntity.ok(Tuple.of(imsMenus));
    }

}
