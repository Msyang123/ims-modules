package com.lhiot.ims.datacenter.service;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tips;
import com.lhiot.ims.datacenter.feign.ProductFegin;
import com.lhiot.ims.datacenter.feign.ProductSpecificationFegin;
import com.lhiot.ims.datacenter.feign.entity.ProductSpecification;
import com.lhiot.ims.datacenter.feign.entity.type.InventorySpecification;
import com.lhiot.ims.datacenter.model.ProductInfo;
import com.lhiot.ims.datacenter.model.ProductSpecificationParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author hufan created in 2018/11/23 18:46
 **/
@Service
@Transactional
public class ProductService {
    private final ProductFegin productFegin;
    private final ProductSpecificationFegin productSpecificationFegin;

    @Autowired
    public ProductService(ProductFegin productFegin, ProductSpecificationFegin productSpecificationFegin) {
        this.productFegin = productFegin;
        this.productSpecificationFegin = productSpecificationFegin;
    }

    /**
     * 获取商品及关联附件和基础规格信息
     *
     * @param id
     * @return
     */
    public Tips<ProductInfo> findProductById(Long id) {
        ProductInfo productInfo = null;
        ResponseEntity productEntity = productFegin.findById(id);
        if (Objects.isNull(productEntity) || productEntity.getStatusCode().isError()) {
            return Tips.warn(productEntity.getBody().toString());
        }
        productInfo = (ProductInfo) productEntity.getBody();
        if (Objects.nonNull(productInfo)) {
            // 查询基础规格信息
            ProductSpecificationParam productSpecificationParam = new ProductSpecificationParam();
            productSpecificationParam.setInventorySpecification(InventorySpecification.YES);
            productSpecificationParam.setProductId(productInfo.getId());
            ResponseEntity productSpecificationEntity = productSpecificationFegin.pages(productSpecificationParam);
            if (productSpecificationEntity.getStatusCode().isError()) {
                return Tips.warn(productSpecificationEntity.getBody().toString());
            }
            Pages<ProductSpecification> pages = (Pages<ProductSpecification>) productSpecificationEntity.getBody();
            if (pages.getArray().size() != 0) {
                ProductSpecification productSpecification = pages.getArray().get(0);
                productInfo.setProductSpecification(productSpecification);
                Tips<ProductInfo> tips = new Tips();
                tips.setData(productInfo);
                return tips;
            }
        }
        return Tips.empty();
    }
}