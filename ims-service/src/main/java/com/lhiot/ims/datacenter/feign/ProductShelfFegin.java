package com.lhiot.ims.datacenter.feign;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.datacenter.feign.entity.ProductShelf;
import com.lhiot.ims.datacenter.model.ProductShelfParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author hufan created in 2018/11/21 16:07
 **/
@Component
@FeignClient(value = "basic-data-service-v1-0-hufan")
public interface ProductShelfFegin {
    /**
     * 新增商品上架
     */
    @RequestMapping(value = "/product-shelves", method = RequestMethod.POST)
    ResponseEntity create(@RequestBody ProductShelf param);

    /**
     * 修改商品上架
     */
    @RequestMapping(value = "/product-shelves/{id}", method = RequestMethod.PUT)
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody ProductShelf param);

    /**
     * 根据Id查找商品上架
     */
    @RequestMapping(value = "/product-shelves/{id}", method = RequestMethod.GET)
    ResponseEntity<ProductShelf> findById(@PathVariable("id") Long id);

    /**
     * 根据Ids删除商品上架
     */
    @RequestMapping(value = "/product-shelves/{ids}", method = RequestMethod.DELETE)
    ResponseEntity batchDelete(@PathVariable("ids") String ids);


    /**
     * 根据条件分页查询商品上架信息列表
     */
    @RequestMapping(value = "/product-shelves/pages", method = RequestMethod.POST)
    ResponseEntity<Pages<ProductShelf>> pages(@RequestBody ProductShelfParam param);

}
