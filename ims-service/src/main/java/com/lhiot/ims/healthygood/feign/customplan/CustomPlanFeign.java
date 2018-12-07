package com.lhiot.ims.healthygood.feign.customplan;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.healthygood.feign.customplan.model.CustomPlanDetailResult;
import com.lhiot.ims.healthygood.feign.customplan.model.CustomPlanParam;
import com.lhiot.ims.healthygood.feign.customplan.model.CustomPlanResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author hufan created in 2018/12/1 15:13
 **/
@Component
@FeignClient(value = "healthy-good-service-v2-0")
public interface CustomPlanFeign {


    /**
     * 添加定制计划
     *
     * @param customPlanDetailResult
     * @return
     */
    @PostMapping("/custom-plans")
    ResponseEntity create(@RequestBody CustomPlanDetailResult customPlanDetailResult);

    /**
     * 修改定制计划
     *
     * @param id
     * @param customPlanDetailResult
     * @return
     */
    @PutMapping("/custom-plans/{id}")
    ResponseEntity update(@PathVariable("id") Long id,  @Valid @RequestBody CustomPlanDetailResult customPlanDetailResult);

    /**
     * 修改定制计划商品
     *
     * @param id
     * @param customPlanDetailResult
     * @return
     */
    @PutMapping("/custom-plan-product/{id}")
    ResponseEntity updateProduct(@PathVariable("id") Long id, @Valid @RequestBody CustomPlanDetailResult customPlanDetailResult);


    /**
     * 根据ids删除定制计划
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/custom-plans/{ids}")
    ResponseEntity batchDelete(@PathVariable("ids") String ids);

    /**
     * 根据条件分页查询定制计划信息列表
     *
     * @param customPlanParam
     * @return
     */
    @PostMapping("/custom-plans/pages")
    ResponseEntity<Pages<CustomPlanResult>> search(@RequestBody CustomPlanParam customPlanParam);

    /**
     * 查询定制计划详细信息
     * @param id
     * @return
     */
    @GetMapping("/custom-plans/{id}")
    ResponseEntity<CustomPlanDetailResult> findById(@PathVariable("id") Long id);
}
