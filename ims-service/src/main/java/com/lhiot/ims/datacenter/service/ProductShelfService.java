package com.lhiot.ims.datacenter.service;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tips;
import com.lhiot.ims.datacenter.feign.ProductSectionRelationFeign;
import com.lhiot.ims.datacenter.feign.ProductShelfFeign;
import com.lhiot.ims.datacenter.feign.entity.ProductAttachment;
import com.lhiot.ims.datacenter.feign.entity.ProductShelf;
import com.lhiot.ims.datacenter.feign.entity.ProductSpecification;
import com.lhiot.ims.datacenter.feign.model.ProductShelfParam;
import com.lhiot.ims.datacenter.feign.type.AttachmentType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author hufan created in 2018/12/3 9:49
 **/
@Service
@SuppressWarnings("unchecked")
public class ProductShelfService {
    private final ProductShelfFeign productShelfFeign;
    private final ProductSectionRelationFeign productSectionRelationFeign;


    public ProductShelfService(ProductShelfFeign productShelfFeign, ProductSectionRelationFeign productSectionRelationFeign) {
        this.productShelfFeign = productShelfFeign;
        this.productSectionRelationFeign = productSectionRelationFeign;
    }

    public Tips create(ProductShelf productShelf) {
        ResponseEntity productShelfEntity = productShelfFeign.create(productShelf);
        if (productShelfEntity.getStatusCode().isError()) {
            return Tips.warn((String) productShelfEntity.getBody());
        }
        // 返回参数 例：<201 Created,{content-type=[application/json;charset=UTF-8], date=[Sat, 24 Nov 2018 06:37:59 GMT], location=[/product-sections/13], transfer-encoding=[chunked]}>
        String location = Objects.isNull(productShelfEntity.getHeaders().getLocation()) ? "" : productShelfEntity.getHeaders().getLocation().toString();
        Long productShelfId = Long.valueOf(location.substring(location.lastIndexOf('/') + 1));

        // 添加上架id和上架板块的关联
        if (productShelfId > 0 && Objects.nonNull(productShelf.getSectionIds())) {
            ResponseEntity entity = productSectionRelationFeign.createBatch(productShelfId, productShelf.getSectionIds());
            if (entity.getStatusCode().isError()) {
                return Tips.warn((String) entity.getBody());
            }
        }
        return Tips.info(productShelfId + "");
    }

    public Tips update(Long id, ProductShelf productShelf) {
        ResponseEntity productShelfEntity = productShelfFeign.update(id, productShelf);
        if (productShelfEntity.getStatusCode().isError()) {
            return Tips.warn((String) productShelfEntity.getBody());
        }
        return Tips.info("修改成功");
    }

    public Tips findById(Long id) {
        // 根据上架id查询商品信息
        ResponseEntity entity = productShelfFeign.findById(id, true);
        if (entity.getStatusCode().isError()) {
            return Tips.warn((String) entity.getBody());
        }
        ProductShelf productShelf = (ProductShelf) entity.getBody();
        // 上架商品信息不为空，设值商品规格信息和商品图片
        if (Objects.nonNull(productShelf)) {
            ProductSpecification productSpecification = productShelf.getProductSpecification();
            if (Objects.nonNull(productSpecification) && Objects.nonNull(productSpecification.getProduct())) {
                String specificationInfo = productSpecification.getProduct().getName() + " " + productSpecification.getWeight() + productSpecification.getPackagingUnit() + "[" + productSpecification.getBarcode() + "]";
                productShelf.getProductSpecification().setSpecificationInfo(specificationInfo);
                List<ProductAttachment> attachmentList = productSpecification.getProduct().getAttachments();
                if (!CollectionUtils.isEmpty(attachmentList)) {
                    List<ProductAttachment> mainImgList = attachmentList.stream().filter(productAttachment -> Objects.equals(AttachmentType.MAIN_IMG, productAttachment.getAttachmentType())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(mainImgList)) {
                        String productImage = mainImgList.get(0).getUrl();
                        productSpecification.getProduct().setProductImage(productImage);
                    }
                }
            }
        }
        return Tips.empty().data(productShelf);
    }



    public Tips pages(ProductShelfParam param) {
        Tips tips = new Tips();
        param.setIncludeProduct(true);
        ResponseEntity entity = productShelfFeign.pages(param);
        if (entity.getStatusCode().isError()) {
            return Tips.warn((String) entity.getBody());
        }
        Pages<ProductShelf> pages = (Pages<ProductShelf>) entity.getBody();
        List<ProductShelf> productShelfList = pages.getArray();
        if (!CollectionUtils.isEmpty(productShelfList)) {
            productShelfList.forEach(productShelf -> {
                ProductSpecification productSpecification = productShelf.getProductSpecification();
                productShelf.setProductName(productSpecification.getProduct().getName());
                String shelfSpecification = productSpecification.getSpecificationQty() + productSpecification.getPackagingUnit() + "*" + productShelf.getShelfQty() + "份";
                productShelf.setShelfSpecification(shelfSpecification);
                productShelf.setBarcode(productSpecification.getBarcode());
                productShelf.setSpecificationInfo(productShelf.getName() + " " + shelfSpecification + " [" + productSpecification.getBarcode() + "]");
            });
        }
        tips.setData(Pages.of(pages.getTotal(), productShelfList));
        return tips;
    }
}