package com.lhiot.ims.datacenter.api;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.datacenter.feign.UiPositionFeign;
import com.lhiot.ims.datacenter.feign.entity.UiPosition;
import com.lhiot.ims.datacenter.feign.entity.type.PositionType;
import com.lhiot.ims.datacenter.model.UiPositionParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author hufan created in 2018/11/21 20:00
 **/
@Api(description = "UI位置接口")
@Slf4j
@RestController
@RequestMapping("/ui-positions")
public class UiPositionApi {
    private final UiPositionFeign uiPositionFeign;

    @Autowired
    public UiPositionApi(UiPositionFeign uiPositionFeign) {
        this.uiPositionFeign = uiPositionFeign;
    }

    /*@ApiOperation("添加UI位置")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "uiPosition", value = "位置信息", dataType = "UiPosition", required = true)
    @PostMapping("/")
    @Deprecated
    public ResponseEntity create(@RequestBody UiPosition uiPosition) {
        log.debug("添加UI位置\t param:{}", uiPosition);

        ResponseEntity entity = uiPositionFeign.create(uiPosition);
        if (Objects.nonNull(entity) && entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        return ResponseEntity.ok(entity.getBody());
    }
*/
    /*@ApiOperation("修改UI位置")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "UI位置id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = ApiParamType.BODY, name = "uiPosition", value = "UiPosition", dataType = "UiPosition", required = true)
    })
    @PutMapping("/{id}")
    @Deprecated
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody UiPosition uiPosition) {
        log.debug("根据id修改UI位置\t id:{} param:{}", id, uiPosition);

        ResponseEntity entity = uiPositionFeign.update(id, uiPosition);
        if (entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        return ResponseEntity.ok(entity.getBody());
    }*/

    @ApiOperation(value = "根据Id查找UI位置", response = UiPosition.class)
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "UI位置id", dataType = "Long", required = true)
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        log.debug("根据Id查找UI位置\t id:{}", id);

        ResponseEntity entity = uiPositionFeign.findById(id);
        if (entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        UiPosition uiPosition = (UiPosition) entity.getBody();
        return ResponseEntity.ok(uiPosition);
    }

    /*@ApiOperation("根据UI位置Ids删除UI位置")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "ids", value = "多个UI位置Id以英文逗号分隔", dataType = "String", required = true)
    @DeleteMapping("/{ids}")
    @Deprecated
    public ResponseEntity batchDelete(@PathVariable("ids") String ids) {
        log.debug("根据UI位置Ids删除UI位置\t param:{}", ids);

        ResponseEntity entity = uiPositionFeign.batchDelete(ids);
        if (entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        return ResponseEntity.ok(entity.getBody());
    }*/

    @ApiOperation(value = "根据条件分页查询UI位置信息列表", response = UiPosition.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "查询条件", dataType = "UiPositionParam")
    @PostMapping("/pages")
    public ResponseEntity search(@RequestBody UiPositionParam param) {
        log.debug("查询UI位置信息列表\t param:{}", param);

        // 设置默认页数和行数
        int page = Objects.nonNull(param.getPage()) ? param.getPage() : 1;
        int rows = Objects.nonNull(param.getRows()) ? param.getRows() : 10;
        param.setPage(page);
        param.setRows(rows);

        ResponseEntity entity = uiPositionFeign.pages(param);
        if (entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        Pages pages = (Pages) entity.getBody();
        return ResponseEntity.ok(pages);
    }

    @ApiOperation(value = "查询板块位置列表集合", response = String.class, responseContainer = "List")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "uiPositionParam", value = "板块位置", dataType = "UiPositionParam")
    @PostMapping("/")
    public ResponseEntity list(@RequestBody UiPositionParam uiPositionParam) {
        log.debug("查询板块位置列表集合\t param{}", uiPositionParam);

        ResponseEntity entity = uiPositionFeign.pages(uiPositionParam);
        if (Objects.nonNull(entity) && !entity.getStatusCode().isError()) {
            Pages pages = (Pages) entity.getBody();
            List<UiPosition> uiPositionList = pages.getArray();
            List<PositionType> positionTypeList = uiPositionList.stream().map(UiPosition::getPositionType).collect(Collectors.toList());
            return ResponseEntity.ok(positionTypeList);
        }
        return ResponseEntity.badRequest().body(entity.getBody());
    }

}