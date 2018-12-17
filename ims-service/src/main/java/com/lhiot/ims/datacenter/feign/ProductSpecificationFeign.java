package com.lhiot.ims.datacenter.feign;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.datacenter.feign.entity.ProductSpecification;
import com.lhiot.ims.datacenter.feign.model.ProductSpecificationParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2018/11/21 16:07
 **/
@Component
@FeignClient(value = "basic-data-service-v1-0")
public interface ProductSpecificationFeign {
    /**
     * 添加商品规格
     *
     */
    @PostMapping(value = "/product-specifications")
    ResponseEntity create(@RequestBody ProductSpecification param);

    /**
     * 修改商品规格
     */
    @PutMapping(value = "/product-specifications/{id}")
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody ProductSpecification param);

    /**
     * 根据Id查找商品规格
     */
    @GetMapping(value = "/product-specifications/{id}")
    ResponseEntity<ProductSpecification> findById(@PathVariable("id") Long id);

    /**
     * 根据Id删除商品规格
     */
    @DeleteMapping(value = "/product-specifications/{ids}")
    ResponseEntity batchDelete(@PathVariable("ids") String ids);


    /**
     * 根据条件分页查询商品规格信息列表
     */
    @PostMapping(value = "/product-specifications/pages")
    ResponseEntity<Pages<ProductSpecification>> pages(@RequestBody ProductSpecificationParam param);

}
