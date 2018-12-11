package com.lhiot.ims.datacenter.service;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tips;
import com.lhiot.ims.datacenter.feign.ProductFegin;
import com.lhiot.ims.datacenter.feign.ProductSectionRelationFegin;
import com.lhiot.ims.datacenter.feign.ProductShelfFegin;
import com.lhiot.ims.datacenter.feign.entity.ProductAttachment;
import com.lhiot.ims.datacenter.feign.entity.ProductShelf;
import com.lhiot.ims.datacenter.feign.entity.ProductSpecification;
import com.lhiot.ims.datacenter.feign.model.ProductShelfParam;
import com.lhiot.ims.datacenter.feign.type.ApplicationType;
import com.lhiot.ims.datacenter.feign.type.AttachmentType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public Tips create(ProductShelf productShelf) {
        productShelf.setApplicationType(ApplicationType.HEALTH_GOOD);
        ResponseEntity productShelfEntity = productShelfFegin.create(productShelf);
        if (productShelfEntity.getStatusCode().isError()) {
            return Tips.warn(productShelfEntity.getBody().toString());
        }
        // 返回参数 例：<201 Created,{content-type=[application/json;charset=UTF-8], date=[Sat, 24 Nov 2018 06:37:59 GMT], location=[/product-sections/13], transfer-encoding=[chunked]}>
        String location = productShelfEntity.getHeaders().getLocation().toString();
        Long productShelfId = Long.valueOf(location.substring(location.lastIndexOf('/') + 1));

        // 添加上架id和上架板块的关联
        if (Objects.nonNull(productShelf.getSectionIds())) {
            ResponseEntity entity = productSectionRelationFegin.createBatch(productShelfId, productShelf.getSectionIds());
            if (entity.getStatusCode().isError()) {
                return Tips.warn(entity.getBody().toString());
            }
        }
        return Tips.info(productShelfId + "");
    }

    public Tips update(Long id, ProductShelf productShelf) {
        ResponseEntity productShelfEntity = productShelfFegin.update(id, productShelf);
        if (productShelfEntity.getStatusCode().isError()) {
            return Tips.warn(productShelfEntity.getBody().toString());
        }
        return Tips.info("修改成功");
    }

    public Tips findById(Long id) {
        ResponseEntity entity = productShelfFegin.findById(id, true);
        if (entity.getStatusCode().isError()) {
            Tips.warn(entity.getBody().toString());
        }
        ProductShelf productShelf = (ProductShelf) entity.getBody();
        ProductSpecification productSpecification = productShelf.getProductSpecification();
        if (Objects.nonNull(productSpecification) && Objects.nonNull(productSpecification.getProduct())) {
            String specificationInfo = productSpecification.getProduct().getName() + " " + productSpecification.getWeight() + productSpecification.getPackagingUnit() + "[" + productSpecification.getBarcode() + "]";
            productShelf.getProductSpecification().setSpecificationInfo(specificationInfo);
            List<ProductAttachment> attachmentList = productSpecification.getProduct().getAttachments();
            if (!attachmentList.isEmpty() && attachmentList.size() > 0) {
                List<ProductAttachment> mainImgList = attachmentList.stream().filter(productAttachment -> Objects.equals(AttachmentType.MAIN_IMG, productAttachment.getAttachmentType())).collect(Collectors.toList());
                if (!mainImgList.isEmpty() && mainImgList.size() >0) {
                    String productImage = mainImgList.get(0).getUrl();
                    productSpecification.getProduct().setProductImage(productImage);
                }
            }
        }

        // Long shelfId = productShelf.getId();
        // FIXME 基础服务提供根据上架id查询关联的板块ids集合
        // productSectionRelationFegin
        // productShelf.setSelected();
        Tips tips = new Tips();
        tips.setData(productShelf);
        return tips;
    }


    public Tips pages(ProductShelfParam param) {
        Tips tips = new Tips();
        param.setIncludeProduct(true);
        param.setApplicationType(ApplicationType.HEALTH_GOOD);
        ResponseEntity entity = productShelfFegin.pages(param);
        if (entity.getStatusCode().isError()) {
            Tips.warn(entity.getBody().toString());
        }
        Pages<ProductShelf> pages = (Pages<ProductShelf>) entity.getBody();
        List<ProductShelf> productShelfList = pages.getArray();
        if (!productShelfList.isEmpty() && productShelfList.size() >0 ) {
            productShelfList.forEach(productShelf -> {
                ProductSpecification productSpecification = productShelf.getProductSpecification();
                productShelf.setProductName(productSpecification.getProduct().getName());
                String shelfSpecification = productSpecification.getWeight()+productSpecification.getPackagingUnit()+"*" + productSpecification.getSpecificationQty() + "份";
                productShelf.setShelfSpecification(shelfSpecification);
                productShelf.setBarcode(productSpecification.getBarcode());
                String specificationInfo = productSpecification.getProduct().getName() + " " + productSpecification.getWeight() + productSpecification.getPackagingUnit() + "[" + productSpecification.getBarcode() + "]";
                productShelf.setSpecificationInfo(specificationInfo);
            });
        }
        tips.setData(Pages.of(pages.getTotal(), productShelfList));
        return tips;
    }
}