package com.lhiot.ims.datacenter.service;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tips;
import com.lhiot.ims.datacenter.feign.AdvertisementFeign;
import com.lhiot.ims.datacenter.feign.ProductSectionFegin;
import com.lhiot.ims.datacenter.feign.UiPositionFeign;
import com.lhiot.ims.datacenter.feign.entity.Advertisement;
import com.lhiot.ims.datacenter.feign.entity.ProductSection;
import com.lhiot.ims.datacenter.feign.entity.UiPosition;
import com.lhiot.ims.datacenter.feign.model.*;
import com.lhiot.ims.datacenter.feign.type.ApplicationType;
import com.lhiot.ims.datacenter.feign.type.PositionType;
import com.lhiot.ims.datacenter.type.YesOrNo;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author hufan created in 2018/12/4 18:26
 **/
@Service
@Transactional
public class UiPositionService {
    private final UiPositionFeign uiPositionFeign;
    private final ProductSectionFegin productSectionFegin;
    private final AdvertisementFeign advertisementFeign;

    public UiPositionService(UiPositionFeign uiPositionFeign, ProductSectionFegin productSectionFegin, AdvertisementFeign advertisementFeign) {
        this.uiPositionFeign = uiPositionFeign;
        this.productSectionFegin = productSectionFegin;
        this.advertisementFeign = advertisementFeign;
    }

    public Tips findById(Long id) {
        UiPositionDetail uiPositionDetail = new UiPositionDetail();
        ResponseEntity entity = uiPositionFeign.findById(id);
        if (entity.getStatusCode().isError()) {
            return Tips.warn(entity.getBody().toString());
        }
        UiPosition uiPosition = (UiPosition) entity.getBody();
        BeanUtils.copyProperties(uiPosition, uiPositionDetail);
        PositionType positionType = uiPosition.getPositionType();
        Long uiPositionId = uiPosition.getId();
        switch (positionType) {
            case ARTICLE:
                break;
            case PRODUCT:
                ProductSectionParam productSectionParam = new ProductSectionParam();
                productSectionParam.setPositionId(uiPositionId);
                productSectionParam.setIncludeShelves(true);
                ResponseEntity productSectionEntity = productSectionFegin.pages(productSectionParam);
                if (productSectionEntity.getStatusCode().isError()) {
                    return Tips.warn(productSectionEntity.getBody().toString());
                }
                Pages<ProductSection> productSectionPages = (Pages<ProductSection>) productSectionEntity.getBody();
                List<ProductSection> productSectionList = productSectionPages.getArray();
                if (!productSectionList.isEmpty() && productSectionList.size() > 0) {
                    uiPositionDetail.setProductSectionList(productSectionList);
                }
                break;
            case ADVERTISEMENT:
                AdvertisementParam advertisementParam = new AdvertisementParam();
                advertisementParam.setPositionId(uiPositionId);
                ResponseEntity advertisementEntity = advertisementFeign.pages(advertisementParam);
                if (advertisementEntity.getStatusCode().isError()) {
                    return Tips.warn(advertisementEntity.getBody().toString());
                }
                Pages<Advertisement> advertisementPages = (Pages<Advertisement>) advertisementEntity.getBody();
                List<Advertisement> advertisementList = advertisementPages.getArray();
                if (!advertisementList.isEmpty() && advertisementList.size() > 0) {
                    uiPositionDetail.setAdvertisementList(advertisementList);
                }
                break;
            default:
                break;
        }

        Tips tips = new Tips();
        tips.setData(uiPositionDetail);
        return tips;

    }

    public Tips search(UiPositionParam uiPositionParam) {
        Tips tips = new Tips();
        List<UiPositionResult> result = new ArrayList<>();

        // 查询鲜果师的所有位置板块
        uiPositionParam.setApplicationType(ApplicationType.HEALTH_GOOD);
        ResponseEntity entity = uiPositionFeign.pages(uiPositionParam);
        if (entity.getStatusCode().isError()) {
            return Tips.warn(entity.getBody().toString());
        }
        Pages<UiPosition> pages = (Pages) entity.getBody();
        List<UiPosition> uiPositionList = pages.getArray();
        uiPositionList.forEach(uiPosition -> {
            UiPositionResult uiPositionResult = new UiPositionResult();
            BeanUtils.copyProperties(uiPosition, uiPositionResult);
            result.add(uiPositionResult);
        });
        // 是否查询关联的商品板块
        if (Objects.equals(YesOrNo.YES, uiPositionParam.getIncludeSection())) {
            ProductSectionParam productSectionParam = new ProductSectionParam();
            result.forEach(item -> {
                productSectionParam.setPositionId(item.getId());
                ResponseEntity sectionEntity = productSectionFegin.pages(productSectionParam);
                if (sectionEntity.getStatusCode().isError()) {
//                    return Tips.warn(sectionEntity.getBody().toString());
                    return;
                }
                Pages<ProductSection> productSectionPages = (Pages<ProductSection>) sectionEntity.getBody();
                List<ProductSection> productSectionList = productSectionPages.getArray();
                item.setProductSections(productSectionList);
            });
        }
        tips.setData(result);
        return tips;
    }

}