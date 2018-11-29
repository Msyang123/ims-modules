package com.lhiot.ims.datacenter.feign;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.datacenter.feign.entity.ProductCategory;
import com.lhiot.ims.datacenter.model.ProductCategoryParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2018/11/20 12:03
 **/
@Component
@FeignClient(value = "basic-data-service-v1-0-hufan")
public interface ProductCategoryFeign {

    /**
     * 添加商品分类
     */
    @PostMapping(value = "/product-categories/")
    ResponseEntity create(@RequestBody ProductCategory productCategory);

    /**
     * 修改商品分类
     */
    @PutMapping(value = "/product-categories/{id}")
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody ProductCategory productCategory);

    /**
     * 根据Id查找商品分类
     */
    @GetMapping(value = "/product-categories/{id}")
    ResponseEntity<ProductCategory> findById(@PathVariable("id") Long id);

    /**
     * 根据Ids删除商品分类，英文逗号分隔
     */
    @DeleteMapping(value = "/product-categories/{ids}")
    ResponseEntity batchDelete(@PathVariable("ids") String ids);

    /**
     * 查询商品分类分页信息
     * 必须指定类型，否则接受到的参数会变为string
     */
    @PostMapping(value = "/product-categories/pages")
    ResponseEntity<Pages<ProductCategory>> pages(@RequestBody ProductCategoryParam param);
}
