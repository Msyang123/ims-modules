package com.lhiot.ims.datacenter.api;

import com.leon.microx.util.Maps;
import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tips;
import com.leon.microx.web.result.Tuple;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.datacenter.feign.ProductCategoryFeign;
import com.lhiot.ims.datacenter.feign.entity.ProductCategory;
import com.lhiot.ims.datacenter.feign.model.ProductCategoryParam;
import com.lhiot.ims.datacenter.service.ProductCategoryService;
import com.lhiot.ims.rbac.domain.MenuDisplay;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author hufan created in 2018/11/20 11:57
 **/
@Api(description = "商品分类接口")
@Slf4j
@RequestMapping("/product-categories")
@RestController
public class ProductCategoryApi {
    private final ProductCategoryFeign productCategoryFeign;
    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductCategoryApi(ProductCategoryFeign productCategoryFeign, ProductCategoryService productCategoryService) {
        this.productCategoryFeign = productCategoryFeign;
        this.productCategoryService = productCategoryService;
    }

    @ApiOperation("添加商品分类")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "productCategory", value = "商品分类信息", dataType = "ProductCategory", required = true)
    @PostMapping("/")
    public ResponseEntity create(@RequestBody ProductCategory productCategory) {
        log.debug("添加商品分类\t param:{}", productCategory);

        ResponseEntity entity = productCategoryFeign.create(productCategory);
        if (entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        // 返回参数 例：<201 Created,{content-type=[application/json;charset=UTF-8], date=[Sat, 24 Nov 2018 06:37:59 GMT], location=[/product-sections/13], transfer-encoding=[chunked]}>
        String location = entity.getHeaders().getLocation().toString();
        Long id = Long.valueOf(location.substring(location.lastIndexOf('/') + 1));
        return ResponseEntity.created(entity.getHeaders().getLocation()).body(Maps.of("id", id));
    }

    @ApiOperation("修改商品分类")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "商品分类Id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = ApiParamType.BODY, name = "productCategory", value = "商品分类信息", dataType = "ProductCategory", required = true)
    })
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody ProductCategory productCategory) {
        log.debug("根据id更新商品分类\t id:{} param:{}", id, productCategory);

        ResponseEntity entity = productCategoryFeign.update(id, productCategory);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }


    @ApiOperation(value = "根据Id查找商品分类", response = ProductCategory.class)
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "商品分类Id", dataType = "Long", required = true)
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        log.debug("根据Id查找商品分类\t param:{}", id);

        ResponseEntity<ProductCategory> entity = productCategoryFeign.findById(id);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }


    @ApiOperation("根据Ids删除商品分类")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "ids", value = "多个商品分类Id以英文逗号分隔", dataType = "String", required = true)
    @DeleteMapping("/{ids}")
    public ResponseEntity batchDelete(@PathVariable("ids") String ids) {
        log.debug("根据Ids删除商品分类\t param:{}", ids);

        ResponseEntity entity = productCategoryFeign.batchDelete(ids);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.noContent().build();
    }


    @ApiOperation(value = "根据条件分页查询商品分类信息列表", response = ProductCategory.class, responseContainer = "Set")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "查询条件", dataType = "ProductCategoryParam")
    })
    @PostMapping("/pages")
    public ResponseEntity search(@RequestBody ProductCategoryParam param) {
        log.debug("查询商品分类信息列表\t param:{}", param);

        ResponseEntity<Pages<ProductCategory>> entity = productCategoryFeign.pages(param);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }

    @ApiOperation(value = "查询去重的商品分类集合", response = String.class, responseContainer = "List")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "parentId", value = "父分类id", dataType = "Long", required = true)
    @GetMapping("/children/{parentId}")
    public ResponseEntity list(@PathVariable("parentId") Long parentId) {
        log.debug("查询去重的商品分类集合\t");

        ProductCategoryParam productCategoryParam = new ProductCategoryParam();
        productCategoryParam.setParentId(parentId);
        ResponseEntity<Pages<ProductCategory>> entity = productCategoryFeign.pages(productCategoryParam);
        if (entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        List<ProductCategory> productCategoryList = entity.getBody().getArray();
        List<String> groupNameList = productCategoryList.stream().map(ProductCategory::getGroupName).distinct().collect(Collectors.toList());
        return ResponseEntity.ok(groupNameList);
    }

    @ApiOperation(value = "查询商品分类树结构")
    @GetMapping("/tree")
    public ResponseEntity<Tuple<MenuDisplay>> tree() {
        log.debug("查询商品分类树结构\t param:");

        Tips tips = productCategoryService.tree();
        List<MenuDisplay> menuDisplayList = null;
        if (!tips.err()) {
            List<ProductCategory> productCategoryList = (List<ProductCategory>) Optional.of(tips.getData()).orElse(Collections.emptyList());
            menuDisplayList = productCategoryList.stream().map(MenuDisplay::new).collect(Collectors.toList());
        }
        return ResponseEntity.ok(Tuple.of(menuDisplayList));
    }
}