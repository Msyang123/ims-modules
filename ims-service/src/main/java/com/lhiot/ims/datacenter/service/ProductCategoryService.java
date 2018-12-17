package com.lhiot.ims.datacenter.service;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tips;
import com.lhiot.ims.datacenter.feign.ProductCategoryFeign;
import com.lhiot.ims.datacenter.feign.entity.ProductCategory;
import com.lhiot.ims.datacenter.feign.model.ProductCategoryParam;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hufan created in 2018/11/26 9:09
 **/
@Service
public class ProductCategoryService {
    private final ProductCategoryFeign productCategoryFeign;

    public ProductCategoryService(ProductCategoryFeign productCategoryFeign) {
        this.productCategoryFeign = productCategoryFeign;
    }

    /**
     * 查询商品分类树结构
     * @return
     */
    public Tips<List<ProductCategory>> tree(){
        ProductCategoryParam productCategoryParam = new ProductCategoryParam();
        ResponseEntity entity = productCategoryFeign.pages(productCategoryParam);
        if (entity.getStatusCode().isError()) {
            return Tips.warn((String) entity.getBody());
        }
        Pages<ProductCategory> pages = (Pages<ProductCategory>) entity.getBody();
        List<ProductCategory> productCategory = pages.getArray();
        Tips tips = new Tips();
        if (!productCategory.isEmpty()) {
            tips.setData(productCategory);
            return tips;
        }
        return Tips.empty();
    }

}