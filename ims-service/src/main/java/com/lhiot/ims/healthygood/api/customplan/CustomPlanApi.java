package com.lhiot.ims.healthygood.api.customplan;

import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.healthygood.feign.customplan.CustomPlanFeign;
import com.lhiot.ims.healthygood.feign.customplan.model.CustomPlanDetailResult;
import com.lhiot.ims.healthygood.feign.customplan.model.CustomPlanParam;
import com.lhiot.ims.healthygood.feign.customplan.model.CustomPlanResult;
import com.lhiot.ims.rbac.aspect.LogCollection;
import com.lhiot.util.FeginResponseTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author hufan created in 2018/12/1 15:20
 **/
@Api(description = "定制计划接口")
@Slf4j
@RestController
public class CustomPlanApi {
    private final CustomPlanFeign customPlanFeign;

    public CustomPlanApi(CustomPlanFeign customPlanFeign) {
        this.customPlanFeign = customPlanFeign;
    }

    @LogCollection
    @ApiOperation("添加定制计划")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "customPlanDetailResult", value = "定制计划", dataType = "CustomPlanDetailResult", required = true)
    @PostMapping("/custom-plans")
    public ResponseEntity create(@Valid @RequestBody CustomPlanDetailResult customPlanDetailResult) {
        log.debug("添加定制计划\t param:{}", customPlanDetailResult);

        ResponseEntity entity = customPlanFeign.create(customPlanDetailResult);
        return FeginResponseTools.convertCreateResponse(entity);
    }

    @GetMapping("/custom-plans/{id}")
    @ApiOperation(value = "定制计划详细信息", response = CustomPlanDetailResult.class)
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "定制计划id", dataType = "Long", required = true)
    public ResponseEntity customPlans(@PathVariable Long id) {
        log.debug("定制计划详细信息\t param:{}", id);

        ResponseEntity entity = customPlanFeign.findById(id);
        return FeginResponseTools.convertNoramlResponse(entity);
    }

    @LogCollection
    @ApiOperation("修改定制计划")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "定制计划id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = ApiParamType.BODY, name = "customPlanDetailResult", value = "定制计划", dataType = "CustomPlanDetailResult", required = true)
    })
    @PutMapping("/custom-plans/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @Valid @RequestBody CustomPlanDetailResult customPlanDetailResult) {
        log.debug("修改定制计划\t param:{}", customPlanDetailResult);

        ResponseEntity entity = customPlanFeign.update(id, customPlanDetailResult);
        return FeginResponseTools.convertUpdateResponse(entity);
    }

    @LogCollection
    @ApiOperation("修改定制计划商品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "定制计划id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = ApiParamType.BODY, name = "customPlanDetailResult", value = "定制计划商品", dataType = "CustomPlanDetailResult", required = true)
    })
    @PutMapping("/custom-plan-products/{id}")
    public ResponseEntity updateProduct(@PathVariable("id") Long id, @RequestBody CustomPlanDetailResult customPlanDetailResult) {
        log.debug("修改定制计划\t param:{}", customPlanDetailResult);

        ResponseEntity entity = customPlanFeign.updateProduct(id, customPlanDetailResult);
        return FeginResponseTools.convertUpdateResponse(entity);
    }

    @LogCollection
    @ApiOperation("根据ids删除定制计划")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "ids", value = "多个定制计划id以英文逗号分隔", dataType = "String", required = true)
    @DeleteMapping("/custom-plans/{ids}")
    public ResponseEntity batchDelete(@PathVariable("ids") String ids) {
        log.debug("批量删除定制计划\t param:{}", ids);

        ResponseEntity entity = customPlanFeign.batchDelete(ids);
        return FeginResponseTools.convertDeleteResponse(entity);
    }

    @ApiOperation(value = "根据条件分页查询定制计划信息列表", response = CustomPlanResult.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "查询条件", dataType = "CustomPlanParam")
    @PostMapping("/custom-plans/pages")
    public ResponseEntity search(@RequestBody CustomPlanParam param) {
        log.debug("根据条件分页查询定制计划信息列表\t param:{}", param);

        ResponseEntity entity = customPlanFeign.search(param);
        return FeginResponseTools.convertNoramlResponse(entity);
    }
}