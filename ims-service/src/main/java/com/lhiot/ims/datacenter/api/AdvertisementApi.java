package com.lhiot.ims.datacenter.api;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.swagger.ApiHideBodyProperty;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.datacenter.feign.AdvertisementFeign;
import com.lhiot.ims.datacenter.feign.ArticleFeign;
import com.lhiot.ims.datacenter.feign.ProductSectionFegin;
import com.lhiot.ims.datacenter.feign.ProductShelfFegin;
import com.lhiot.ims.datacenter.feign.entity.Advertisement;
import com.lhiot.ims.datacenter.feign.entity.Article;
import com.lhiot.ims.datacenter.feign.entity.ProductSection;
import com.lhiot.ims.datacenter.feign.entity.ProductShelf;
import com.lhiot.ims.datacenter.feign.model.AdvertisementParam;
import com.lhiot.ims.datacenter.feign.type.RelationType;
import com.lhiot.ims.healthygood.feign.customplan.CustomPlanFeign;
import com.lhiot.ims.healthygood.feign.customplan.CustomPlanSectionFeign;
import com.lhiot.ims.healthygood.feign.customplan.model.CustomPlanDetailResult;
import com.lhiot.ims.healthygood.feign.customplan.model.CustomPlanSectionResultAdmin;
import com.lhiot.ims.rbac.aspect.LogCollection;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * @author hufan created in 2018/11/22 16:13
 **/
@Api(description = "广告管理接口")
@Slf4j
@RestController
@RequestMapping("/advertisements")
public class AdvertisementApi {
    private final AdvertisementFeign advertisementFeign;
    private final CustomPlanFeign customPlanFeign;
    private final ArticleFeign articleFeign;
    private final ProductShelfFegin productShelfFegin;
    private final ProductSectionFegin productSectionFegin;
    private final CustomPlanSectionFeign customPlanSectionFeign;

    @Autowired
    public AdvertisementApi(AdvertisementFeign advertisementFeign, CustomPlanFeign customPlanFeign, ArticleFeign articleFeign, ProductShelfFegin productShelfFegin, ProductSectionFegin productSectionFegin, CustomPlanSectionFeign customPlanSectionFeign) {
        this.advertisementFeign = advertisementFeign;
        this.customPlanFeign = customPlanFeign;
        this.articleFeign = articleFeign;
        this.productShelfFegin = productShelfFegin;
        this.productSectionFegin = productSectionFegin;
        this.customPlanSectionFeign = customPlanSectionFeign;
    }

    @LogCollection
    @ApiOperation("添加广告")
    @PostMapping("/")
    @ApiHideBodyProperty({"id", "uiPosition", "createAt", "validityPeriod"})
    public ResponseEntity create(@RequestBody Advertisement advertisement) {
        log.debug("添加广告\t param:{}", advertisement);

        ResponseEntity entity = advertisementFeign.create(advertisement);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }

