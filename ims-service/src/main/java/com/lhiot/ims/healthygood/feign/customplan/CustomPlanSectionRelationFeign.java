package com.lhiot.ims.healthygood.feign.customplan;

import com.lhiot.ims.healthygood.feign.customplan.entity.CustomPlanSectionRelation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2018/12/1 15:12
 **/
@Component
@FeignClient(value = "healthy-good-service-v2-0")
public interface CustomPlanSectionRelationFeign {

    /**
     * 批量修改定制版块与定制计划关系
     *
     * @param sectionId
     * @param planIds
     * @param sorts
     * @return
     */
    @PutMapping("/custom-plan-section-relations/batches")
    ResponseEntity updateBatch(@RequestParam("sectionId") Long sectionId, @RequestParam(value = "planIds", required = false) String planIds, @RequestParam(value = "sorts", required = false) String sorts);


    /**
     * 添加定制版块与定制计划关系
     *
     * @param customPlanSectionRelation
     * @return
     */
    @PostMapping("/custom-plan-section-relations")
    ResponseEntity create(@RequestBody CustomPlanSectionRelation customPlanSectionRelation);


    /**
     * 根据关联Id删除定制版块与定制计划架关系
     *
     * @param relationId
     * @return
     */
    @DeleteMapping("/custom-plan-section-relations/{id}")
    ResponseEntity delete(@PathVariable("id") Long relationId);
}

