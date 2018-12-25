package com.lhiot.ims.datacenter.service;

import com.leon.microx.util.StringUtils;
import com.leon.microx.web.result.Tips;
import com.lhiot.ims.datacenter.feign.ProductSectionFeign;
import com.lhiot.ims.datacenter.feign.ProductSectionRelationFeign;
import com.lhiot.ims.datacenter.feign.entity.ProductSection;
import com.lhiot.ims.datacenter.feign.entity.ProductShelf;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author hufan created in 2018/11/24 11:02
 **/
@Service
public class ProductSectionService {
    private final ProductSectionFeign productSectionFeign;
    private final ProductSectionRelationFeign productSectionRelationFeign;

    public ProductSectionService(ProductSectionFeign productSectionFeign, ProductSectionRelationFeign productSectionRelationFeign) {
        this.productSectionFeign = productSectionFeign;
        this.productSectionRelationFeign = productSectionRelationFeign;
    }

    public Tips create(ProductSection productSection) {
        ResponseEntity entity = productSectionFeign.create(productSection);
        if (entity.getStatusCode().isError()) {
            return Tips.warn((String) entity.getBody());
        }
        // 返回参数 例：<201 Created,{content-type=[application/json;charset=UTF-8], date=[Sat, 24 Nov 2018 06:37:59 GMT], location=[/product-sections/13], transfer-encoding=[chunked]}>
        String location = Objects.isNull(entity.getHeaders().getLocation()) ? "" : entity.getHeaders().getLocation().toString();
        Long sectionId = Long.valueOf(location.substring(location.lastIndexOf('/') + 1));
        // 如果新增后商品板块id不为空，且上架ids不为空 添加板块和商品之间的关联
        if (sectionId > 0 && Objects.nonNull(productSection.getProductShelfList())) {
            List<Long> shelfIdList = productSection.getProductShelfList().stream().map(ProductShelf::getId).collect(Collectors.toList());
            String shelfIds = StringUtils.collectionToDelimitedString(shelfIdList, ",");
            productSectionRelationFeign.createBatch(sectionId, shelfIds);
        }
        return Tips.info(sectionId + "");
    }
}