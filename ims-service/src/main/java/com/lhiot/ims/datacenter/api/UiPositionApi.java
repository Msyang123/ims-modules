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
import java.util.stream.Collectors;

/**
 * @author hufan created in 2018/11/21 20:00
 **/
@Api("UI位置接口")
@Slf4j
@RestController
@RequestMapping("/ui-positions")
public class UiPositionApi {
    private final UiPositionFeign uiPositionFeign;

    @Autowired
    public UiPositionApi(UiPositionFeign uiPositionFeign) {
        this.uiPositionFeign = uiPositionFeign;
    }

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

    @ApiOperation(value = "根据条件分页查询UI位置信息列表", response = UiPosition.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "查询条件", dataType = "UiPositionParam")
    @PostMapping("/pages")
    public ResponseEntity search(@RequestBody UiPositionParam param) {
        log.debug("查询UI位置信息列表\t param:{}", param);

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
        if (entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        Pages pages = (Pages) entity.getBody();
        List<UiPosition> uiPositionList = pages.getArray();
        List<PositionType> positionTypeList = uiPositionList.stream().map(UiPosition::getPositionType).collect(Collectors.toList());
        return ResponseEntity.ok(positionTypeList);
    }

}