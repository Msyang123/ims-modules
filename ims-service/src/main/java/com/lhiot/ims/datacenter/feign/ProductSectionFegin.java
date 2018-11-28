package com.lhiot.ims.datacenter.feign;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.datacenter.feign.entity.ProductSection;
import com.lhiot.ims.datacenter.model.ProductSectionParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2018/11/21 16:07
 **/
@Component
@FeignClient(value = "basic-data-service-v1-0-hufan")
public interface ProductSectionFegin {
    /**
     * 添加商品版块
     */
    @PostMapping(value = "/product-sections")
    ResponseEntity create(@RequestBody ProductSection param);

    /**
     * 修改商品版块
     */
    @PutMapping(value = "/product-sections/{id}")
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody ProductSection param);

    /**
     * 根据Id查找商品版块
     */
    @GetMapping(value = "/product-sections/{id}")
    ResponseEntity<ProductSection> findById(@PathVariable("id") Long id, @RequestParam(value = "includeShelves", required = false) boolean includeShelves,
                                            @RequestParam(value = "includeShelvesQty", required = false) Long includeShelvesQty);

    /**
     * 根据Ids删除商品版块
     */
    @DeleteMapping(value = "/product-sections/{ids}")
    ResponseEntity batchDelete(@PathVariable("ids") String ids);

    /**
     * 根据条件分页查询商品版块信息列表
     */
    @PostMapping(value = "/product-sections/pages")
    ResponseEntity<Pages<ProductSection>> pages(@RequestBody ProductSectionParam param);

}
