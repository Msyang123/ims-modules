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
     * @param sectionId 定制版块id
     * @param planIds   定制计划ids，用英文逗号分隔
     * @param sorts     排序ids，用英文逗号分隔
     * @return 批量修改操作的结果
     */
    @PutMapping("/custom-plan-section-relations/batches")
    ResponseEntity updateBatch(@RequestParam("sectionId") Long sectionId, @RequestParam(value = "planIds", required = false) String planIds, @RequestParam(value = "sorts", required = false) String sorts);


    /**
     * 添加定制版块与定制计划关系
     *
     * @param customPlanSectionRelation 要添加的定制版块与定制计划
     * @return 添加操作的结果
     */
    @PostMapping("/custom-plan-section-relations")
    ResponseEntity create(@RequestBody CustomPlanSectionRelation customPlanSectionRelation);

    /**
     * 批量添加定制版块与定制计划关系
     *
     * @param sectionId 定制版块id
     * @param planIds   定制计划ids，用英文逗号分隔
     * @param sorts     排序ids，用英文逗号分隔
     * @return 批量添加操作的结果
     */
    @PostMapping("/product-section-relations/batches")
    ResponseEntity createBatch(@RequestParam("sectionId") Long sectionId, @RequestParam("planIds") String planIds, @RequestParam("sorts") String sorts);

    /**
     * 根据关联Id删除定制版块与定制计划架关系
     *
     * @param relationId 定制版块与定制计划关联id
     * @return 删除操作结果
     */
    @DeleteMapping("/custom-plan-section-relations/{id}")
    ResponseEntity delete(@PathVariable("id") Long relationId);

    /**
     * 批量删除定制版块与定制计划关系
     *
     * @param sectionId 定制版块id
     * @param planIds   定制计划ids，用英文逗号分隔
     * @return 批量删除操作结果
     */
    @DeleteMapping(value = "/custom-plan-section-relations/batches")
    ResponseEntity deleteBatch(@RequestParam("sectionId") Long sectionId, @RequestParam(value = "planIds", required = false) String planIds);


    /**
     * 修改定制板块关联
     * @param id 定制板块关联id
     * @param customPlanSectionRelation 定制板块关联
     * @return 修改定制板块关联结果
     */
    @PutMapping("/custom-plan-section-relations/{id}")
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody CustomPlanSectionRelation customPlanSectionRelation);
}

