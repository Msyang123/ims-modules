package com.lhiot.ims.datacenter.feign;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.datacenter.feign.entity.ProductSection;
import com.lhiot.ims.datacenter.feign.model.ProductSectionParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2018/11/21 16:07
 **/
@Component
@FeignClient(value = "basic-data-service-v1-0")
public interface ProductSectionFeign {

    /**
     * 添加商品版块
     *
     * @param param 要添加的商品版块
     * @return 添加操作的结果
     */
    @PostMapping(value = "/product-sections")
    ResponseEntity create(@RequestBody ProductSection param);

    /**
     * 修改商品版块
     *
     * @param id    商品版块id
     * @param param 要修改的商品版块
     * @return 修改操作的结果
     */
    @PutMapping(value = "/product-sections/{id}")
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody ProductSection param);

    /**
     * 根据Id查找商品版块
     *
     * @param id                商品版块id
     * @param includeShelves    是否包括上架商品信息
     * @param includeShelvesQty 包括上架商品的最大条数
     * @param includeProduct    是否包括商品信息
     * @return 查询结果
     */
    @GetMapping(value = "/product-sections/{id}")
    ResponseEntity<ProductSection> findById(@PathVariable("id") Long id, @RequestParam(value = "includeShelves", required = false) boolean includeShelves,
                                            @RequestParam(value = "includeShelvesQty", required = false) Long includeShelvesQty, @RequestParam(value = "includeProduct", required = false) boolean includeProduct);

    /**
     * 根据Ids删除商品版块
     *
     * @param ids 商品版块ids，用英文逗号分隔
     * @return 删除操作结果
     */
    @DeleteMapping(value = "/product-sections/{ids}")
    ResponseEntity batchDelete(@PathVariable("ids") String ids);

    /**
     * 根据条件分页查询商品版块信息列表
     *
     * @param param 分页查询条件
     * @return 分页查询结果
     */
    @PostMapping(value = "/product-sections/pages")
    ResponseEntity<Pages<ProductSection>> pages(@RequestBody ProductSectionParam param);

}
