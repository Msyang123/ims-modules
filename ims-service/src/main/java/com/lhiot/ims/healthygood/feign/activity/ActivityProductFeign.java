package com.lhiot.ims.healthygood.feign.activity;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.healthygood.feign.activity.entity.ActivityProduct;
import com.lhiot.ims.healthygood.feign.activity.model.ActivityProductParam;
import com.lhiot.ims.healthygood.feign.activity.model.ActivityProductResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author hufan created in 2018/12/4 15:45
 **/
@FeignClient(value = "healthy-good-service-v2-0")
@Component
public interface ActivityProductFeign {

    /**
     * 添加新品尝鲜活动商品
     *
     * @param activityProduct
     * @return
     */
    @PostMapping("/activity-products")
    ResponseEntity<ActivityProduct> create(@Valid @RequestBody ActivityProduct activityProduct);

    /**
     * 修改新品尝鲜活动商品
     *
     * @param id
     * @param activityProduct
     * @return
     */
    @PutMapping("/activity-products/{id}")
    ResponseEntity update(@PathVariable("id") Long id, @Valid @RequestBody ActivityProduct activityProduct);

    /**
     * 根据ids删除新品尝鲜活动商品
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/activity-products/{ids}")
    ResponseEntity batchDelete(@PathVariable("ids") String ids);


    /**
     * 根据条件分页查询新品尝鲜活动商品信息列表
     *
     * @param param
     * @return
     */
    @PostMapping("/activity-products/pages")
    ResponseEntity<Pages<ActivityProductResult>> search(@RequestBody ActivityProductParam param);
}
