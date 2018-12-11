package com.lhiot.ims.healthygood.api.customplan;

import com.leon.microx.util.Maps;
import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tips;
import com.leon.microx.web.result.Tuple;
import com.leon.microx.web.session.Sessions;
import com.leon.microx.web.swagger.ApiHideBodyProperty;
import com.lhiot.ims.healthygood.feign.customplan.CustomPlanSpecificationStandardFeign;
import com.lhiot.ims.healthygood.feign.customplan.entity.CustomPlanSpecificationStandard;
import com.lhiot.ims.healthygood.feign.customplan.model.CustomPlanSpecificationStandardParam;
import com.lhiot.ims.rbac.aspect.LogCollection;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* Description:定制计划规格基础数据接口类
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
        log.debug("添加定制计划规格基础数据\t param:{}",customPlanSpecificationStandard);

        ResponseEntity entity = customPlanSpecificationStandardFeign.create(customPlanSpecificationStandard);
        if (entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(Tips.warn(entity.getBody().toString()));
        }
        String location = entity.getHeaders().getLocation().toString();
        Long id = Long.valueOf(location.substring(location.lastIndexOf('/') + 1));
        return ResponseEntity.created(entity.getHeaders().getLocation()).body(Maps.of("id", id));
}

    @LogCollection
    @PutMapping("/custom-plan-specification-standards/{id}")
    @ApiOperation(value = "根据id更新定制计划规格基础数据")
    @ApiHideBodyProperty({"id"})
    public ResponseEntity update(@PathVariable("id") Long id,@RequestBody CustomPlanSpecificationStandard customPlanSpecificationStandard) {
        log.debug("根据id更新定制计划规格基础数据\t id:{} param:{}",id,customPlanSpecificationStandard);

        ResponseEntity entity = customPlanSpecificationStandardFeign.update(id, customPlanSpecificationStandard);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(Tips.warn(entity.getBody().toString())) : ResponseEntity.ok(entity.getBody());
    }

    @LogCollection
    @Sessions.Uncheck
    @DeleteMapping("/custom-plan-specification-standards/{ids}")
    @ApiOperation(value = "根据ids删除定制计划规格基础数据")
    @ApiImplicitParam(paramType = "path", name = "ids", value = "要删除定制计划规格基础数据的ids,逗号分割", required = true, dataType = "String")
    public ResponseEntity deleteByIds(@PathVariable("ids") String ids) {
        log.debug("根据ids删除定制计划规格基础数据\t param:{}",ids);

        ResponseEntity entity = customPlanSpecificationStandardFeign.deleteByIds(ids);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.noContent().build();
    }

    @GetMapping("/custom-plan-specification-standards/{id}")
    @ApiOperation(value = "根据id查询定制计划规格基础数据", notes = "根据id查询定制计划规格基础数据", response = CustomPlanSpecificationStandard.class)
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键id", required = true, dataType = "Long")
    public ResponseEntity findCustomPlanSpecificationStandard(@PathVariable("id") Long id) {
        log.debug("根据id查询定制计划规格基础数据\t param:{}",id);

        ResponseEntity<CustomPlanSpecificationStandard> entity = customPlanSpecificationStandardFeign.findById(id);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(Tips.warn(entity.getBody().toString())) : ResponseEntity.ok(entity.getBody());
    }

    @ApiOperation(value = "查询定制计划规格基础数据分页列表",response = CustomPlanSpecificationStandard.class, responseContainer = "Set")
    @PostMapping("/custom-plan-specification-standards/pages")
    public ResponseEntity search(@RequestBody CustomPlanSpecificationStandardParam customPlanSpecificationStandardParam){
        log.debug("查询定制计划规格基础数据分页列表\t param:{}",customPlanSpecificationStandardParam);

        ResponseEntity<Pages<CustomPlanSpecificationStandard>> entity = customPlanSpecificationStandardFeign.search(customPlanSpecificationStandardParam);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(Tips.warn(entity.getBody().toString())) : ResponseEntity.ok(entity.getBody());
    }

    @ApiOperation(value = "查询定制计划规格基础数据列表",response = CustomPlanSpecificationStandard.class, responseContainer = "List")
    @GetMapping("/custom-plan-specification-standards")
    public ResponseEntity findList(){
        log.debug("查询定制计划规格基础数据列表\t param:{}");

        ResponseEntity<List<CustomPlanSpecificationStandard>> entity = customPlanSpecificationStandardFeign.findList();
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(Tips.warn(entity.getBody().toString())) : ResponseEntity.ok(Tuple.of(entity.getBody()));
    }
}
