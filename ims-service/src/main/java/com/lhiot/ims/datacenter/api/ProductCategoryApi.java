package com.lhiot.ims.datacenter.api;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.datacenter.feign.ProductCategoryFeign;
import com.lhiot.ims.datacenter.feign.entity.ProductCategory;
import com.lhiot.ims.datacenter.model.ProductCategoryParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
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

    @Autowired
    public ProductCategoryApi(ProductCategoryFeign productCategoryFeign) {
        this.productCategoryFeign = productCategoryFeign;
    }

    @ApiOperation("添加商品分类")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "productCategory", value = "商品分类信息", dataType = "ProductCategory", required = true)
    @PostMapping("/")
    public ResponseEntity create(@RequestBody ProductCategory productCategory) {
        log.debug("添加商品分类\t param:{}", productCategory);

        ResponseEntity entity = productCategoryFeign.create(productCategory);
        if (Objects.isNull(entity) || entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        return ResponseEntity.ok(entity.getBody());
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
        if (Objects.isNull(entity) || entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        return ResponseEntity.ok(entity.getBody());
    }


    @ApiOperation(value = "根据Id查找商品分类", response = ProductCategory.class)
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "商品分类Id", dataType = "Long", required = true)
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long categoryId) {
        log.debug("根据Id查找商品分类\t param:{}", categoryId);

        ResponseEntity<ProductCategory> entity = productCategoryFeign.findById(categoryId);
        if (Objects.isNull(entity) || entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        return ResponseEntity.ok(entity.getBody());
    }


    @ApiOperation("根据Ids删除商品分类")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "ids", value = "多个商品分类Id以英文逗号分隔", dataType = "String", required = true)
    @DeleteMapping("/{ids}")
    public ResponseEntity batchDelete(@PathVariable("ids") String ids) {
        log.debug("根据Ids删除商品分类\t param:{}", ids);

        ResponseEntity entity = productCategoryFeign.batchDelete(ids);
        if (Objects.isNull(entity) || entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        return ResponseEntity.ok(entity.getBody());
    }


    @ApiOperation(value = "根据条件分页查询商品分类信息列表", response = ProductCategory.class, responseContainer = "Set")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "查询条件", dataType = "ProductCategoryParam")
    })
    @PostMapping("/pages")
    public ResponseEntity search(@RequestBody ProductCategoryParam param) {
        log.debug("查询商品分类信息列表\t param:{}", param);

        // 设置默认页数和行数
        int page = Objects.nonNull(param.getPage()) ? param.getPage() : 1;
        int rows = Objects.nonNull(param.getRows()) ? param.getRows() : 10;
        param.setPage(page);
        param.setRows(rows);

        ResponseEntity<Pages<ProductCategory>> entity = productCategoryFeign.pages(param);
        if (Objects.isNull(entity) || entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        return ResponseEntity.ok(entity);
    }

    @ApiOperation(value = "查询去重的商品分类集合", response = String.class, responseContainer = "List")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "parentId", value = "父分类id", dataType = "Long", required = true)
    @GetMapping("/childrens/{parentId}")
    public ResponseEntity list(@PathVariable("parentId") Long parentId) {
        log.debug("查询去重的商品分类集合\t");

        ProductCategoryParam productCategoryParam = new ProductCategoryParam();
        productCategoryParam.setParentId(parentId);
        ResponseEntity<Pages<ProductCategory>> entity = productCategoryFeign.pages(productCategoryParam);
        if (Objects.nonNull(entity) && entity.getStatusCodeValue() < 400) {
            List<ProductCategory> productCategoryList = entity.getBody().getArray();
            List<String> groupNameList = productCategoryList.stream().map(ProductCategory::getGroupName).collect(Collectors.toList());
            return ResponseEntity.ok(groupNameList);
        }
        return ResponseEntity.badRequest().body(entity.getBody());
    }

    // TODO "/{tree}" 参考菜单的 @GetMapping("/list/all")
}