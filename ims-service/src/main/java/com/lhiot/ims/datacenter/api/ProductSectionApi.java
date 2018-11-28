package com.lhiot.ims.datacenter.api;

import com.leon.microx.util.Maps;
import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tips;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.datacenter.feign.ProductSectionFegin;
import com.lhiot.ims.datacenter.feign.ProductSectionRelationFegin;
import com.lhiot.ims.datacenter.feign.entity.ProductSection;
import com.lhiot.ims.datacenter.model.ProductSectionParam;
import com.lhiot.ims.datacenter.service.ProductSectionService;
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
@Api(description = "商品版块接口")
@Slf4j
@RestController
@RequestMapping("/product-sections")
public class ProductSectionApi {
    private final ProductSectionFegin productSectionFegin;
    private final ProductSectionRelationFegin productSectionRelationFegin;
    private final ProductSectionService productSectionService;

    @Autowired
    public ProductSectionApi(ProductSectionFegin productSectionFegin, ProductSectionRelationFegin productSectionRelationFegin, ProductSectionService productSectionService) {
        this.productSectionFegin = productSectionFegin;
        this.productSectionRelationFegin = productSectionRelationFegin;
        this.productSectionService = productSectionService;
    }

    @ApiOperation("添加商品版块(包括添加商品关联关系)")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "productSection", value = "商品版块信息", dataType = "ProductSection", required = true)
    @PostMapping("/")
    public ResponseEntity create(@RequestBody ProductSection productSection) {
        log.debug("添加商品版块\t param:{}", productSection);

        Tips result = productSectionService.create(productSection);
        return Objects.nonNull(result) ? ResponseEntity.ok(Maps.of("id", result.getMessage())) : ResponseEntity.badRequest().body("添加失败");
    }

    @ApiOperation("修改商品版块")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "商品版块id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = ApiParamType.BODY, name = "productSection", value = "productSection", dataType = "ProductSection", required = true)
    })
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody ProductSection productSection) {
        log.debug("根据id修改商品版块\t id:{} param:{}", id, productSection);

        ResponseEntity entity = productSectionFegin.update(id, productSection);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }

    @ApiOperation(value = "根据Id查找商品版块", response = ProductSection.class)
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "商品版块id", dataType = "Long", required = true)
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        log.debug("根据Id查找商品版块\t id:{}", id);

        ResponseEntity<ProductSection> entity = productSectionFegin.findById(id, true, null);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }

    @ApiOperation("根据商品Ids删除商品版块")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "ids", value = "多个商品Id以英文逗号分隔", dataType = "String", required = true)
    @DeleteMapping("/{ids}")
    public ResponseEntity batchDelete(@PathVariable("ids") String ids) {
        log.debug("根据商品Ids删除商品版块\t param:{}", ids);

        ResponseEntity entity = productSectionFegin.batchDelete(ids);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "根据条件分页查询商品版块信息列表", response = ProductSection.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "查询条件", dataType = "ProductSectionParam")
    @PostMapping("/pages")
    public ResponseEntity search(@RequestBody ProductSectionParam param) {
        log.debug("查询商品版块信息列表\t param:{}", param);

        ResponseEntity<Pages<ProductSection>> entity = productSectionFegin.pages(param);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }

    @ApiOperation("根据关联id删除商品和板块关联")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "relationId", value = "商品板块关联id", dataType = "Long", required = true)
    @DeleteMapping("relation/{relationId}")
    public ResponseEntity updateRelation(@PathVariable("relationId") Long relationId) {
        log.debug("根据关联id删除商品和板块关联\t param:{}", relationId);

        ResponseEntity entity = productSectionRelationFegin.delete(relationId);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.noContent().build();
    }

    @ApiOperation("根据板块id和商品ids修改商品和板块关联")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.QUERY, name = "sectionId", value = "商品板块id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = ApiParamType.QUERY, name = "productIds", value = "商品板块关联id", dataType = "String", required = true)
    })
    @PutMapping("/relation/batch")
    public ResponseEntity deleteRelation(@RequestParam("sectionId") Long sectionId, @RequestParam("productIds") String productIds) {
        log.debug("根据板块id和商品ids修改商品和板块关联\t param:{}", sectionId, productIds);

        Tips tips = productSectionService.updateBatch(sectionId, productIds);
        return tips.err() ? ResponseEntity.badRequest().body(tips.getMessage()) : ResponseEntity.ok().body(tips.getMessage());
    }

}