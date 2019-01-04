package com.lhiot.ims.datacenter.service;

import com.leon.microx.util.Beans;
import com.leon.microx.util.StringUtils;
import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tips;
import com.lhiot.ims.datacenter.feign.AdvertisementFeign;
import com.lhiot.ims.datacenter.feign.ArticleFeign;
import com.lhiot.ims.datacenter.feign.ProductSectionFeign;
import com.lhiot.ims.datacenter.feign.UiPositionFeign;
import com.lhiot.ims.datacenter.feign.entity.Advertisement;
import com.lhiot.ims.datacenter.feign.entity.Article;
import com.lhiot.ims.datacenter.feign.entity.ProductSection;
import com.lhiot.ims.datacenter.feign.entity.UiPosition;
import com.lhiot.ims.datacenter.feign.model.*;
import com.lhiot.ims.datacenter.feign.type.PositionType;
import com.lhiot.ims.datacenter.type.YesOrNo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author hufan created in 2018/12/4 18:26
 **/
@Service
@SuppressWarnings("unchecked")
public class UiPositionService {
    private final UiPositionFeign uiPositionFeign;
    private final ProductSectionFeign productSectionFeign;
    private final AdvertisementFeign advertisementFeign;
    private final ArticleFeign articleFeign;

    public UiPositionService(UiPositionFeign uiPositionFeign, ProductSectionFeign productSectionFeign, AdvertisementFeign advertisementFeign, ArticleFeign articleFeign) {
        this.uiPositionFeign = uiPositionFeign;
        this.productSectionFeign = productSectionFeign;
        this.advertisementFeign = advertisementFeign;
        this.articleFeign = articleFeign;
    }

    public Tips<UiPositionDetail> findById(Long id) {
        UiPositionDetail uiPositionDetail = new UiPositionDetail();
        ResponseEntity entity = uiPositionFeign.findById(id);
        if (entity.getStatusCode().isError()) {
            return Tips.warn((String) entity.getBody());
        }
        UiPosition uiPosition = (UiPosition) entity.getBody();
        if (Objects.isNull(uiPosition)) {
            return Tips.empty();
        }
        Beans.wrap(uiPositionDetail).copyOf(uiPosition);
        PositionType positionType = uiPosition.getPositionType();
        Long uiPositionId = uiPosition.getId();
        switch (positionType) {
            case ARTICLE:
                ArticleParam articleParam = new ArticleParam();
                // FIXME 后期与基础服务保持同步修改
                // articleParam.setPositionId(uiPosition);
                ResponseEntity articleEntity = articleFeign.search(articleParam);
                if (articleEntity.getStatusCode().isError()) {
                    return Tips.warn((String) articleEntity.getBody());
                }
                Pages<Article> articlePages = (Pages<Article>) articleEntity.getBody();
                List<Article> articleList = Objects.isNull(articlePages) ? null : articlePages.getArray();
                if (!CollectionUtils.isEmpty(articleList)) {
                    uiPositionDetail.setArticleList(articleList);
                }
                break;
            case PRODUCT:
                ProductSectionParam productSectionParam = new ProductSectionParam();
                productSectionParam.setPositionIds(uiPositionId.toString());
                productSectionParam.setIncludeShelves(true);
                ResponseEntity productSectionEntity = productSectionFeign.pages(productSectionParam);
                if (productSectionEntity.getStatusCode().isError()) {
                    return Tips.warn((String) productSectionEntity.getBody());
                }
                Pages<ProductSection> productSectionPages = (Pages<ProductSection>) productSectionEntity.getBody();
                List<ProductSection> productSectionList = Objects.isNull(productSectionPages) ? null : productSectionPages.getArray();
                if (!CollectionUtils.isEmpty(productSectionList)) {
                    uiPositionDetail.setProductSectionList(productSectionList);
                }
                break;
            case ADVERTISEMENT:
                AdvertisementParam advertisementParam = new AdvertisementParam();
                advertisementParam.setPositionIds(uiPositionId.toString());
                ResponseEntity advertisementEntity = advertisementFeign.pages(advertisementParam);
                if (advertisementEntity.getStatusCode().isError()) {
                    return Tips.warn((String) advertisementEntity.getBody());
                }
                Pages<Advertisement> advertisementPages = (Pages<Advertisement>) advertisementEntity.getBody();
                List<Advertisement> advertisementList = Objects.isNull(advertisementPages) ? null : advertisementPages.getArray();
                if (!CollectionUtils.isEmpty(advertisementList)) {
                    uiPositionDetail.setAdvertisementList(advertisementList);
                }
                break;
            default:
                break;
        }
        return Tips.<UiPositionDetail>empty().data(uiPositionDetail);

    }

    public Tips<Pages<UiPositionResult>> search(UiPositionParam uiPositionParam) {
        List<UiPositionResult> result = new ArrayList<>();
        // 查询鲜果师的所有位置板块
        ResponseEntity entity = uiPositionFeign.pages(uiPositionParam);
        if (entity.getStatusCode().isError()) {
            return Tips.warn((String) entity.getBody());
        }
        Pages<UiPosition> pages = (Pages) entity.getBody();
        if (Objects.isNull(pages)) {
            return Tips.empty();
        }
        List<UiPosition> uiPositionList = pages.getArray();
        uiPositionList.forEach(uiPosition -> {
            UiPositionResult uiPositionResult = new UiPositionResult();
            Beans.wrap(uiPositionResult).copyOf(uiPosition);
            result.add(uiPositionResult);
        });

        // 是否查询关联的商品板块
        if (Objects.equals(YesOrNo.YES, uiPositionParam.getIncludeSection())) {
            ProductSectionParam productSectionParam = new ProductSectionParam();
            List<Long> positionIdList = result.stream().map(UiPositionResult::getId).collect(Collectors.toList());
            productSectionParam.setPositionIds(StringUtils.collectionToDelimitedString(positionIdList, ","));
            ResponseEntity sectionEntity = productSectionFeign.pages(productSectionParam);
            if (sectionEntity.getStatusCode().isError()) {
                return Tips.warn((String) sectionEntity.getBody());
            }

            Pages<ProductSection> productSectionPages = (Pages<ProductSection>) sectionEntity.getBody();

            if (Objects.nonNull(productSectionPages)) {
                List<ProductSection> productSectionList = productSectionPages.getArray();
                result.forEach(uiPositionResult -> productSectionList.forEach(productSection -> {
                    if (Objects.equals(uiPositionResult.getId(), productSection.getPositionId())) {
                        uiPositionResult.setProductSections(new ArrayList<>());
                        uiPositionResult.getProductSections().add(productSection);
                    }
                }));
            }
        }
        return Tips.<Pages<UiPositionResult>>empty().data(Pages.of(pages.getTotal(), result));
    }

}