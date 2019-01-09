package com.lhiot.ims.datacenter.api;

import com.leon.microx.predefine.OnOff;
import com.leon.microx.util.Maps;
import com.leon.microx.web.result.Tips;
import com.leon.microx.web.swagger.ApiHideBodyProperty;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.datacenter.feign.ProductShelfFeign;
import com.lhiot.ims.datacenter.feign.entity.ProductShelf;
import com.lhiot.ims.datacenter.feign.model.ProductShelfParam;
import com.lhiot.ims.datacenter.feign.type.ApplicationType;
import com.lhiot.ims.datacenter.service.ProductShelfService;
import com.lhiot.ims.healthygood.feign.customplan.CustomPlanFeign;
import com.lhiot.ims.healthygood.feign.customplan.entity.CustomPlan;
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

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author hufan created in 2018/11/21 16:57
 **/
@Api(description = "商品上架接口")
@Slf4j
@RestController
@RequestMapping("/product-shelves")
public class ProductShelfApi {
    private final ProductShelfFeign productShelfFeign;
    private final ProductShelfService productShelfService;
    private final CustomPlanFeign customPlanFeign;

    @Autowired
    public ProductShelfApi(ProductShelfFeign productShelfFeign, ProductShelfService productShelfService, CustomPlanFeign customPlanFeign) {
        this.productShelfFeign = productShelfFeign;
        this.productShelfService = productShelfService;
        this.customPlanFeign = customPlanFeign;
    }

    @LogCollection
    @ApiOperation("添加商品上架")
    @PostMapping("/")
    @ApiHideBodyProperty({"id","productSpecification","createAt","productName","shelfSpecification","barcode","specificationInfo"})
    public ResponseEntity create(@Valid @RequestBody ProductShelf productShelf) {
        log.debug("添加商品上架\t param:{}", productShelf);

        Tips tips = productShelfService.create(productShelf);
        return tips.err() ? ResponseEntity.badRequest().body(tips.getMessage()) : ResponseEntity.created(URI.create("/product-shelves/" + tips.getMessage())).body(Maps.of("id", tips.getMessage()));
    }

    @LogCollection
    @ApiOperation("修改商品上架")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "商品上架id", dataType = "Long", required = true)
    @PutMapping("/{id}")
    @ApiHideBodyProperty({"id","productSpecification","applicationType","createAt","productName","shelfSpecification","barcode","specificationInfo","sectionIds"})
    public ResponseEntity update(@PathVariable("id") Long id, @Valid @RequestBody ProductShelf productShelf) {
        log.debug("根据id修改商品上架\t id:{} param:{}", id, productShelf);

        // 根据上架id查询商品信息
        ResponseEntity entity = productShelfFeign.findById(id, false);
        if (entity.getStatusCode().isError() || Objects.isNull(entity.getBody())) {
            return ResponseEntity.badRequest().body("基础数据服务调用失败");
        }
        ProductShelf beforeProductShelf = (ProductShelf) entity.getBody();
        // 应用类型是否为和色果膳或者为空
        if (Objects.equals(ApplicationType.HEALTH_GOOD, productShelf.getApplicationType()) || Objects.isNull(productShelf.getApplicationType())) {
            // 如果状态改完下架则先查询该上架id是否关联了定制计划商品
            if (Objects.equals(OnOff.OFF, productShelf.getShelfStatus()) && Objects.equals(OnOff.ON, beforeProductShelf.getShelfStatus())){
                ResponseEntity customPlanEntity = customPlanFeign.findByShelfId(id);
                if (customPlanEntity.getStatusCode().isError()){
                    return ResponseEntity.badRequest().body("和色果膳服务调用失败");
                }
                List<CustomPlan> customPlanList = (List<CustomPlan>) customPlanEntity.getBody();
                if (!CollectionUtils.isEmpty(customPlanList)){
                    List<String> customPlanIdList = customPlanList.stream().map(CustomPlan::getName).collect(Collectors.toList());
                    return ResponseEntity.badRequest().body("下架修改失败,该商品已关联定制计划:" + customPlanIdList);
                }
            }
        }
        Tips tips = productShelfService.update(id, productShelf);
        return tips.err() ? ResponseEntity.badRequest().body(tips.getMessage()) : ResponseEntity.ok(tips.getMessage());
    }

    @ApiOperation(value = "根据Id查找商品上架", response = ProductShelf.class)
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "商品上架id", dataType = "Long", required = true)
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        log.debug("根据Id查找商品上架\t id:{}", id);

        Tips tips = productShelfService.findById(id);
        return tips.err() ?  ResponseEntity.badRequest().body(tips.getMessage()): ResponseEntity.ok(tips.getData());
    }

    @LogCollection
    @ApiOperation("根据商品上架Ids删除商品上架")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "ids", value = "多个商品上架Id以英文逗号分隔", dataType = "String", required = true)
    @DeleteMapping("/{ids}")
    public ResponseEntity batchDelete(@PathVariable("ids") String ids) {
        log.debug("根据商品上架Ids删除商品上架\t param:{}", ids);

        ResponseEntity entity = productShelfFeign.batchDelete(ids);
        return FeginResponseTools.convertDeleteResponse(entity);
    }

    @ApiOperation(value = "根据条件分页查询商品上架信息列表", response = ProductShelf.class, responseContainer = "Set")
    @PostMapping("/pages")
    public ResponseEntity search(@RequestBody ProductShelfParam param) {
        log.debug("查询商品上架信息列表\t param:{}", param);

        Tips tips = productShelfService.pages(param);
        return tips.err() ? ResponseEntity.badRequest().body(tips.getMessage()) : ResponseEntity.ok(tips.getData());
    }

}