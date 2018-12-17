package com.lhiot.ims.healthygood.api.customplan;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.session.Sessions;
import com.leon.microx.web.swagger.ApiHideBodyProperty;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.healthygood.feign.customplan.CustomPlanSpecificationStandardFeign;
import com.lhiot.ims.healthygood.feign.customplan.entity.CustomPlanSpecificationStandard;
import com.lhiot.ims.healthygood.feign.customplan.model.CustomPlanSpecificationStandardParam;
import com.lhiot.ims.rbac.aspect.LogCollection;
import com.lhiot.util.FeginResponseTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Description:定制计划规格基础数据接口类
 *
 * @author hufan
 * @date 2018/12/08
 */
@Api(description = "定制计划规格基础数据接口")
@Slf4j
@RestController
public class CustomPlanSpecificationStandardApi {

    private final CustomPlanSpecificationStandardFeign customPlanSpecificationStandardFeign;

    @Autowired
    public CustomPlanSpecificationStandardApi(CustomPlanSpecificationStandardFeign customPlanSpecificationStandardFeign) {
        this.customPlanSpecificationStandardFeign = customPlanSpecificationStandardFeign;
    }

    @LogCollection
    @PostMapping("/custom-plan-specification-standards")
    @ApiOperation(value = "添加定制计划规格基础数据")
    @ApiHideBodyProperty({"id"})
    public ResponseEntity create(@RequestBody CustomPlanSpecificationStandard customPlanSpecificationStandard) {
        log.debug("添加定制计划规格基础数据\t param:{}", customPlanSpecificationStandard);

        ResponseEntity entity = customPlanSpecificationStandardFeign.create(customPlanSpecificationStandard);
        return FeginResponseTools.convertCreateResponse(entity);
    }

    @LogCollection
    @PutMapping("/custom-plan-specification-standards/{id}")
    @ApiImplicitParam(paramType = ApiParamType.PATH, value = "id", name = "id", dataType = "long", required = true)
    @ApiOperation(value = "根据id更新定制计划规格基础数据")
    @ApiHideBodyProperty({"id"})
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody CustomPlanSpecificationStandard customPlanSpecificationStandard) {
        log.debug("根据id更新定制计划规格基础数据\t id:{} param:{}", id, customPlanSpecificationStandard);

        ResponseEntity entity = customPlanSpecificationStandardFeign.update(id, customPlanSpecificationStandard);
        return FeginResponseTools.convertUpdateResponse(entity);
    }

    @LogCollection
    @Sessions.Uncheck
    @DeleteMapping("/custom-plan-specification-standards/{ids}")
    @ApiOperation(value = "根据ids删除定制计划规格基础数据")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "ids", value = "要删除定制计划规格基础数据的ids,逗号分割", required = true, dataType = "String")
    public ResponseEntity deleteByIds(@PathVariable("ids") String ids) {
        log.debug("根据ids删除定制计划规格基础数据\t param:{}", ids);

        ResponseEntity entity = customPlanSpecificationStandardFeign.deleteByIds(ids);
        return FeginResponseTools.convertDeleteResponse(entity);
    }

    @GetMapping("/custom-plan-specification-standards/{id}")
    @ApiOperation(value = "根据id查询定制计划规格基础数据", notes = "根据id查询定制计划规格基础数据", response = CustomPlanSpecificationStandard.class)
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "主键id", required = true, dataType = "Long")
    public ResponseEntity findCustomPlanSpecificationStandard(@PathVariable("id") Long id) {
        log.debug("根据id查询定制计划规格基础数据\t param:{}", id);

        ResponseEntity<CustomPlanSpecificationStandard> entity = customPlanSpecificationStandardFeign.findById(id);
        return FeginResponseTools.convertNoramlResponse(entity);
    }

    @ApiOperation(value = "查询定制计划规格基础数据分页列表", response = CustomPlanSpecificationStandard.class, responseContainer = "Set")
    @PostMapping("/custom-plan-specification-standards/pages")
    public ResponseEntity search(@RequestBody CustomPlanSpecificationStandardParam customPlanSpecificationStandardParam) {
        log.debug("查询定制计划规格基础数据分页列表\t param:{}", customPlanSpecificationStandardParam);

        ResponseEntity<Pages<CustomPlanSpecificationStandard>> entity = customPlanSpecificationStandardFeign.search(customPlanSpecificationStandardParam);
        return FeginResponseTools.convertNoramlResponse(entity);
    }

    @ApiOperation(value = "查询定制计划规格基础数据列表", response = CustomPlanSpecificationStandard.class, responseContainer = "List")
    @GetMapping("/custom-plan-specification-standards")
    public ResponseEntity findList() {
        log.debug("查询定制计划规格基础数据列表\t param:{}");

        ResponseEntity entity = customPlanSpecificationStandardFeign.findList();
        return FeginResponseTools.convertNoramlResponse(entity);
    }
}
