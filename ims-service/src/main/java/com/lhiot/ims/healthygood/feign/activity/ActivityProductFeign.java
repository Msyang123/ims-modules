package com.lhiot.ims.healthygood.feign.activity;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.healthygood.feign.activity.entity.ActivityProduct;
import com.lhiot.ims.healthygood.feign.activity.model.ActivityProductParam;
import com.lhiot.ims.healthygood.feign.activity.model.ActivityProductResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2018/12/4 15:45
 **/
@FeignClient(value = "healthy-good-service-v2-0-hufan")
@Component
public interface ActivityProductFeign {

    /**
     * 添加新品尝鲜活动商品
     *
     * @param activityProduct 要添加的新品尝鲜活动商品
     * @return 添加操作的结果
     */
    @PostMapping("/activity-products")
    ResponseEntity create(@RequestBody ActivityProduct activityProduct);

    /**
     * 修改新品尝鲜活动商品
     *
     * @param id              新品尝鲜活动商品id
     * @param activityProduct 要修改的新品尝鲜活动商品
     * @return 修改操作的结果
     */
    @PutMapping("/activity-products/{id}")
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody ActivityProduct activityProduct);

    /**
     * 根据ids删除新品尝鲜活动商品
     *
     * @param ids 新品尝鲜活动商品ids,用英文逗号分隔
     * @return 删除操作结果
     */
    @DeleteMapping("/activity-products/{ids}")
    ResponseEntity batchDelete(@PathVariable("ids") String ids);


    /**
     * 根据条件分页查询新品尝鲜活动商品信息列表
     *
     * @param param 分页查询条件
     * @return 分页查询结果
     */
    @PostMapping("/activity-products/pages")
    ResponseEntity<Pages<ActivityProductResult>> search(@RequestBody ActivityProductParam param);
}
