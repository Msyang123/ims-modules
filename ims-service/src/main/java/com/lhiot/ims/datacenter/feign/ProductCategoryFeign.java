package com.lhiot.ims.datacenter.feign;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.datacenter.feign.entity.ProductCategory;
import com.lhiot.ims.datacenter.model.ProductCategoryParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author hufan created in 2018/11/20 12:03
 **/
@Component
@FeignClient(value = "basic-data-service-v1-0-hufan")
public interface ProductCategoryFeign {

    /**
     * 添加商品分类
     */
    @RequestMapping(value = "/product-categories/", method = RequestMethod.POST)
    ResponseEntity create(@RequestBody ProductCategory productCategory);

    /**
     * 修改商品分类
     */
    @RequestMapping(value = "/product-categories/{id}", method = RequestMethod.PUT)
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody ProductCategory productCategory);

    /**
     * 根据Id查找商品分类
     */
    @RequestMapping(value = "/product-categories/{id}", method = RequestMethod.GET)
    ResponseEntity<ProductCategory> findById(@PathVariable("id") Long id);

    /**
     * 根据Ids删除商品分类，英文逗号分隔
     */
    @RequestMapping(value = "/product-categories/{ids}", method = RequestMethod.DELETE)
    ResponseEntity batchDelete(@PathVariable("ids") String ids);

    /**
     * 查询商品分类分页信息
     * 必须指定类型，否则接受到的参数会变为string
     */
    @RequestMapping(value = "/product-categories/pages", method = RequestMethod.POST)
    ResponseEntity<Pages<ProductCategory>> pages(@RequestBody ProductCategoryParam param);
}
