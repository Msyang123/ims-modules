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
@FeignClient(value = "healthy-good-service-v2-0-hufan")
public interface CustomPlanFeign {


    /**
     * 添加定制计划
     *
     * @param customPlanDetailResult 要添加的定制计划
     * @return 添加操作的结果
     */
    @PostMapping("/custom-plans")
    ResponseEntity create(@RequestBody CustomPlanDetailResult customPlanDetailResult);

    /**
     * 修改定制计划
     *
     * @param id                     定制计划id
     * @param customPlanDetailResult 要修改的定制计划
     * @return 修改操作的结果
     */
    @PutMapping("/custom-plans/{id}")
    ResponseEntity update(@PathVariable("id") Long id, @Valid @RequestBody CustomPlanDetailResult customPlanDetailResult);

    /**
     * 修改定制计划商品
     *
     * @param id                     定制计划商品id
     * @param customPlanDetailResult 要修改的定制计划商品
     * @return 修改操作的结果
     */
    @PutMapping("/custom-plan-product/{id}")
    ResponseEntity updateProduct(@PathVariable("id") Long id, @Valid @RequestBody CustomPlanDetailResult customPlanDetailResult);


    /**
     * 根据ids删除定制计划
     *
     * @param ids 定制计划ids,用英文逗号分隔
     * @return 删除操作结果
     */
    @DeleteMapping("/custom-plans/{ids}")
    ResponseEntity batchDelete(@PathVariable("ids") String ids);

    /**
     * 根据条件分页查询定制计划信息列表
     *
     * @param customPlanParam 分页查询条件
     * @return 分页查询结果
     */
    @PostMapping("/custom-plans/pages")
    ResponseEntity<Pages<CustomPlanResult>> search(@RequestBody CustomPlanParam customPlanParam);

    /**
     * 查询定制计划详细信息
     *
     * @param id 定制计划id
     * @return 查询结果
     */
    @GetMapping("/custom-plans/{id}")
    ResponseEntity<CustomPlanDetailResult> findById(@PathVariable("id") Long id);
}
