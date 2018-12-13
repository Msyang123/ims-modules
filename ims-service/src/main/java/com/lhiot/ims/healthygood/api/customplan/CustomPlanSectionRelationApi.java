package com.lhiot.ims.healthygood.api.customplan;

import com.leon.microx.util.Maps;
import com.leon.microx.web.swagger.ApiHideBodyProperty;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.healthygood.feign.customplan.CustomPlanSectionRelationFeign;
import com.lhiot.ims.healthygood.feign.customplan.entity.CustomPlanSectionRelation;
import com.lhiot.ims.rbac.aspect.LogCollection;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * @author hufan created in 2018/12/1 15:39
 **/
@Api(description = "定制版块与定制计划关系接口")
@Slf4j
@RestController
public class CustomPlanSectionRelationApi {
    private final CustomPlanSectionRelationFeign customPlanSectionRelationFeign;

    @Autowired
    public CustomPlanSectionRelationApi(CustomPlanSectionRelationFeign customPlanSectionRelationFeign) {
        this.customPlanSectionRelationFeign = customPlanSectionRelationFeign;
    }

    @Deprecated
    @LogCollection
    @ApiOperation("批量修改定制版块与定制计划关系")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.QUERY, name = "sectionId", value = "定制版块Id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = ApiParamType.QUERY, name = "planIds", value = "多个定制计划Id以英文逗号分隔", dataType = "String"),
            @ApiImplicitParam(paramType = ApiParamType.QUERY, name = "sorts", value = "多个排序以英文逗号分隔", dataType = "String")
    })
    @PutMapping("/custom-plan-section-relations/batches")
    public ResponseEntity updateBatch(@RequestParam("sectionId") Long sectionId, @RequestParam(value = "planIds", required = false) String planIds, @RequestParam(value = "sorts", required = false) String sorts) {
        log.debug("批量修改定制版块与定制计划关系\t param:{}", sectionId, planIds, sorts);

        ResponseEntity entity = customPlanSectionRelationFeign.updateBatch(sectionId, planIds, sorts);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok().build();
    }

    @LogCollection
    @ApiOperation("添加定制版块与定制计划关系(后台)")
    @PostMapping("/custom-plan-section-relations")
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

    @LogCollection
    @ApiOperation("根据关联Id删除定制版块与定制计划架关系(后台)")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "关系Id", dataType = "Long", required = true)
    @DeleteMapping("/custom-plan-section-relations/{id}")
    public ResponseEntity delete(@PathVariable("id") Long relationId) {
        log.debug("根据关联Id删除定制版块与定制计划架关系\t relationId: {}", relationId);

        ResponseEntity entity = customPlanSectionRelationFeign.delete(relationId);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body("删除定制版块与定制计划架关系失败！") : ResponseEntity.noContent().build();
    }
}