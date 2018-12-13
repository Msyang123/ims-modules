package com.lhiot.ims.datacenter.api;

import com.leon.microx.util.Maps;
import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tips;
import com.leon.microx.web.swagger.ApiHideBodyProperty;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.datacenter.feign.ProductSectionFegin;
import com.lhiot.ims.datacenter.feign.ProductSectionRelationFegin;
import com.lhiot.ims.datacenter.feign.entity.ProductSection;
import com.lhiot.ims.datacenter.feign.entity.ProductSectionRelation;
import com.lhiot.ims.datacenter.feign.model.ProductSectionParam;
import com.lhiot.ims.datacenter.service.ProductSectionService;
import com.lhiot.ims.rbac.aspect.LogCollection;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hufan created in 2018/11/21 16:57
 **/
@Api(description = "商品版块接口")
@Slf4j
@RestController
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

    @LogCollection
    @ApiOperation("添加商品版块(包括添加商品关联关系)")
    @PostMapping("/product-sections/")
    @ApiHideBodyProperty({"id", "createAt"})
    public ResponseEntity create(@Valid @RequestBody ProductSection productSection) {
        log.debug("添加商品版块\t param:{}", productSection);

        Tips tips = productSectionService.create(productSection);
        return tips.err() ? ResponseEntity.badRequest().body("添加失败") : ResponseEntity.created(URI.create("/product-sections/" + tips.getMessage())).body(Maps.of("id", tips.getMessage()));
    }

    @LogCollection
    @ApiOperation("修改商品版块")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "商品版块id", dataType = "Long", required = true)
    @PutMapping("/product-sections/{id}")
    @ApiHideBodyProperty({"id", "createAt"})
    public ResponseEntity update(@PathVariable("id") Long id, @Valid @RequestBody ProductSection productSection) {
        log.debug("根据id修改商品版块\t id:{} param:{}", id, productSection);

        ResponseEntity entity = productSectionFegin.update(id, productSection);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }

    @ApiOperation(value = "根据Id查找商品版块", response = ProductSection.class)
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "商品版块id", dataType = "Long", required = true)
    @GetMapping("/product-sections/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        log.debug("根据Id查找商品版块\t id:{}", id);

        ResponseEntity<ProductSection> entity = productSectionFegin.findById(id, true, null, true);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }

    @LogCollection
    @ApiOperation("根据商品Ids删除商品版块")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "ids", value = "多个商品Id以英文逗号分隔", dataType = "String", required = true)
    @DeleteMapping("/product-sections/{ids}")
    public ResponseEntity batchDelete(@PathVariable("ids") String ids) {
        log.debug("根据商品Ids删除商品版块\t param:{}", ids);

        ResponseEntity entity = productSectionFegin.batchDelete(ids);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "根据条件分页查询商品版块信息列表", response = ProductSection.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "查询条件", dataType = "ProductSectionParam")
    @PostMapping("/product-sections/pages")
    public ResponseEntity search(@RequestBody ProductSectionParam param) {
        log.debug("查询商品版块信息列表\t param:{}", param);

        ResponseEntity<Pages<ProductSection>> entity = productSectionFegin.pages(param);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }

    @Deprecated
    @LogCollection
    @ApiOperation("根据关联id删除商品和板块关联")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "relationId", value = "商品板块关联id", dataType = "Long", required = true)
    @DeleteMapping("/product-sections/relation/{relationId}")
    public ResponseEntity updateRelation(@PathVariable("relationId") Long relationId) {
        log.debug("根据关联id删除商品和板块关联\t param:{}", relationId);

        ResponseEntity entity = productSectionRelationFegin.delete(relationId);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.noContent().build();
    }

    @ApiOperation("批量删除版块与商品上架关系")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.QUERY, name = "sectionId", value = "商品版块Id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = ApiParamType.QUERY, name = "shelfIds", value = "多个商品上架Id以英文逗号分隔,为空则删除此版块所有上架关系", dataType = "String")
    })
    @DeleteMapping("/product-sections/relation/batches")
    public ResponseEntity deleteBatch(@RequestParam("sectionId") Long sectionId, @RequestParam(value = "shelfIds", required = false) String shelfIds) {
        log.debug("批量删除版块与商品上架关系\t sectionId:{},shelfIds:{} ", sectionId, shelfIds);

        ResponseEntity entity = productSectionRelationFegin.deleteBatch(sectionId, shelfIds);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.noContent().build();
    }

    @LogCollection
    @ApiOperation("新增商品和板块的关联")
    @PostMapping("/product-sections/relation")
    public ResponseEntity addRelation(@RequestBody ProductSectionRelation productSectionRelation) {
        log.debug("根据关联id删除商品和板块关联\t param:{}", productSectionRelation);

        ResponseEntity entity = productSectionRelationFegin.create(productSectionRelation);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok().build();
       /* String location = entity.getHeaders().getLocation().toString();
        Long relationId = Long.valueOf(location.substring(location.lastIndexOf('/') + 1));
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.created(URI.create("/product-sections/relation/" + relationId)).body(Maps.of("id", relationId));*/
    }

    @Deprecated
    @ApiOperation("根据板块id和商品ids修改商品和板块关联")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.QUERY, name = "sectionId", value = "商品板块id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = ApiParamType.QUERY, name = "productIds", value = "商品板块关联id", dataType = "String", required = true)
    })
    @PutMapping("/product-sections/relation/batch")
    public ResponseEntity deleteRelation(@RequestParam("sectionId") Long sectionId, @RequestParam("productIds") String productIds) {
        log.debug("根据板块id和商品ids修改商品和板块关联\t param:{}", sectionId, productIds);

        Tips tips = productSectionService.updateBatch(sectionId, productIds);
        return tips.err() ? ResponseEntity.badRequest().body(tips.getMessage()) : ResponseEntity.ok().body(tips.getMessage());
    }

    @ApiOperation(value = "查询去重的商品板块集合", response = String.class, responseContainer = "List")
    @GetMapping("/product-sections/")
    public ResponseEntity list() {
        log.debug("查询去重的商品板块集合\t");

        ProductSectionParam productSectionParam = new ProductSectionParam();
        ResponseEntity<Pages<ProductSection>> entity = productSectionFegin.pages(productSectionParam);
        if (entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        List<ProductSection> productSectionList = entity.getBody().getArray();
        List<String> sectionNameList = productSectionList.stream().map(ProductSection::getSectionName).distinct().collect(Collectors.toList());
        return ResponseEntity.ok(sectionNameList);
    }
}