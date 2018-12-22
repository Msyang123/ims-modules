package com.lhiot.ims.datacenter.api;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.swagger.ApiHideBodyProperty;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.datacenter.feign.AdvertisementFeign;
import com.lhiot.ims.datacenter.feign.ArticleFeign;
import com.lhiot.ims.datacenter.feign.ProductSectionFeign;
import com.lhiot.ims.datacenter.feign.ProductShelfFeign;
import com.lhiot.ims.datacenter.feign.entity.Advertisement;
import com.lhiot.ims.datacenter.feign.entity.Article;
import com.lhiot.ims.datacenter.feign.entity.ProductSection;
import com.lhiot.ims.datacenter.feign.entity.ProductShelf;
import com.lhiot.ims.datacenter.feign.model.AdvertisementParam;
import com.lhiot.ims.datacenter.feign.type.RelationType;
import com.lhiot.ims.healthygood.feign.customplan.CustomPlanFeign;
import com.lhiot.ims.healthygood.feign.customplan.CustomPlanSectionFeign;
import com.lhiot.ims.healthygood.feign.customplan.entity.CustomPlanSection;
import com.lhiot.ims.healthygood.feign.customplan.model.CustomPlanDetailResult;
import com.lhiot.ims.rbac.aspect.LogCollection;
import com.lhiot.util.FeginResponseTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
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
    private final ProductShelfFeign productShelfFeign;
    private final ProductSectionFeign productSectionFeign;
    private final CustomPlanSectionFeign customPlanSectionFeign;

    @Autowired
    public AdvertisementApi(AdvertisementFeign advertisementFeign, CustomPlanFeign customPlanFeign, ArticleFeign articleFeign, ProductShelfFeign productShelfFeign, ProductSectionFeign productSectionFeign, CustomPlanSectionFeign customPlanSectionFeign) {
        this.advertisementFeign = advertisementFeign;
        this.customPlanFeign = customPlanFeign;
        this.articleFeign = articleFeign;
        this.productShelfFeign = productShelfFeign;
        this.productSectionFeign = productSectionFeign;
        this.customPlanSectionFeign = customPlanSectionFeign;
    }

    @LogCollection
    @ApiOperation("添加广告")
    @PostMapping("/")
    @ApiHideBodyProperty({"id", "uiPosition", "createAt", "validityPeriod", "articleList"})
    public ResponseEntity create(@RequestBody Advertisement advertisement) {
        log.debug("添加广告\t param:{}", advertisement);

        ResponseEntity entity = advertisementFeign.create(advertisement);
        return FeginResponseTools.convertNoramlResponse(entity);
    }

    @LogCollection
    @ApiOperation("修改广告")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "广告id", dataType = "Long", required = true)
    @PutMapping("/{id}")
    @ApiHideBodyProperty({"uiPosition", "createAt", "validityPeriod", "articleList"})
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody Advertisement advertisement) {
        log.debug("根据id修改广告\t id:{} param:{}", id, advertisement);

        ResponseEntity entity = advertisementFeign.update(id, advertisement);
        return FeginResponseTools.convertUpdateResponse(entity);
    }

    @ApiOperation(value = "根据Id查找广告", response = Advertisement.class)
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "文章版块Id", dataType = "Long", required = true)
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        log.debug("根据Id查找广告\t id:{}", id);

        ResponseEntity entity = advertisementFeign.findById(id);
        if (entity.getStatusCode().isError() || Objects.isNull(entity.getBody())) {
            return ResponseEntity.badRequest().body(Objects.isNull(entity.getBody()) ? "基础服务调用失败" : entity.getBody());
        }
        Advertisement advertisement = (Advertisement) entity.getBody();
        RelationType relationType = advertisement.getRelationType();
        switch (relationType) {
            case STORE_LIVE_TELECAST:
                break;
            case CUSTOM_PLAN:
                ResponseEntity customPlanEntity = customPlanFeign.findById(Long.valueOf(advertisement.getAdvertiseRelation()));
                if (customPlanEntity.getStatusCode().isError()) {
                    return ResponseEntity.badRequest().body(customPlanEntity.getBody());
                }
                CustomPlanDetailResult customPlanDetailResult = (CustomPlanDetailResult) customPlanEntity.getBody();
                advertisement.setAdvertiseRelationText(Objects.nonNull(customPlanDetailResult) ? customPlanDetailResult.getName() : null);
                break;
            case EXTERNAL_LINKS:
                advertisement.setAdvertiseRelationText(advertisement.getAdvertiseRelation());
                break;
            case MORE_AMUSEMENT:
                break;
            case ARTICLE_DETAILS:
                ResponseEntity articleEntity = articleFeign.single(Long.valueOf(advertisement.getAdvertiseRelation()), false);
                if (articleEntity.getStatusCode().isError()) {
                    return ResponseEntity.badRequest().body(articleEntity.getBody());
                }
                Article article = (Article) articleEntity.getBody();
                advertisement.setAdvertiseRelationText(Objects.nonNull(article) ? article.getTitle() : null);
                break;
            case PRODUCT_DETAILS:
                ResponseEntity productShelfEntity = productShelfFeign.findById(Long.valueOf(advertisement.getAdvertiseRelation()), false);
                if (productShelfEntity.getStatusCode().isError()) {
                    return ResponseEntity.badRequest().body(productShelfEntity.getBody());
                }
                ProductShelf productShelf = (ProductShelf) productShelfEntity.getBody();
                advertisement.setAdvertiseRelationText(Objects.nonNull(productShelf) ? productShelf.getName() : null);
                break;
            case PRODUCT_SECTION:
                ResponseEntity productSectionEntity = productSectionFeign.findById(Long.valueOf(advertisement.getAdvertiseRelation()), false, null, false);
                if (productSectionEntity.getStatusCode().isError()) {
                    return ResponseEntity.badRequest().body(productSectionEntity.getBody());
                }
                ProductSection productSection = (ProductSection) productSectionEntity.getBody();
                advertisement.setAdvertiseRelationText(Objects.nonNull(productSection) ? productSection.getSectionName() : null);
                break;
            case CUSTOM_PLAN_SECTION:
                ResponseEntity customPlanSectionEntity = customPlanSectionFeign.findById(Long.valueOf(advertisement.getAdvertiseRelation()), false);
                if (customPlanSectionEntity.getStatusCode().isError()) {
                    return ResponseEntity.badRequest().body(customPlanSectionEntity.getBody());
                }
                CustomPlanSection customPlanSection = (CustomPlanSection) customPlanSectionEntity.getBody();
                advertisement.setAdvertiseRelationText(Objects.nonNull(customPlanSection) ? customPlanSection.getSectionName() : null);
                break;
            default:
                advertisement.setAdvertiseRelationText(advertisement.getAdvertiseRelation());
                break;
        }
        return FeginResponseTools.convertNoramlResponse(entity);
    }

    @LogCollection
    @ApiOperation("根据广告Ids删除广告")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "ids", value = "多个广告Id以英文逗号分隔", dataType = "String", required = true)
    @DeleteMapping("/{ids}")
    public ResponseEntity batchDelete(@PathVariable("ids") String ids) {
        log.debug("根据广告Ids删除广告\t param:{}", ids);

        ResponseEntity entity = advertisementFeign.batchDelete(ids);
        return FeginResponseTools.convertDeleteResponse(entity);
    }

    @ApiOperation(value = "根据条件分页查询广告信息列表", response = Advertisement.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "查询条件", dataType = "AdvertisementParam")
    @PostMapping("/pages")
    public ResponseEntity search(@RequestBody AdvertisementParam param) {
        log.debug("查询广告信息列表\t param:{}", param);

        ResponseEntity entity = advertisementFeign.pages(param);
        if (Objects.isNull(entity.getBody()) || entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(Objects.isNull(entity.getBody()) ? "服务内部错误" : entity.getBody());
        }
        Pages<Advertisement> pages = (Pages<Advertisement>) entity.getBody();
        if (!pages.getArray().isEmpty() || !CollectionUtils.isEmpty(pages.getArray())) {
            pages.getArray().forEach(advertisement -> {
                if (Objects.isNull(advertisement.getBeginAt()) && Objects.isNull(advertisement.getEndAt())) {
                    advertisement.setValidityPeriod("永久有效");
                } else {
                    String beginAt = Objects.nonNull(advertisement.getBeginAt()) ? new SimpleDateFormat("yyyy.MM.dd").format(advertisement.getBeginAt()) : "";
                    String endAt = Objects.nonNull(advertisement.getEndAt()) ? new SimpleDateFormat("yyyy.MM.dd").format(advertisement.getEndAt()) : "";
                    advertisement.setValidityPeriod(beginAt + "-" + endAt);
                }
            });
        }
        return ResponseEntity.ok(pages);
    }

}