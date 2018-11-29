package com.lhiot.ims.datacenter.feign;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.datacenter.feign.entity.Product;
import com.lhiot.ims.datacenter.model.ProductInfo;
import com.lhiot.ims.datacenter.model.ProductParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2018/11/21 16:07
 **/
@Component
@FeignClient(value = "basic-data-service-v1-0-hufan")
public interface ProductFegin {
    /**
     * 添加商品
     */
    @PostMapping(value = "/products")
    ResponseEntity create(@RequestBody Product param);

    /**
     * 修改商品
     */
    @PutMapping(value = "/products/{id}")
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody Product param);

    /**
     * 根据Id查找商品
     */
    @GetMapping(value = "/products/{id}")
    ResponseEntity<Product> findById(@PathVariable("id") Long id);

    /**
     * 根据商品Ids删除商品
     */
    @DeleteMapping(value = "/products/{ids}")
    ResponseEntity batchDelete(@PathVariable("ids") String ids);


    /**
     * 根据条件分页查询商品信息列表
     */
    @PostMapping(value = "/products/pages")
    ResponseEntity<Pages<Product>> pages(@RequestBody ProductParam param);

}
