package com.lhiot.ims.datacenter.service;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tips;
import com.lhiot.ims.datacenter.feign.ProductSectionFegin;
import com.lhiot.ims.datacenter.feign.UiPositionFeign;
import com.lhiot.ims.datacenter.feign.entity.ProductSection;
import com.lhiot.ims.datacenter.feign.entity.UiPosition;
import com.lhiot.ims.datacenter.feign.model.ProductSectionParam;
import com.lhiot.ims.datacenter.feign.model.UiPositionParam;
import com.lhiot.ims.datacenter.feign.model.UiPositionResult;
import com.lhiot.ims.datacenter.feign.type.ApplicationType;
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

    public UiPositionService(UiPositionFeign uiPositionFeign, ProductSectionFegin productSectionFegin) {
        this.uiPositionFeign = uiPositionFeign;
        this.productSectionFegin = productSectionFegin;
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