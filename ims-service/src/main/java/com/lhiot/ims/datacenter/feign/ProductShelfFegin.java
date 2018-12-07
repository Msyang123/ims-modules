package com.lhiot.ims.datacenter.feign;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.datacenter.feign.entity.ProductShelf;
import com.lhiot.ims.datacenter.feign.model.ProductShelfParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2018/11/21 16:07
 **/
@Component
@FeignClient(value = "basic-data-service-v1-0")
public interface ProductShelfFegin {
    /**
     * 新增商品上架
     */
    @PostMapping(value = "/product-shelves")
    ResponseEntity create(@RequestBody ProductShelf param);

    /**
     * 修改商品上架
     */
    @PutMapping(value = "/product-shelves/{id}")
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody ProductShelf param);

    /**
     * 根据Id查找商品上架
     */
    @GetMapping(value = "/product-shelves/{id}")
    ResponseEntity<ProductShelf> findById(@PathVariable("id") Long id,@RequestParam(value = "includeProduct", required = false) boolean includeProduct);

    /**
     * 根据Ids删除商品上架
     */
    @DeleteMapping(value = "/product-shelves/{ids}")
    ResponseEntity batchDelete(@PathVariable("ids") String ids);


    /**
     * 根据条件分页查询商品上架信息列表
     */
    @PostMapping(value = "/product-shelves/pages")
    ResponseEntity<Pages<ProductShelf>> pages(@RequestBody ProductShelfParam param);

}
