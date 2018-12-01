package com.lhiot.ims.healthygood.feign.customplan;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author hufan created in 2018/12/1 15:12
 **/
@Component
@FeignClient(value = "healthy-good-service-v2-0-hufan")
public interface CustomPlanSectionRelationFeign {

    /**
     * 批量修改定制版块与定制计划关系
     * @param sectionId
     * @param planIds
     * @param sorts
     * @return
     */
    @PutMapping("/custom-plan-section-relations/batches")
    ResponseEntity updateBatch(@RequestParam("sectionId") Long sectionId, @RequestParam(value = "planIds",required = false) String planIds, @RequestParam(value = "sorts", required = false) String sorts);
}

