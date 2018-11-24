package com.lhiot.ims.datacenter.api;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tips;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.datacenter.feign.ProductSpecificationFegin;
import com.lhiot.ims.datacenter.feign.entity.ProductSpecification;
import com.lhiot.ims.datacenter.model.ProductSpecificationParam;
import com.lhiot.ims.datacenter.service.ProductSpecificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author hufan created in 2018/11/21 16:57
 **/
@Api(description = "商品规格接口")
@Slf4j
@RestController
@RequestMapping("/product-specifications")
public class ProductSpecificationApi {
    private final ProductSpecificationFegin productSpecificationFegin;
    private final ProductSpecificationService productSpecificationService;

    @Autowired
    public ProductSpecificationApi(ProductSpecificationFegin productSpecificationFegin, ProductSpecificationService productSpecificationService) {
        this.productSpecificationFegin = productSpecificationFegin;
        this.productSpecificationService = productSpecificationService;
    }

    @ApiOperation("添加商品规格")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "productSpecification", value = "商品规格信息", dataType = "ProductSpecification", required = true)
    @PostMapping("/")
    public ResponseEntity create(@RequestBody ProductSpecification productSpecification) {
        log.debug("添加商品规格\t param:{}", productSpecification);

        ResponseEntity entity = productSpecificationFegin.create(productSpecification);
        if (Objects.isNull(entity) || entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        return ResponseEntity.ok(entity.getBody());
    }

    @ApiOperation("修改商品规格")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "商品规格id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = ApiParamType.BODY, name = "productSpecification", value = "ProductSpecification", dataType = "ProductSpecification", required = true)
    })
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody ProductSpecification productSpecification) {
        log.debug("根据id修改商品规格\t id:{} param:{}", id, productSpecification);

        ResponseEntity entity = productSpecificationFegin.update(id, productSpecification);
        if (Objects.isNull(entity) || entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        return ResponseEntity.ok(entity.getBody());
    }

    @ApiOperation(value = "根据Id查找商品规格", response = ProductSpecification.class)
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "商品规格id", dataType = "Long", required = true)
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        log.debug("根据Id查找商品规格\t id:{}", id);

        ResponseEntity<ProductSpecification> entity = productSpecificationFegin.findById(id);
        if (Objects.isNull(entity) || entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        return ResponseEntity.ok(entity.getBody());
    }

    @ApiOperation("根据商品规格Ids删除商品规格")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "ids", value = "多个商品规格Id以英文逗号分隔", dataType = "String", required = true)
    @DeleteMapping("/{ids}")
    public ResponseEntity batchDelete(@PathVariable("ids") String ids) {
        log.debug("根据商品规格Ids删除商品规格\t param:{}", ids);

        ResponseEntity entity = productSpecificationFegin.batchDelete(ids);
        if (Objects.isNull(entity) || entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        return ResponseEntity.ok(entity.getBody());
    }

    @ApiOperation(value = "根据条件分页查询商品规格信息列表(传值produtId)", response = ProductSpecification.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "查询条件", dataType = "ProductSpecificationParam")
    @PostMapping("/pages")
    public ResponseEntity search(@RequestBody ProductSpecificationParam param) {
        log.debug("查询商品规格信息列表\t param:{}", param);

        // 设置默认页数和行数
        int page = Objects.nonNull(param.getPage()) ? param.getPage() : 1;
        int rows = Objects.nonNull(param.getRows()) ? param.getRows() : 10;
        param.setPage(page);
        param.setRows(rows);

        ResponseEntity<Pages<ProductSpecification>> entity = productSpecificationFegin.pages(param);
        if (Objects.isNull(entity) || entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        return ResponseEntity.ok(entity.getBody());
    }

    @ApiOperation(value = "查询所有基础规格单位列表", response = String.class, responseContainer = "List")
    @GetMapping("/units")
    public ResponseEntity findList() {
        log.debug("查询所有基础规格单位");

        Tips tips = productSpecificationService.getUnits();
        if (tips.err()) {
            return ResponseEntity.badRequest().body(tips.getMessage());
        }
        return ResponseEntity.ok(tips.getData());
    }

    @ApiOperation(value = "商品规格导出", response = ProductSpecification.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "查询条件", dataType = "ProductSpecificationParam")
    @PostMapping("/export")
    public ResponseEntity export(@RequestBody ProductSpecificationParam param) {
        log.debug("商品规格导出\t param:{}", param);

        ResponseEntity<Pages<ProductSpecification>> entity = productSpecificationFegin.pages(param);
        if (Objects.isNull(entity) || entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        return ResponseEntity.ok(entity.getBody());
    }

}