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
     * @param param 要添加的商品规格
     * @return 添加操作的结果
     */
    @PostMapping(value = "/product-specifications")
    ResponseEntity create(@RequestBody ProductSpecification param);

    /**
     * 修改商品规格
     *
     * @param id    商品规格id
     * @param param 要修改的商品规格
     * @return 修改操作的结果
     */
    @PutMapping(value = "/product-specifications/{id}")
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody ProductSpecification param);

    /**
     * 根据Id查找商品规格
     *
     * @param id 商品规格id
     * @return 查询结果
     */
    @GetMapping(value = "/product-specifications/{id}")
    ResponseEntity<ProductSpecification> findById(@PathVariable("id") Long id);

    /**
     * 根据Ids批量删除商品规格
     *
     * @param ids 商品规格ids，英文逗号分隔
     * @return 删除操作结果
     */
    @DeleteMapping(value = "/product-specifications/{ids}")
    ResponseEntity batchDelete(@PathVariable("ids") String ids);

    /**
     * 根据条件分页查询商品规格信息列表
     *
     * @param param 分页查询条件
     * @return 分页查询结果
     */
    @PostMapping(value = "/product-specifications/pages")
    ResponseEntity<Pages<ProductSpecification>> pages(@RequestBody ProductSpecificationParam param);

}
