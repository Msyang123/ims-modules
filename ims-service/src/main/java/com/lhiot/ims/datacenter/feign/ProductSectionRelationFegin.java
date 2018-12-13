package com.lhiot.ims.datacenter.feign;

import com.lhiot.ims.datacenter.feign.entity.ProductSectionRelation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2018/11/21 16:07
 **/
@Component
@FeignClient(value = "basic-data-service-v1-0")
public interface ProductSectionRelationFegin {
    /**
     * 添加版块与商品上架关系
     */
    @PostMapping(value = "/product-section-relations")
    ResponseEntity create(@RequestBody ProductSectionRelation param);

    /**
     * 批量添加版块与商品上架关系
     * 多个商品上架Id以英文逗号分隔
     */
    @PostMapping(value = "/product-section-relations/batches")
    ResponseEntity createBatch(@RequestParam("sectionId") Long sectionId, @RequestParam("shelfIds") String shelfIds);

    /**
     * 根据Id删除版块与商品上架关系
     */
    @DeleteMapping(value = "/product-section-relations/{id}")
    ResponseEntity delete(@PathVariable("id") Long id);

    /**
     * 批量删除版块与商品上架关系
     * 多个商品上架Id以英文逗号分隔
     */
    @DeleteMapping(value = "/product-section-relations/batches")
    ResponseEntity deleteBatch(@RequestParam("sectionId") Long sectionId, @RequestParam(value = "shelfIds", required = false) String shelfIds);

}
