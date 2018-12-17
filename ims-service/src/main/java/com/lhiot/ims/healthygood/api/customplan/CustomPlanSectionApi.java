package com.lhiot.ims.healthygood.api.customplan;

import com.leon.microx.util.Maps;
import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tuple;
import com.leon.microx.web.swagger.ApiHideBodyProperty;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.datacenter.feign.entity.ProductCategory;
import com.lhiot.ims.healthygood.feign.customplan.CustomPlanSectionFeign;
import com.lhiot.ims.healthygood.feign.customplan.CustomPlanSectionRelationFeign;
import com.lhiot.ims.healthygood.feign.customplan.entity.CustomPlanSection;
import com.lhiot.ims.healthygood.feign.customplan.entity.CustomPlanSectionRelation;
import com.lhiot.ims.healthygood.feign.customplan.model.CustomPlanSectionParam;
import com.lhiot.ims.rbac.aspect.LogCollection;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hufan created in 2018/12/1 15:30
 **/
@Api(description = "定制板块接口")
@Slf4j
@RestController
public class CustomPlanSectionApi {
    private final CustomPlanSectionFeign customPlanSectionFeign;
    private final CustomPlanSectionRelationFeign customPlanSectionRelationFeign;

    public CustomPlanSectionApi(CustomPlanSectionFeign customPlanSectionFeign, CustomPlanSectionRelationFeign customPlanSectionRelationFeign) {
        this.customPlanSectionFeign = customPlanSectionFeign;
        this.customPlanSectionRelationFeign = customPlanSectionRelationFeign;
    }

    @LogCollection
    @ApiOperation("添加定制板块")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "customPlanSection", value = "定制计划板块", dataType = "CustomPlanSection", required = true)
    @PostMapping("/custom-plan-sections")
    public ResponseEntity create(@Valid @RequestBody CustomPlanSection customPlanSection) {
        log.debug("添加定制板块\t param:{}", customPlanSection);

        ResponseEntity entity = customPlanSectionFeign.create(customPlanSection);
        if (entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        String location = entity.getHeaders().getLocation().toString();
        Long id = Long.valueOf(location.substring(location.lastIndexOf('/') + 1));
        return id > 0
                ? ResponseEntity.created(URI.create("/custom-plan-sections/" + id)).body(Maps.of("id", id))
                : ResponseEntity.badRequest().body(entity.getBody());
    }


    @LogCollection
    @ApiOperation("修改定制板块")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "定制板块id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = ApiParamType.BODY, name = "customPlanSection", value = "定制板块", dataType = "CustomPlanSection", required = true)
    })
    @PutMapping("/custom-plan-sections/{id}")
    @ApiHideBodyProperty({"customPlanList", "relationSorts"})
    public ResponseEntity update(@PathVariable("id") Long id, @Valid @RequestBody CustomPlanSection customPlanSection) {
        log.debug("修改定制板块\t param:{}", customPlanSection);

        ResponseEntity entity = customPlanSectionFeign.update(id, customPlanSection);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok().build();
    }

    @ApiOperation(value = "根据id查找单个定制板块", response = CustomPlanSection.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "定制板块id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = ApiParamType.QUERY, name = "flag", value = "是否查询关联定制计划信息", dataType = "Boolean")
    })
    @GetMapping("/custom-plan-sections/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id, @RequestParam(value = "flag", required = false) boolean flag) {
        log.debug("根据id查找单个定制板块\t param:{}", id, flag);

        ResponseEntity entity = customPlanSectionFeign.findById(id, true);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok().body(entity.getBody());
    }

    @LogCollection
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

        ResponseEntity entity = customPlanSectionFeign.search(param);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }

    @ApiOperation(value = "查询去重的定制板块集合", response = String.class, responseContainer = "List")
    @GetMapping("/custom-plan-sections")
    public ResponseEntity list() {
        log.debug("查询去重的定制板块集合\t");

        CustomPlanSectionParam customPlanSectionParam = new CustomPlanSectionParam();
        ResponseEntity entity = customPlanSectionFeign.search(customPlanSectionParam);
        if (entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        Pages<CustomPlanSection> pages = (Pages<CustomPlanSection>) entity.getBody();
        List<String> sectionNameList = null;
        if (Objects.nonNull(pages)) {
            List<CustomPlanSection> customPlanSectionList = Optional.of(pages.getArray()).orElse(Collections.emptyList());
            sectionNameList = CollectionUtils.isEmpty(customPlanSectionList) ? null : customPlanSectionList.stream().map(CustomPlanSection::getSectionName).distinct().collect(Collectors.toList());
        }
        return ResponseEntity.ok(Tuple.of(sectionNameList));
    }

    @LogCollection
    @ApiOperation("添加定制版块与定制计划关系")
    @PostMapping("/custom-plan-sections/relation")
    @ApiHideBodyProperty("id")
    public ResponseEntity create(@RequestBody CustomPlanSectionRelation customPlanSectionRelation) {
        log.debug("批量修改定制版块与定制计划关系\t param:{}", customPlanSectionRelation);

        ResponseEntity entity = customPlanSectionRelationFeign.create(customPlanSectionRelation);
        String location = entity.getHeaders().getLocation().toString();
        Long relationId = Long.valueOf(location.substring(location.lastIndexOf('/') + 1));

        return entity.getStatusCode().isError()
                ? ResponseEntity.badRequest().body("添加商品与版块关系记录失败！")
                : ResponseEntity.created(URI.create("/custom-plan-section-relations/" + relationId)).body(Maps.of("id", relationId));
    }

    @ApiOperation("批量删除定制版块与定制计划架关系")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.QUERY, name = "sectionId", value = "定制版块Id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = ApiParamType.QUERY, name = "planIds", value = "多个定制计划Id以英文逗号分隔,为空则删除此版块所有上架关系", dataType = "String")
    })
    @DeleteMapping("/custom-plan-sections/relation/batches")
    public ResponseEntity deleteBatch(@RequestParam("sectionId") Long sectionId, @RequestParam(value = "planIds", required = false) String planIds) {
        log.debug("批量删除版块与商品上架关系\t sectionId:{},shelfIds:{} ", sectionId, planIds);

        ResponseEntity entity = customPlanSectionRelationFeign.deleteBatch(sectionId, planIds);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.noContent().build();
    }
}