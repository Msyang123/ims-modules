package com.lhiot.ims.datacenter.feign;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.datacenter.feign.entity.Product;
import com.lhiot.ims.datacenter.model.ProductInfo;
import com.lhiot.ims.datacenter.model.ProductParam;
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
public interface ProductFegin {
    /**
     * 添加商品
     */
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    ResponseEntity create(@RequestBody Product param);

    /**
     * 修改商品
     */
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody Product param);

    /**
     * 根据Id查找商品
     */
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    ResponseEntity<Product> findById(@PathVariable("id") Long id);

    /**
     * 根据商品Ids删除商品
     */
    @RequestMapping(value = "/products/{ids}", method = RequestMethod.DELETE)
    ResponseEntity batchDelete(@PathVariable("ids") String ids);


    /**
     * 根据条件分页查询商品信息列表
     */
    @RequestMapping(value = "/products/pages", method = RequestMethod.POST)
    ResponseEntity<Pages<Product>> pages(@RequestBody ProductParam param);

}
