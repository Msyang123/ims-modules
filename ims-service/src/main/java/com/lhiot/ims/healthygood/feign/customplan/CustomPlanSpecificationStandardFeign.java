package com.lhiot.ims.healthygood.feign.customplan;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.session.Sessions;
import com.lhiot.ims.healthygood.feign.customplan.entity.CustomPlanSpecificationStandard;
import com.lhiot.ims.healthygood.feign.customplan.model.CustomPlanSpecificationStandardParam;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hufan created in 2018/12/8 10:19
 **/
@Component
@FeignClient(value = "healthy-good-service-v2-0-hufan")
public interface CustomPlanSpecificationStandardFeign {
    /**
     * 添加定制计划规格基础数据
     *
     * @param customPlanSpecificationStandard
     * @return
     */
    @PostMapping("/custom-plan-specification-standards")
    ResponseEntity create(@RequestBody CustomPlanSpecificationStandard customPlanSpecificationStandard);

    /**
     * 根据id更新定制计划规格基础数据
     *
     * @param id
     * @param customPlanSpecificationStandard
     * @return
     */
    @PutMapping("/custom-plan-specification-standards/{id}")
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody CustomPlanSpecificationStandard customPlanSpecificationStandard);

    /**
     * 根据ids删除定制计划规格基础数据
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/custom-plan-specification-standards/{ids}")
    ResponseEntity deleteByIds(@PathVariable("ids") String ids);

    /**
     * 根据id查询定制计划规格基础数据
     *
     * @param id
     * @return
     */
    @Sessions.Uncheck
    @GetMapping("/custom-plan-specification-standards/{id}")
    ResponseEntity<CustomPlanSpecificationStandard> findById(@PathVariable("id") Long id);

    /**
     * 查询定制计划规格基础数据分页列表
     *
     * @param customPlanSpecificationStandardParam
     * @return
     */
    @PostMapping("/custom-plan-specification-standards/pages")
    ResponseEntity<Pages<CustomPlanSpecificationStandard>> search(@RequestBody CustomPlanSpecificationStandardParam customPlanSpecificationStandardParam);

    /**
     * 查询定制计划规格基础数据列表
     *
     * @return
     */
    @GetMapping("/custom-plan-specification-standards")
    ResponseEntity<List<CustomPlanSpecificationStandard>> findList();
}
