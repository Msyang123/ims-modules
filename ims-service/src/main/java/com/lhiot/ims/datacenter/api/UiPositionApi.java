package com.lhiot.ims.datacenter.api;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tips;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.datacenter.feign.UiPositionFeign;
import com.lhiot.ims.datacenter.feign.entity.UiPosition;
import com.lhiot.ims.datacenter.feign.model.UiPositionDetail;
import com.lhiot.ims.datacenter.feign.model.UiPositionParam;
import com.lhiot.ims.datacenter.feign.model.UiPositionResult;
import com.lhiot.ims.datacenter.service.UiPositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2018/11/21 20:00
 **/
@Api(description = "UI位置接口")
@Slf4j
@RestController
@RequestMapping("/ui-positions")
public class UiPositionApi {
    private final UiPositionFeign uiPositionFeign;
    private final UiPositionService uiPositionService;

    @Autowired
    public UiPositionApi(UiPositionFeign uiPositionFeign, UiPositionService uiPositionService) {
        this.uiPositionFeign = uiPositionFeign;
        this.uiPositionService = uiPositionService;
    }

    @ApiOperation(value = "根据Id查找UI位置", response = UiPositionDetail.class)
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "UI位置id", dataType = "Long", required = true)
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        log.debug("根据Id查找UI位置\t id:{}", id);

        Tips tips = uiPositionService.findById(id);
        return  tips.err() ? ResponseEntity.badRequest().body(tips.getMessage()) : ResponseEntity.ok(tips.getData());
    }

    @ApiOperation(value = "根据条件分页查询UI位置信息列表（可包括位置的子板块）", response = UiPositionResult.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "uiPositionParam", value = "UI位置信息", dataType = "UiPositionParam")
    @PostMapping("/pages")
    public ResponseEntity search(@RequestBody UiPositionParam uiPositionParam) {
        log.debug("查询UI位置列表集合\t param{}", uiPositionParam);

        Tips<Pages<UiPositionResult>> tips = uiPositionService.search(uiPositionParam);
        return tips.err() ? ResponseEntity.badRequest().body(tips.getMessage()) : ResponseEntity.ok(tips.getData());
    }

}