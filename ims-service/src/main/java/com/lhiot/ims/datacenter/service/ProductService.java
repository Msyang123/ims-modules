package com.lhiot.ims.datacenter.service;

import com.leon.microx.predefine.Use;
import com.leon.microx.util.StringUtils;
import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tips;
import com.lhiot.ims.datacenter.feign.ProductFeign;
import com.lhiot.ims.datacenter.feign.ProductSpecificationFeign;
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

/**
 * @author hufan created in 2018/11/23 18:46
 **/
@Service
@Transactional
public class ProductService {
    private final ProductFeign productFeign;
    private final ProductSpecificationFeign productSpecificationFeign;

    @Autowired
    public ProductService(ProductFeign productFeign, ProductSpecificationFeign productSpecificationFeign) {
        this.productFeign = productFeign;
        this.productSpecificationFeign = productSpecificationFeign;
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
        ResponseEntity productEntity = productFeign.create(product);
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
        if (Objects.nonNull(productSpecification)) {
            productSpecification.setInventorySpecification(InventorySpecification.YES);
            productSpecification.setAvailableStatus(Use.ENABLE);
            productSpecification.setProductId(productId);
            // TODO  productSpecification.setSpecificationQty(XXX);
            ResponseEntity specificationEntity = productSpecificationFeign.create(productSpecification);
            if (specificationEntity.getStatusCode().isError()) {
                return Tips.warn(specificationEntity.getBody().toString());
            }
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
        ResponseEntity entity = productFeign.update(id, product);
        if (entity.getStatusCode().isError()) {
            return Tips.warn(entity.getBody().toString());
        }
        if (Objects.isNull(productResult.getProductSpecification())) {
            return Tips.warn("商品基础条码不能为空");
        }
        // 修改商品基础规格
        ProductSpecification productSpecification = productResult.getProductSpecification();
        ResponseEntity specificationEntity = productSpecificationFeign.update(productSpecification.getId(), productSpecification);
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
        ResponseEntity productEntity = productFeign.findById(id);
        if (productEntity.getStatusCode().isError()) {
            return Tips.warn(productEntity.getBody().toString());
        }
        Product product = (Product) productEntity.getBody();
        if (Objects.nonNull(product)) {
            // 设置附件信息
            BeanUtils.copyProperties(product, productResult);
            List<String> subImgs = new ArrayList<>();
            List<String> detailImgs = new ArrayList<>();
            List<String> mainImags = new ArrayList<>();
            List<String> icons = new ArrayList<>();
            product.getAttachments().stream().filter(Objects::nonNull).forEach(productAttachment -> {
                switch (productAttachment.getAttachmentType()) {
                    case SUB_IMG:
                        subImgs.add(productAttachment.getUrl());
                        break;
                    case DETAIL_IMG:
                        detailImgs.add(productAttachment.getUrl());
                        break;
                    case MAIN_IMG:
                        mainImags.add(productAttachment.getUrl());
                        break;
                    case ICON:
                        icons.add(productAttachment.getUrl());
                        break;
                    default:
                        break;
                }
            });

            productResult.setSubImg(subImgs);
            productResult.setDetailImg(detailImgs);
            productResult.setMainImg(StringUtils.collectionToDelimitedString(mainImags, ","));
            productResult.setIcon(StringUtils.collectionToDelimitedString(icons, ","));

            // 查询基础规格信息
            ProductSpecificationParam productSpecificationParam = new ProductSpecificationParam();
            productSpecificationParam.setInventorySpecification(InventorySpecification.YES);
            productSpecificationParam.setProductId(productResult.getId());
            ResponseEntity productSpecificationEntity = productSpecificationFeign.pages(productSpecificationParam);
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