    @LogCollection
    @ApiOperation("修改广告")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "广告id", dataType = "Long", required = true)
    })
    @PutMapping("/{id}")
    @ApiHideBodyProperty({"uiPosition", "createAt", "validityPeriod"})
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody Advertisement advertisement) {
        log.debug("根据id修改广告\t id:{} param:{}", id, advertisement);

        ResponseEntity entity = advertisementFeign.update(id, advertisement);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }

    @ApiOperation(value = "根据Id查找广告", response = Advertisement.class)
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "广告id", dataType = "Long", required = true)
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        log.debug("根据Id查找广告\t id:{}", id);

        ResponseEntity<Advertisement> entity = advertisementFeign.findById(id);
        if (entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        Advertisement advertisement = entity.getBody();
        RelationType relationType = advertisement.getRelationType();
        switch (relationType) {
            case STORE_LIVE_TELECAST:
                break;
            case CUSTOM_PLAN:
                ResponseEntity<CustomPlanDetailResult> customPlanEntity = customPlanFeign.findById(Long.valueOf(advertisement.getAdvertiseRelation()));
                if (customPlanEntity.getStatusCode().isError()) {
                    return ResponseEntity.badRequest().body(customPlanEntity.getBody());
                }
                advertisement.setAdvertiseRelationText(customPlanEntity.getBody().getName());
                break;
            case EXTERNAL_LINKS:
                advertisement.setAdvertiseRelationText(advertisement.getAdvertiseRelation());
                break;
            case MORE_AMUSEMENT:
                break;
            case ARTICLE_DETAILS:
                ResponseEntity<Article> articleEntity = articleFeign.single(Long.valueOf(advertisement.getAdvertiseRelation()), false);
                if (articleEntity.getStatusCode().isError()) {
                    return ResponseEntity.badRequest().body(articleEntity.getBody());
                }
                advertisement.setAdvertiseRelationText(articleEntity.getBody().getTitle());
                break;
            case PRODUCT_DETAILS:
                ResponseEntity<ProductShelf> productShelfEntity = productShelfFegin.findById(Long.valueOf(advertisement.getAdvertiseRelation()), false);
                if (productShelfEntity.getStatusCode().isError()) {
                    return ResponseEntity.badRequest().body(productShelfEntity.getBody());
                }
                advertisement.setAdvertiseRelationText(productShelfEntity.getBody().getName());
                break;
            case PRODUCT_SECTION:
                ResponseEntity<ProductSection> productSectionEntity = productSectionFegin.findById(Long.valueOf(advertisement.getAdvertiseRelation()), false, null, false);
                if (productSectionEntity.getStatusCode().isError()) {
                    return ResponseEntity.badRequest().body(productSectionEntity.getBody());
                }
                advertisement.setAdvertiseRelationText(productSectionEntity.getBody().getSectionName());
                break;
            case CUSTOM_PLAN_SECTION:
                ResponseEntity<CustomPlanSectionResultAdmin> customPlanSectionEntity = customPlanSectionFeign.findById(Long.valueOf(advertisement.getAdvertiseRelation()), false);
                if (customPlanSectionEntity.getStatusCode().isError()) {
                    return ResponseEntity.badRequest().body(customPlanSectionEntity.getBody());
                }
                advertisement.setAdvertiseRelationText(customPlanSectionEntity.getBody().getSectionName());
                break;
            default:
                advertisement.setAdvertiseRelationText(advertisement.getAdvertiseRelation());
                break;
        }
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }

    @LogCollection
    @ApiOperation("根据广告Ids删除广告")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "ids", value = "多个广告Id以英文逗号分隔", dataType = "String", required = true)
    @DeleteMapping("/{ids}")
    public ResponseEntity batchDelete(@PathVariable("ids") String ids) {
        log.debug("根据广告Ids删除广告\t param:{}", ids);

        ResponseEntity entity = advertisementFeign.batchDelete(ids);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "根据条件分页查询广告信息列表", response = Advertisement.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "查询条件", dataType = "AdvertisementParam")
    @PostMapping("/pages")
    public ResponseEntity search(@RequestBody AdvertisementParam param) {
        log.debug("查询广告信息列表\t param:{}", param);

        ResponseEntity<Pages<Advertisement>> entity = advertisementFeign.pages(param);
        Pages<Advertisement> pages = entity.getBody();
        pages.getArray().forEach(advertisement -> {
            if (Objects.isNull(advertisement.getBeginAt()) && Objects.isNull(advertisement.getEndAt())) {
                advertisement.setValidityPeriod("永久有效");
            } else {
                String beginAt = new SimpleDateFormat("yyyy.MM.dd").format(advertisement.getBeginAt());
                String endAt = new SimpleDateFormat("yyyy.MM.dd").format(advertisement.getEndAt());
                advertisement.setValidityPeriod(beginAt + "-" + endAt);
            }
        });
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(pages);
    }

}