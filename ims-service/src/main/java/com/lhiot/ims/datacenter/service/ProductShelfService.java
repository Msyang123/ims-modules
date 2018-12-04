package com.lhiot.ims.datacenter.service;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tips;
import com.lhiot.ims.datacenter.feign.ProductFegin;
import com.lhiot.ims.datacenter.feign.ProductSectionRelationFegin;
import com.lhiot.ims.datacenter.feign.ProductShelfFegin;
import com.lhiot.ims.datacenter.feign.entity.Product;
import com.lhiot.ims.datacenter.feign.entity.ProductShelf;
import com.lhiot.ims.datacenter.feign.entity.ProductSpecification;
import com.lhiot.ims.datacenter.feign.model.ProductShelfParam;
import com.lhiot.ims.datacenter.feign.model.ProductShelfResult;
import com.lhiot.ims.datacenter.feign.type.ApplicationType;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author hufan created in 2018/12/3 9:49
 **/
@Service
@Transactional
public class ProductShelfService {
    private final ProductShelfFegin productShelfFegin;
    private final ProductSectionRelationFegin productSectionRelationFegin;
    private final ProductFegin productFegin;


    public ProductShelfService(ProductShelfFegin productShelfFegin, ProductSectionRelationFegin productSectionRelationFegin, ProductFegin productFegin) {
        this.productShelfFegin = productShelfFegin;
        this.productSectionRelationFegin = productSectionRelationFegin;
        this.productFegin = productFegin;
    }

    public Tips create(ProductShelfResult productShelfResult) {
        ProductShelf productShelf = new ProductShelf();
        BeanUtils.copyProperties(productShelfResult, productShelf);

        productShelf.setApplicationType(ApplicationType.HEALTH_GOOD);
        ResponseEntity productShelfEntity = productShelfFegin.create(productShelf);
        if (productShelfEntity.getStatusCode().isError()) {
            return Tips.warn(productShelfEntity.getBody().toString());
        }
        // 返回参数 例：<201 Created,{content-type=[application/json;charset=UTF-8], date=[Sat, 24 Nov 2018 06:37:59 GMT], location=[/product-sections/13], transfer-encoding=[chunked]}>
        String location = productShelfEntity.getHeaders().getLocation().toString();
        Long productShelfId = Long.valueOf(location.substring(location.lastIndexOf('/') + 1));

        // 添加上架id和上架板块的关联
        if (Objects.nonNull(productShelfResult.getSectionIds())) {
            ResponseEntity entity = productSectionRelationFegin.createBatch(productShelfId, productShelfResult.getSectionIds());
            if (entity.getStatusCode().isError()) {
                return Tips.warn(entity.getBody().toString());
            }
        }
        return Tips.info(productShelfId + "");
    }

    public Tips update(Long id, ProductShelfResult productShelfResult) {
        ProductShelf productShelf = new ProductShelf();
        BeanUtils.copyProperties(productShelfResult, productShelf);

        ResponseEntity productShelfEntity = productShelfFegin.update(id, productShelf);
        if (productShelfEntity.getStatusCode().isError()) {
            return Tips.warn(productShelfEntity.getBody().toString());
        }
        return Tips.info("修改成功");
    }

    public Tips pages(ProductShelfParam param) {
        Tips tips = new Tips();
        ResponseEntity entity = productShelfFegin.pages(param);
        if (entity.getStatusCode().isError()) {
            Tips.warn(entity.getBody().toString());
        }
        Pages<ProductShelf> pages = (Pages<ProductShelf>) entity.getBody();
        List<ProductShelf> productShelfList = pages.getArray();
        List<ProductShelfResult> productShelfResultList = new ArrayList<>();
        if (!productShelfList.isEmpty() && productShelfList.size() >0 ) {
            productShelfList.forEach(productShelf -> {
                ProductShelfResult productShelfResult = new ProductShelfResult();
                BeanUtils.copyProperties(productShelf, productShelfResult);
                // 商品规格信息
                ProductSpecification specification = productShelf.getProductSpecification();
                if (Objects.nonNull(specification)) {
                    productShelfResult.setBarcode(specification.getBarcode());
                    String productSpecificationStr = specification.getSpecificationQty() + specification.getPackagingUnit() + "*" + productShelf.getShelfQty() + "份";
                    productShelfResult.setProductSpecification(productSpecificationStr);
                    // 查询商品名称
                    Long productId =specification.getProductId();
                    if (Objects.nonNull(productId)) {
                        ResponseEntity prodcutEntity = productFegin.findById(productId);
                        if (prodcutEntity.getStatusCode().isError()) {
                            Tips.warn(prodcutEntity.getBody().toString());
                        }
                        Product product = (Product) prodcutEntity.getBody();
                        productShelfResult.setProductName(product.getName());
                    }
                }
                productShelfResultList.add(productShelfResult);
            });
        }
        tips.setData(Pages.of(pages.getTotal(), productShelfResultList));
        return tips;
    }
}