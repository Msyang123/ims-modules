package com.lhiot.ims.healthygood.feign.customplan;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.healthygood.feign.customplan.entity.CustomPlanSection;
import com.lhiot.ims.healthygood.feign.customplan.model.CustomPlanSectionParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2018/12/1 15:06
 **/
@Component
@FeignClient(value = "healthy-good-service-v2-0-hufan")
public interface CustomPlanSectionFeign {
    /**
     * 添加定制板块
     *
     * @param customPlanSection 要添加的定制板块
     * @return 添加操作的结果
     */
    @PostMapping("/custom-plan-sections")
    ResponseEntity create(@RequestBody CustomPlanSection customPlanSection);


    /**
     * 修改定制板块
     *
     * @param id                定制板块id
     * @param customPlanSection 要修改的定制板块
     * @return 修改操作的结果
     */
    @PutMapping("/custom-plan-sections/{id}")
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody CustomPlanSection customPlanSection);

    /**
     * 根据id查找单个定制板块
     *
     * @param id   定制板块id
     * @param flag 是否查询定制板块商品
     * @return 查询结果
     */
    @GetMapping("/custom-plan-sections/{id}")
    ResponseEntity<CustomPlanSection> findById(@PathVariable("id") Long id, @RequestParam(value = "flag", required = false) boolean flag);
    // FIXME

    /**
     * 根据ids删除定制板块
     *
     * @param ids 定制板块ids，用英文逗号分隔
     * @return 删除操作结果
     */
    @DeleteMapping("/custom-plan-sections/{ids}")
    ResponseEntity batchDelete(@PathVariable("ids") String ids);

    /**
     * 根据条件分页查询定制板块信息列表
     *
     * @param customPlanSectionParam 分页查询条件
     * @return 分页查询结果
     */
    @PostMapping("/custom-plan-sections/pages")
    ResponseEntity<Pages<CustomPlanSection>> search(@RequestBody CustomPlanSectionParam customPlanSectionParam);
}
