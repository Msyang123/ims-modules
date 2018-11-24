package com.lhiot.ims.datacenter.service;

import com.leon.microx.web.result.Tips;
import com.lhiot.ims.datacenter.feign.ProductSectionFegin;
import com.lhiot.ims.datacenter.feign.ProductSectionRelationFegin;
import com.lhiot.ims.datacenter.feign.entity.ProductSection;
import com.lhiot.ims.datacenter.feign.entity.ProductShelf;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author hufan created in 2018/11/24 11:02
 **/
@Service
@Transactional
public class ProductSectionService {
    private final ProductSectionFegin productSectionFegin;
    private final ProductSectionRelationFegin productSectionRelationFegin;

    public ProductSectionService(ProductSectionFegin productSectionFegin, ProductSectionRelationFegin productSectionRelationFegin) {
        this.productSectionFegin = productSectionFegin;
        this.productSectionRelationFegin = productSectionRelationFegin;
    }

    /**
     * 新增板块 及板块商品关联
     *
     * @param productSection
     * @return
     */
    public Tips create(ProductSection productSection) {
        ResponseEntity entity = productSectionFegin.create(productSection);
        if (entity.getStatusCode().isError()) {
            return Tips.warn(entity.getBody().toString());
        }
        // 返回参数 例：<201 Created,{content-type=[application/json;charset=UTF-8], date=[Sat, 24 Nov 2018 06:37:59 GMT], location=[/product-sections/13], transfer-encoding=[chunked]}>
        String location = entity.getHeaders().getLocation().toString();
        Long sectionId = Long.valueOf(location.substring(location.lastIndexOf("/") + 1));
        // 如果新增后商品板块id不为空，且上架ids不为空 添加板块和商品之间的关联
        if (Objects.nonNull(sectionId) && Objects.nonNull(productSection.getProductShelfList())) {
            List<Long> shelfIdList = productSection.getProductShelfList().stream().map(ProductShelf::getId).collect(Collectors.toList());
            String shelfIds = StringUtils.join(shelfIdList, ",");
            productSectionRelationFegin.createBatch(sectionId, shelfIds);
        }
        return Tips.info(sectionId + "");
    }

    public Tips updateBatch(Long sectionId, String productIds) {
        // 1、先批量删除sectionId关联的所有记录
        ResponseEntity deleteEntity = productSectionRelationFegin.deleteBatch(sectionId, productIds);
        if (Objects.isNull(deleteEntity) || deleteEntity.getStatusCode().isError()) {
            return Tips.warn(deleteEntity.getBody().toString());
        }
        // 2、再批量添加
        ResponseEntity addEntity = productSectionRelationFegin.createBatch(sectionId, productIds);
        if (Objects.isNull(addEntity) || addEntity.getStatusCode().isError()) {
            return Tips.warn(addEntity.getBody().toString());
        }
        return Tips.info("修改成功");
    }
}