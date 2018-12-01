package com.lhiot.ims.healthygood.api.customplan;

import com.leon.microx.util.Maps;
import com.leon.microx.web.result.Pages;
import com.leon.microx.web.swagger.ApiHideBodyProperty;
import com.leon.microx.web.swagger.ApiParamType;
import com.leon.microx.web.swagger.reader.ApiReaderContext;
import com.lhiot.ims.datacenter.feign.entity.ProductSection;
import com.lhiot.ims.datacenter.feign.model.ProductSectionParam;
import com.lhiot.ims.healthygood.feign.customplan.CustomPlanSectionFeign;
import com.lhiot.ims.healthygood.feign.customplan.entity.CustomPlanSection;
import com.lhiot.ims.healthygood.feign.customplan.model.CustomPlanSectionParam;
import com.lhiot.ims.healthygood.feign.customplan.model.CustomPlanSectionResultAdmin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hufan created in 2018/12/1 15:30
 **/
@Api(description = "定制板块接口")
@Slf4j
@RestController
public class CustomPlanSectionApi {
    private final CustomPlanSectionFeign customPlanSectionFeign;

    public CustomPlanSectionApi(CustomPlanSectionFeign customPlanSectionFeign) {
        this.customPlanSectionFeign = customPlanSectionFeign;
    }


    @ApiOperation("添加定制板块")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "customPlanSectionResultAdmin", value = "定制计划板块", dataType = "CustomPlanSectionResultAdmin", required = true)
    @PostMapping("/custom-plan-sections")
    public ResponseEntity create(@Valid @RequestBody CustomPlanSectionResultAdmin customPlanSectionResultAdmin) {
        log.debug("添加定制板块\t param:{}", customPlanSectionResultAdmin);

        ResponseEntity entity = customPlanSectionFeign.create(customPlanSectionResultAdmin);
        if (entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        String location = entity.getHeaders().getLocation().toString();
        Long id = Long.valueOf(location.substring(location.lastIndexOf('/') + 1));
        return id > 0 ?
                ResponseEntity.created(URI.create("/custom-plan-sections/" + id)).body(Maps.of("id", id)) :
                ResponseEntity.badRequest().body(entity.getBody());
    }


    @ApiOperation("修改定制板块")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "定制板块id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = ApiParamType.BODY, name = "customPlanSectionResultAdmin", value = "定制板块", dataType = "CustomPlanSectionResultAdmin", required = true)
    })
    @PutMapping("/custom-plan-sections/{id}")
    @ApiHideBodyProperty({"customPlanList","relationSorts"})
    public ResponseEntity update(@PathVariable("id") Long id,@Valid @RequestBody CustomPlanSectionResultAdmin customPlanSectionResultAdmin) {
        log.debug("修改定制板块\t param:{}", customPlanSectionResultAdmin);

        ResponseEntity entity = customPlanSectionFeign.update(id, customPlanSectionResultAdmin);
        return !entity.getStatusCode().isError() ? ResponseEntity.ok().build() : ResponseEntity.badRequest().body(entity.getBody());
    }

    @ApiOperation(value = "根据id查找单个定制板块", response = CustomPlanSectionResultAdmin.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "定制板块id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = ApiParamType.QUERY, name = "flag", value = "是否查询关联定制计划信息", dataType = "Boolean")
    })
    @GetMapping("/custom-plan-sections/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id, @RequestParam(value = "flag", required = false) boolean flag) {
        log.debug("根据id查找单个定制板块\t param:{}", id, flag);

        ResponseEntity<CustomPlanSectionResultAdmin> entity = customPlanSectionFeign.findById(id, true);
        return !entity.getStatusCode().isError() ? ResponseEntity.ok().body(entity.getBody()) : ResponseEntity.badRequest().body(entity.getBody());
    }

    @ApiOperation("根据ids删除定制板块")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "ids", value = "多个定制板块id以英文逗号分隔", dataType = "String", required = true)
    @DeleteMapping("/custom-plan-sections/{ids}")
    public ResponseEntity batchDelete(@PathVariable("ids") String ids) {
        log.debug("批量删除定制板块\t param:{}", ids);

        ResponseEntity entity = customPlanSectionFeign.batchDelete(ids);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "根据条件分页查询定制板块信息列表", response = CustomPlanSection.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "查询条件", dataType = "CustomPlanSectionParam")
    @PostMapping("/custom-plan-sections/pages")
    public ResponseEntity search(@RequestBody CustomPlanSectionParam param) {
        log.debug("根据条件分页查询定制板块信息列表\t param:{}", param);

        ResponseEntity<Pages<CustomPlanSection>> entity = customPlanSectionFeign.search(param);
        return !entity.getStatusCode().isError() ? ResponseEntity.ok(entity.getBody()) : ResponseEntity.badRequest().body(entity.getBody());
    }

    @ApiOperation(value = "查询去重的定制板块集合", response = String.class, responseContainer = "List")
    @GetMapping("/custom-plan-sections")
    public ResponseEntity list() {
        log.debug("查询去重的定制板块集合\t");

        CustomPlanSectionParam customPlanSectionParam = new CustomPlanSectionParam();
        ResponseEntity<Pages<CustomPlanSection>> entity = customPlanSectionFeign.search(customPlanSectionParam);
        if (entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        List<CustomPlanSection> customPlanSectionList = entity.getBody().getArray();
        List<String> sectionNameList = customPlanSectionList.stream().map(CustomPlanSection::getSectionName).distinct().collect(Collectors.toList());
        return ResponseEntity.ok(sectionNameList);
    }
}