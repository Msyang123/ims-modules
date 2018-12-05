package com.lhiot.ims.datacenter.api;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tips;
import com.leon.microx.web.result.Tuple;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.datacenter.feign.UiPositionFeign;
import com.lhiot.ims.datacenter.feign.entity.UiPosition;
import com.lhiot.ims.datacenter.feign.model.UiPositionDetail;
import com.lhiot.ims.datacenter.feign.model.UiPositionResult;
import com.lhiot.ims.datacenter.feign.type.ApplicationType;
import com.lhiot.ims.datacenter.feign.model.UiPositionParam;
import com.lhiot.ims.datacenter.service.UiPositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        if (tips.err()) {
            return ResponseEntity.badRequest().body(Tips.warn(tips.getMessage()));
        }
        UiPositionDetail uiPositionDetail = (UiPositionDetail) tips.getData();
        return ResponseEntity.ok(uiPositionDetail);
    }

    @ApiOperation(value = "根据条件分页查询UI位置信息列表", response = UiPosition.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "查询条件", dataType = "UiPositionParam")
    @PostMapping("/pages")
    public ResponseEntity search(@RequestBody UiPositionParam param) {
        log.debug("查询UI位置信息列表\t param:{}", param);

        param.setApplicationType(ApplicationType.HEALTH_GOOD);
        ResponseEntity entity = uiPositionFeign.pages(param);
        if (entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(Tips.warn(entity.getBody().toString()));
        }
        Pages pages = (Pages) entity.getBody();
        return ResponseEntity.ok(pages);
    }

    @ApiOperation(value = "查询UI位置列表集合（可包括位置的子板块）", response = UiPosition.class, responseContainer = "List")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "uiPositionParam", value = "UI位置信息", dataType = "UiPositionParam")

    @PostMapping("/")
    public ResponseEntity list(@RequestBody UiPositionParam uiPositionParam) {
        log.debug("查询UI位置列表集合\t param{}", uiPositionParam);


        Tips tips = uiPositionService.search(uiPositionParam);
        if (tips.err()) {
            ResponseEntity.badRequest().body(tips.getMessage());
        }
        List<UiPositionResult> uiPositionResults = (List<UiPositionResult>) tips.getData();
        return ResponseEntity.ok(Tuple.of(uiPositionResults));
    }

}