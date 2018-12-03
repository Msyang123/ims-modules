package com.lhiot.ims.datacenter.service;

import com.leon.microx.predefine.Use;
import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tips;
import com.lhiot.ims.datacenter.feign.ProductFegin;
import com.lhiot.ims.datacenter.feign.ProductSpecificationFegin;
import com.lhiot.ims.datacenter.feign.entity.Product;
import com.lhiot.ims.datacenter.feign.entity.ProductAttachment;
import com.lhiot.ims.datacenter.feign.entity.ProductSpecification;
import com.lhiot.ims.datacenter.feign.model.ProductResult;
import com.lhiot.ims.datacenter.feign.model.ProductSpecificationParam;
import com.lhiot.ims.datacenter.feign.type.AttachmentType;
import com.lhiot.ims.datacenter.feign.type.InventorySpecification;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
     * @param productResult
     * @return
     */
    public Tips create(ProductResult productResult) {
        Product product = new Product();
        BeanUtils.copyProperties(productResult, product);
        // 设置附件
        List<ProductAttachment> productAttachments = setAttachmentImages(productResult.getMainImg(), productResult.getSubImg(), productResult.getDetailImg(), productResult.getIcon());
        product.setAttachments(productAttachments);
        ResponseEntity productEntity = productFegin.create(product);
        if (productEntity.getStatusCode().isError()) {
            return Tips.warn(productEntity.getBody().toString());
        }
        // 返回参数 例：<201 Created,{content-type=[application/json;charset=UTF-8], date=[Sat, 24 Nov 2018 06:37:59 GMT], location=[/product-sections/13], transfer-encoding=[chunked]}>
        String location = productEntity.getHeaders().getLocation().toString();
        Long productId = Long.valueOf(location.substring(location.lastIndexOf('/') + 1));

        if (Objects.isNull(productResult.getProductSpecification())) {
            return Tips.warn("商品基础条码不能为空");
        }
        // 添加商品基础规格
        ProductSpecification productSpecification = productResult.getProductSpecification();
        productSpecification.setInventorySpecification(InventorySpecification.YES);
        productSpecification.setAvailableStatus(Use.ENABLE);
        productSpecification.setProductId(productId);
        // TODO  productSpecification.setSpecificationQty(XXX);
        ResponseEntity specificationEntity = productSpecificationFegin.create(productSpecification);
        if (specificationEntity.getStatusCode().isError()) {
            return Tips.warn(specificationEntity.getBody().toString());
        }
        return Tips.info(productId + "");
    }

    /**
     * 修改商品
     *
     * @param id
     * @param productResult
     * @return
     */
    public Tips update(Long id, ProductResult productResult) {
        Product product = new Product();
        BeanUtils.copyProperties(productResult, product);
        // 设置附件
        List<ProductAttachment> productAttachments = setAttachmentImages(productResult.getMainImg(), productResult.getSubImg(), productResult.getDetailImg(), productResult.getIcon());
        product.setAttachments(productAttachments);
        ResponseEntity entity = productFegin.update(id, product);
        if (entity.getStatusCode().isError()) {
            return Tips.warn(entity.getBody().toString());
        }
        if (Objects.isNull(productResult.getProductSpecification())) {
            return Tips.warn("商品基础条码不能为空");
        }
        // 修改商品基础规格
        ProductSpecification productSpecification = productResult.getProductSpecification();
        ResponseEntity specificationEntity = productSpecificationFegin.update(productSpecification.getId(), productSpecification);
        if (specificationEntity.getStatusCode().isError()) {
            return Tips.warn(specificationEntity.getBody().toString());
        }
        return Tips.info("修改成功");
    }

    /**
     * 获取商品及关联附件和基础规格信息
     *
     * @param id
     * @return
     */
    public Tips<ProductResult> findProductById(Long id) {
        Tips<ProductResult> tips = new Tips();
        ProductResult productResult = new ProductResult();
        ResponseEntity productEntity = productFegin.findById(id);
        if (productEntity.getStatusCode().isError()) {
            return Tips.warn(productEntity.getBody().toString());
        }
        Product product = (Product) productEntity.getBody();
        if (Objects.nonNull(product)) {
            // 设置附件信息
            BeanUtils.copyProperties(product, productResult);
            if (!product.getAttachments().isEmpty()) {
                List<String> mainImg = product.getAttachments().stream().filter(item -> item.getAttachmentType().equals(AttachmentType.MAIN_IMG)).map(ProductAttachment::getUrl).collect(Collectors.toList());
                if (!mainImg.isEmpty()) {
                    productResult.setMainImg(mainImg.get(0));
                }
                productResult.setSubImg(product.getAttachments().stream().filter(item -> item.getAttachmentType().equals(AttachmentType.SUB_IMG)).map(ProductAttachment::getUrl).collect(Collectors.toList()));
                productResult.setDetailImg(product.getAttachments().stream().filter(item -> item.getAttachmentType().equals(AttachmentType.DETAIL_IMG)).map(ProductAttachment::getUrl).collect(Collectors.toList()));
                List<String> subImg = product.getAttachments().stream().filter(item -> item.getAttachmentType().equals(AttachmentType.ICON)).map(ProductAttachment::getUrl).collect(Collectors.toList());
                if (!subImg.isEmpty()) {
                    productResult.setIcon(subImg.get(0));
                }
            }
            // 查询基础规格信息
            ProductSpecificationParam productSpecificationParam = new ProductSpecificationParam();
            productSpecificationParam.setInventorySpecification(InventorySpecification.YES);
            productSpecificationParam.setProductId(productResult.getId());
            ResponseEntity productSpecificationEntity = productSpecificationFegin.pages(productSpecificationParam);
            if (productSpecificationEntity.getStatusCode().isError()) {
                return Tips.warn(productSpecificationEntity.getBody().toString());
            }

            Pages<ProductSpecification> pages = (Pages<ProductSpecification>) productSpecificationEntity.getBody();
            if (!pages.getArray().isEmpty() && pages.getArray().size() > 0) {
                ProductSpecification productSpecification = pages.getArray().get(0);
                productResult.setProductSpecification(productSpecification);
                tips.setData(productResult);
                return tips;
            }
            productResult.setProductSpecification(new ProductSpecification());
            tips.setData(productResult);
            return tips;
        }
        return Tips.empty();
    }

    /**
     * 设置附件图片
     *
     * @param mainImg
     * @param subImgs
     * @param detailImgs
     * @param icon
     * @return
     */
    public List<ProductAttachment> setAttachmentImages(String mainImg, List<String> subImgs, List<String> detailImgs, String icon) {
        List<ProductAttachment> productAttachments = new ArrayList<>();
        if (Objects.nonNull(mainImg)) {
            ProductAttachment attachmentMainImg = new ProductAttachment();
            attachmentMainImg.setUrl(mainImg);
            attachmentMainImg.setSorting(1);
            attachmentMainImg.setAttachmentType(AttachmentType.MAIN_IMG);
            productAttachments.add(attachmentMainImg);
        }
        if (Objects.nonNull(subImgs)) {
            for (String imgs : subImgs) {
                ProductAttachment attachmentSubImg = new ProductAttachment();
                attachmentSubImg.setUrl(imgs);
                attachmentSubImg.setSorting(subImgs.indexOf(imgs) + 1);
                attachmentSubImg.setAttachmentType(AttachmentType.SUB_IMG);
                productAttachments.add(attachmentSubImg);
            }
        }
        if (Objects.nonNull(detailImgs)) {
            for (String imgs : detailImgs) {
                ProductAttachment attachmentDetailImg = new ProductAttachment();
                attachmentDetailImg.setUrl(imgs);
                attachmentDetailImg.setSorting(detailImgs.indexOf(imgs) + 1);
                attachmentDetailImg.setAttachmentType(AttachmentType.DETAIL_IMG);
                productAttachments.add(attachmentDetailImg);
            }
        }
        if (Objects.nonNull(icon)) {
            ProductAttachment attachmentIcon = new ProductAttachment();
            attachmentIcon.setUrl(icon);
            attachmentIcon.setSorting(1);
            attachmentIcon.setAttachmentType(AttachmentType.ICON);
            productAttachments.add(attachmentIcon);
        }
        return productAttachments;
    }
}