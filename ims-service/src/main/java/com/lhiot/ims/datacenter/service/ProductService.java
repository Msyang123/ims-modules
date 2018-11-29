package com.lhiot.ims.datacenter.service;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tips;
import com.lhiot.ims.datacenter.feign.ProductFegin;
import com.lhiot.ims.datacenter.feign.ProductSpecificationFegin;
import com.lhiot.ims.datacenter.feign.entity.Product;
import com.lhiot.ims.datacenter.feign.entity.ProductAttachment;
import com.lhiot.ims.datacenter.feign.entity.ProductSpecification;
import com.lhiot.ims.datacenter.feign.entity.type.AttachmentType;
import com.lhiot.ims.datacenter.feign.entity.type.InventorySpecification;
import com.lhiot.ims.datacenter.model.ProductInfo;
import com.lhiot.ims.datacenter.model.ProductSpecificationParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
     * 新增商品
     *
     * @param productInfo
     * @return
     */
    public Tips create(ProductInfo productInfo) {
        Product product = new Product();
        BeanUtils.copyProperties(productInfo, product);
        // 设置附件
        List<ProductAttachment> productAttachments = product.setAttachmentImages(productInfo.getMainImg(), productInfo.getSubImg(), productInfo.getDetailImg(), productInfo.getIcon());
        product.setAttachments(productAttachments);
        ResponseEntity entity = productFegin.create(product);
        if (entity.getStatusCode().isError()) {
            return Tips.warn(entity.getBody().toString());
        }
        // 返回参数 例：<201 Created,{content-type=[application/json;charset=UTF-8], date=[Sat, 24 Nov 2018 06:37:59 GMT], location=[/product-sections/13], transfer-encoding=[chunked]}>
        String location = entity.getHeaders().getLocation().toString();
        Long id = Long.valueOf(location.substring(location.lastIndexOf('/') + 1));
        return Tips.info(id + "");
    }

    /**
     * 修改商品
     *
     * @param id
     * @param productInfo
     * @return
     */
    public Tips update(Long id, ProductInfo productInfo) {
        Product product = new Product();
        BeanUtils.copyProperties(productInfo, product);
        // 设置附件
        List<ProductAttachment> productAttachments = product.setAttachmentImages(productInfo.getMainImg(), productInfo.getSubImg(), productInfo.getDetailImg(), productInfo.getIcon());
        product.setAttachments(productAttachments);
        ResponseEntity entity = productFegin.update(id, product);
        if (entity.getStatusCode().isError()) {
            return Tips.warn(entity.getBody().toString());
        }
        return Tips.info("修改成功");
    }

    /**
     * 获取商品及关联附件和基础规格信息
     *
     * @param id
     * @return
     */
    public Tips<ProductInfo> findProductById(Long id) {
        Tips<ProductInfo> tips = new Tips();
        ProductInfo productInfo = new ProductInfo();
        ResponseEntity productEntity = productFegin.findById(id);
        if (productEntity.getStatusCode().isError()) {
            return Tips.warn(productEntity.getBody().toString());
        }
        Product product = (Product) productEntity.getBody();
        if (Objects.nonNull(product)) {
            // 设置附件信息
            BeanUtils.copyProperties(product, productInfo);
            if (!product.getAttachments().isEmpty()) {
                productInfo.setMainImg(product.getAttachments().stream().filter(item -> item.getAttachmentType().equals(AttachmentType.MAIN_IMG)).map(ProductAttachment::getUrl).collect(Collectors.toList()).get(0));
                productInfo.setSubImg(product.getAttachments().stream().filter(item -> item.getAttachmentType().equals(AttachmentType.SUB_IMG)).map(ProductAttachment::getUrl).collect(Collectors.toList()));
                productInfo.setDetailImg(product.getAttachments().stream().filter(item -> item.getAttachmentType().equals(AttachmentType.DETAIL_IMG)).map(ProductAttachment::getUrl).collect(Collectors.toList()));
                productInfo.setIcon(product.getAttachments().stream().filter(item -> item.getAttachmentType().equals(AttachmentType.ICON)).map(ProductAttachment::getUrl).collect(Collectors.toList()).get(0));
            }
            // 查询基础规格信息
            ProductSpecificationParam productSpecificationParam = new ProductSpecificationParam();
            productSpecificationParam.setInventorySpecification(InventorySpecification.YES);
            productSpecificationParam.setProductId(productInfo.getId());
            ResponseEntity productSpecificationEntity = productSpecificationFegin.pages(productSpecificationParam);
            if (productSpecificationEntity.getStatusCode().isError()) {
                return Tips.warn(productSpecificationEntity.getBody().toString());
            }

            Pages<ProductSpecification> pages = (Pages<ProductSpecification>) productSpecificationEntity.getBody();
            if (!pages.getArray().isEmpty()) {
                ProductSpecification productSpecification = pages.getArray().get(0);
                productInfo.setProductSpecification(productSpecification);
                tips.setData(productInfo);
                return tips;
            }
            tips.setData(productInfo);
            return tips;
        }
        return Tips.empty();
    }
}