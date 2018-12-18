package com.lhiot.ims.datacenter.feign;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.datacenter.feign.entity.Product;
import com.lhiot.ims.datacenter.feign.model.ProductParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2018/11/21 16:07
 **/
@Component
@FeignClient(value = "basic-data-service-v1-0")
public interface ProductFeign {
    /**
     * 添加商品
     *
     * @param param 要添加的商品
     * @return 添加操作的结果
     */
    @PostMapping(value = "/products")
    ResponseEntity create(@RequestBody Product param);

    /**
     * 修改商品
     *
     * @param id    商品id
     * @param param 要修改的商品
     * @return 修改操作的结果
     */
    @PutMapping(value = "/products/{id}")
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody Product param);

    /**
     * 根据Id查找商品
     *
     * @param id 商品id
     * @return 查询结果
     */
    @GetMapping(value = "/products/{id}")
    ResponseEntity<Product> findById(@PathVariable("id") Long id);

    /**
     * 根据商品Ids删除商品
     *
     * @param ids 商品ids,英文逗号分隔
     * @return 删除操作结果
     */
    @DeleteMapping(value = "/products/{ids}")
    ResponseEntity batchDelete(@PathVariable("ids") String ids);

    /**
     * 根据条件分页查询商品信息列表
     *
     * @param param 分页查询条件
     * @return 分页查询结果
     */
    @PostMapping(value = "/products/pages")
    ResponseEntity<Pages<Product>> pages(@RequestBody ProductParam param);

}
