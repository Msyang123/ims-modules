package com.lhiot.ims.datacenter.service;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tips;
import com.lhiot.ims.datacenter.feign.ProductCategoryFeign;
import com.lhiot.ims.datacenter.feign.entity.ProductCategory;
import com.lhiot.ims.datacenter.feign.model.ProductCategoryParam;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
     *
     * @return 返回商品分类树结构
     */
    @SuppressWarnings("unchecked")
    public Tips<List<ProductCategory>> tree() {
        ProductCategoryParam productCategoryParam = new ProductCategoryParam();
        ResponseEntity entity = productCategoryFeign.pages(productCategoryParam);
        if (entity.getStatusCode().isError() || Objects.isNull(entity.getBody())) {
            return Tips.warn(Objects.isNull(entity.getBody()) ? "基础服务调用失败" : (String) entity.getBody());
        }
        Pages<ProductCategory> pages = (Pages<ProductCategory>) entity.getBody();
        return Objects.isNull(pages) ? Tips.empty() : Tips.<List<ProductCategory>>empty().data(pages.getArray());
    }

}