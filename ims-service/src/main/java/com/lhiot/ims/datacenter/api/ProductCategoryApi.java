package com.lhiot.ims.datacenter.api;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tips;
import com.leon.microx.web.result.Tuple;
import com.leon.microx.web.swagger.ApiHideBodyProperty;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.datacenter.feign.ProductCategoryFeign;
import com.lhiot.ims.datacenter.feign.entity.ProductCategory;
import com.lhiot.ims.datacenter.feign.model.ProductCategoryParam;
import com.lhiot.ims.datacenter.service.ProductCategoryService;
import com.lhiot.ims.rbac.aspect.LogCollection;
import com.lhiot.ims.rbac.domain.MenuDisplay;
import com.lhiot.util.FeginResponseTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
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

    @LogCollection
    @ApiOperation("添加商品分类")
    @PostMapping("/")
    @ApiHideBodyProperty({"id", "createAt"})
    public ResponseEntity create(@RequestBody ProductCategory productCategory) {
        log.debug("添加商品分类\t param:{}", productCategory);

        ResponseEntity entity = productCategoryFeign.create(productCategory);
        return FeginResponseTools.convertCreateResponse(entity);
    }

    @LogCollection
    @ApiOperation("修改商品分类")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "商品分类Id", dataType = "Long", required = true)
    @PutMapping("/{id}")
    @ApiHideBodyProperty({"id", "createAt"})
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody ProductCategory productCategory) {
        log.debug("根据id更新商品分类\t id:{} param:{}", id, productCategory);

        ResponseEntity entity = productCategoryFeign.update(id, productCategory);
        return FeginResponseTools.convertUpdateResponse(entity);
    }


    @ApiOperation(value = "根据Id查找商品分类", response = ProductCategory.class)
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "商品分类Id", dataType = "Long", required = true)
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        log.debug("根据Id查找商品分类\t param:{}", id);

        ResponseEntity entity = productCategoryFeign.findById(id);
        return FeginResponseTools.convertNoramlResponse(entity);
    }

    @LogCollection
    @ApiOperation("根据Ids删除商品分类")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "ids", value = "多个商品分类Id以英文逗号分隔", dataType = "String", required = true)
    @DeleteMapping("/{ids}")
    public ResponseEntity batchDelete(@PathVariable("ids") String ids) {
        log.debug("根据Ids删除商品分类\t param:{}", ids);

        ResponseEntity entity = productCategoryFeign.batchDelete(ids);
        return FeginResponseTools.convertDeleteResponse(entity);
    }


    @ApiOperation(value = "根据条件分页查询商品分类信息列表", response = ProductCategory.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "查询条件", dataType = "ProductCategoryParam")
    @PostMapping("/pages")
    public ResponseEntity search(@RequestBody ProductCategoryParam param) {
        log.debug("查询商品分类信息列表\t param:{}", param);

        ResponseEntity entity = productCategoryFeign.pages(param);
        return FeginResponseTools.convertNoramlResponse(entity);
    }

    @ApiOperation(value = "查询去重的商品分类集合", response = String.class, responseContainer = "List")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "parentId", value = "父分类id", dataType = "Long", required = true)
    @GetMapping("/children/{parentId}")
    public ResponseEntity list(@PathVariable("parentId") Long parentId) {
        log.debug("查询去重的商品分类集合\t");

        ProductCategoryParam productCategoryParam = new ProductCategoryParam();
        productCategoryParam.setParentId(parentId);
        ResponseEntity entity = productCategoryFeign.pages(productCategoryParam);
        if (Objects.isNull(entity.getBody()) || entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(Objects.isNull(entity.getBody()) ? "调用基础服务失败" : entity.getBody());
        }
        Pages<ProductCategory> pages = (Pages<ProductCategory>) entity.getBody();
        List<String> groupNameList = null;
        if (Objects.nonNull(pages)) {
            List<ProductCategory> productCategoryList = Optional.of(pages.getArray()).orElse(Collections.emptyList());
            groupNameList = CollectionUtils.isEmpty(productCategoryList) ? null : productCategoryList.stream().map(ProductCategory::getGroupName).distinct().collect(Collectors.toList());
        }
        return ResponseEntity.ok(groupNameList);
    }

    @ApiOperation(value = "查询商品分类树结构", response = MenuDisplay.class, responseContainer = "List")
    @GetMapping("/tree")
    public ResponseEntity tree() {
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