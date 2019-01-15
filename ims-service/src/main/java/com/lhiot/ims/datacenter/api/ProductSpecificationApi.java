package com.lhiot.ims.datacenter.api;

import com.leon.microx.web.result.Tips;
import com.leon.microx.web.swagger.ApiHideBodyProperty;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.datacenter.feign.ProductSpecificationFeign;
import com.lhiot.ims.datacenter.feign.entity.ProductSpecification;
import com.lhiot.ims.datacenter.feign.model.ProductSpecificationParam;
import com.lhiot.ims.datacenter.service.ProductSpecificationService;
import com.lhiot.ims.rbac.aspect.LogCollection;
import com.lhiot.util.FeginResponseTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2018/11/21 16:57
 **/
@Api(description = "商品规格接口")
@Slf4j
@RestController
@RequestMapping("/product-specifications")
public class ProductSpecificationApi {
    private final ProductSpecificationFeign productSpecificationFeign;
    private final ProductSpecificationService productSpecificationService;

    @Autowired
    public ProductSpecificationApi(ProductSpecificationFeign productSpecificationFeign, ProductSpecificationService productSpecificationService) {
        this.productSpecificationFeign = productSpecificationFeign;
        this.productSpecificationService = productSpecificationService;
    }

    @LogCollection
    @ApiOperation("添加商品规格")
    @PostMapping("/")
    @ApiHideBodyProperty({"id", "createAt", "specificationInfo", "product"})
    public ResponseEntity create(@RequestBody ProductSpecification productSpecification) {
        log.debug("添加商品规格\t param:{}", productSpecification);

        ResponseEntity entity = productSpecificationFeign.create(productSpecification);
        return FeginResponseTools.convertCreateResponse(entity);
    }

    @LogCollection
    @ApiOperation("修改商品规格")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "商品规格id", dataType = "Long", required = true)
    @PutMapping("/{id}")
    @ApiHideBodyProperty({"id", "createAt", "specificationInfo", "product"})
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody ProductSpecification productSpecification) {
        log.debug("根据id修改商品规格\t id:{} param:{}", id, productSpecification);

        ResponseEntity entity = productSpecificationFeign.update(id, productSpecification);
        return FeginResponseTools.convertUpdateResponse(entity);
    }

    @ApiOperation(value = "根据Id查找商品规格", response = ProductSpecification.class)
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "商品规格id", dataType = "Long", required = true)
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        log.debug("根据Id查找商品规格\t id:{}", id);

        ResponseEntity entity = productSpecificationFeign.findById(id);
        return FeginResponseTools.convertNoramlResponse(entity);
    }

    @LogCollection
    @ApiOperation("根据商品规格Ids删除商品规格")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "ids", value = "多个商品规格Id以英文逗号分隔", dataType = "String", required = true)
    @DeleteMapping("/{ids}")
    public ResponseEntity batchDelete(@PathVariable("ids") String ids) {
        log.debug("根据商品规格Ids删除商品规格\t param:{}", ids);

        ResponseEntity entity = productSpecificationFeign.batchDelete(ids);
        return FeginResponseTools.convertDeleteResponse(entity);
    }

    @ApiOperation(value = "根据条件分页查询商品规格信息列表(传值produtId/输入条码或品名)", response = ProductSpecification.class, responseContainer = "Set")
    @PostMapping("/pages")
    @ApiHideBodyProperty("inventorySpecification")
    public ResponseEntity search(@RequestBody ProductSpecificationParam param) {
        log.debug("查询商品规格信息列表\t param:{}", param);

        Tips tips = productSpecificationService.pages(param);
        return tips.err() ? ResponseEntity.badRequest().body(tips.getMessage()) : ResponseEntity.ok(tips.getData());
    }

    @ApiOperation(value = "查询所有基础规格单位列表", response = String.class, responseContainer = "List")
    @GetMapping("/units")
    public ResponseEntity findList() {
        log.debug("查询所有基础规格单位");

        Tips tips = productSpecificationService.getUnits();
        return tips.err() ? ResponseEntity.badRequest().body(tips.getMessage()) : ResponseEntity.ok(tips.getData());
    }
}