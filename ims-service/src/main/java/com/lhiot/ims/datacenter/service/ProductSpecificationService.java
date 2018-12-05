package com.lhiot.ims.datacenter.service;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tips;
import com.lhiot.dc.dictionary.DictionaryClient;
import com.lhiot.dc.dictionary.module.Dictionary;
import com.lhiot.ims.datacenter.feign.ProductFegin;
import com.lhiot.ims.datacenter.feign.ProductSpecificationFegin;
import com.lhiot.ims.datacenter.feign.entity.Product;
import com.lhiot.ims.datacenter.feign.entity.ProductAttachment;
import com.lhiot.ims.datacenter.feign.entity.ProductSpecification;
import com.lhiot.ims.datacenter.feign.model.ProductSpecificationParam;
import com.lhiot.ims.datacenter.feign.model.ProductSpecificationResult;
import com.lhiot.ims.datacenter.feign.type.AttachmentType;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author hufan created in 2018/11/23 9:42
 **/
@Service
@Transactional
public class ProductSpecificationService {

    private DictionaryClient dictionaryClient;
    private final ProductSpecificationFegin productSpecificationFegin;
    private final ProductFegin productFegin;

    public ProductSpecificationService(DictionaryClient dictionaryClient, ProductSpecificationFegin productSpecificationFegin, ProductFegin productFegin) {
        this.dictionaryClient = dictionaryClient;
        this.productSpecificationFegin = productSpecificationFegin;
        this.productFegin = productFegin;
    }

    /**
     * 获取所有基础规格单位列表
     *
     * @return
     */
    public Tips<List<String>> getUnits() {
        Optional<Dictionary> optional = dictionaryClient.dictionary("units");
        if (optional.isPresent()) {
            Dictionary dictionary = optional.get();
            List<String> units = dictionary.getEntries().stream().map(Dictionary.Entry::getName).collect(Collectors.toList());
            Tips<List<String>> tips = new Tips();
            tips.setData(units);
            return tips;
        }
        return Tips.empty();
    }

    public Tips pages(ProductSpecificationParam param) {
        ResponseEntity entity = productSpecificationFegin.pages(param);
        if (entity.getStatusCode().isError()) {
            return Tips.warn(entity.getBody().toString());
        }
        Pages pages = (Pages) entity.getBody();
        List<ProductSpecification> productSpecificationList = pages.getArray();
        productSpecificationList = productSpecificationList.stream().filter(productSpecification -> Objects.nonNull(productSpecification.getProduct())).collect(Collectors.toList());
        if (!productSpecificationList.isEmpty() && productSpecificationList.size() > 0) {
            productSpecificationList.forEach(productSpecification -> {
                    String specificationInfo = productSpecification.getProduct().getName() + " " + productSpecification.getWeight() + productSpecification.getPackagingUnit() + "[" + productSpecification.getBarcode() + "]";
                    productSpecification.setSpecificationInfo(specificationInfo);
                    List<ProductAttachment> attachmentList = productSpecification.getProduct().getAttachments();
                    if (!attachmentList.isEmpty() && attachmentList.size() > 0) {
                        List<ProductAttachment> mainImgList = attachmentList.stream().filter(productAttachment -> Objects.equals(AttachmentType.MAIN_IMG, productAttachment.getAttachmentType())).collect(Collectors.toList());
                        if (!mainImgList.isEmpty() && mainImgList.size() >0) {
                            String productImage = mainImgList.get(0).getUrl();
                            productSpecification.getProduct().setProductImage(productImage);
                        }
                    }


            });
        }
        Tips tips = new Tips();
        tips.setData(productSpecificationList);
        return tips;
    }
}