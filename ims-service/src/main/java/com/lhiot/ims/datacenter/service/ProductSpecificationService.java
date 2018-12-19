package com.lhiot.ims.datacenter.service;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tips;
import com.lhiot.dc.dictionary.DictionaryClient;
import com.lhiot.dc.dictionary.module.Dictionary;
import com.lhiot.ims.datacenter.feign.ProductSpecificationFeign;
import com.lhiot.ims.datacenter.feign.entity.ProductAttachment;
import com.lhiot.ims.datacenter.feign.entity.ProductSpecification;
import com.lhiot.ims.datacenter.feign.model.ProductSpecificationParam;
import com.lhiot.ims.datacenter.feign.type.AttachmentType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author hufan created in 2018/11/23 9:42
 **/
@Service
@SuppressWarnings("unchecked")
public class ProductSpecificationService {

    private DictionaryClient dictionaryClient;
    private final ProductSpecificationFeign productSpecificationFeign;

    public ProductSpecificationService(DictionaryClient dictionaryClient, ProductSpecificationFeign productSpecificationFeign) {
        this.dictionaryClient = dictionaryClient;
        this.productSpecificationFeign = productSpecificationFeign;
    }

    /**
     * 获取所有基础规格单位列表
     *
     * @return 所有基础规格单位列表
     */
    public Tips<List<String>> getUnits() {
        Optional<Dictionary> optional = dictionaryClient.dictionary("units");
        if (optional.isPresent()) {
            Dictionary dictionary = optional.get();
            List<String> units = dictionary.getEntries().stream().map(Dictionary.Entry::getName).collect(Collectors.toList());
            return Tips.<List<String>>empty().data(units);
        }
        return Tips.empty();
    }

    public Tips<Pages<ProductSpecification>> pages(ProductSpecificationParam param) {
        ResponseEntity entity = productSpecificationFeign.pages(param);
        if (entity.getStatusCode().isError()) {
            return Tips.warn((String) entity.getBody());
        }
        Pages pages = (Pages) entity.getBody();
        if (Objects.isNull(pages)) {
            return Tips.empty();
        }
        List<ProductSpecification> productSpecificationList = pages.getArray();
        productSpecificationList = productSpecificationList.stream().filter(productSpecification -> Objects.nonNull(productSpecification.getProduct())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(productSpecificationList)) {
            productSpecificationList.forEach(productSpecification -> {
                String specificationInfo = productSpecification.getProduct().getName() + " " + productSpecification.getWeight() + productSpecification.getPackagingUnit() + "[" + productSpecification.getBarcode() + "]";
                productSpecification.setSpecificationInfo(specificationInfo);
                List<ProductAttachment> attachmentList = productSpecification.getProduct().getAttachments();
                if (!CollectionUtils.isEmpty(attachmentList)) {
                    List<ProductAttachment> mainImgList = attachmentList.stream().filter(productAttachment -> Objects.equals(AttachmentType.MAIN_IMG, productAttachment.getAttachmentType())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(mainImgList)) {
                        String productImage = mainImgList.get(0).getUrl();
                        productSpecification.getProduct().setProductImage(productImage);
                    }
                }
            });
        }
        return Tips.<Pages<ProductSpecification>>empty().data(Pages.of(pages.getTotal(), productSpecificationList));
    }
}