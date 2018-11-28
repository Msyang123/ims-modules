package com.lhiot.ims.datacenter.api;

import com.leon.microx.util.Maps;
import com.leon.microx.web.result.Pages;
import com.leon.microx.web.swagger.ApiHideBodyProperty;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.datacenter.feign.ProductShelfFegin;
import com.lhiot.ims.datacenter.feign.entity.ProductShelf;
import com.lhiot.ims.datacenter.model.ProductShelfParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2018/11/21 16:57
 **/
@Api("商品上架接口")
@Slf4j
@RestController
@RequestMapping("/product-shelves")
public class ProductShelfApi {
    private final ProductShelfFegin productShelfFegin;

    @Autowired
    public ProductShelfApi(ProductShelfFegin productShelfFegin) {
        this.productShelfFegin = productShelfFegin;
    }

    @ApiOperation("添加商品上架")
    @PostMapping("/")
    @ApiHideBodyProperty("productSpecification")
    public ResponseEntity create(@RequestBody ProductShelf productShelf) {
        log.debug("添加商品上架\t param:{}", productShelf);

        ResponseEntity entity = productShelfFegin.create(productShelf);
        if (entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        // 返回参数 例：<201 Created,{content-type=[application/json;charset=UTF-8], date=[Sat, 24 Nov 2018 06:37:59 GMT], location=[/product-sections/13], transfer-encoding=[chunked]}>
        String location = entity.getHeaders().getLocation().toString();
        Long id = Long.valueOf(location.substring(location.lastIndexOf('/') + 1));
        return ResponseEntity.created(entity.getHeaders().getLocation()).body(Maps.of("id", id));
    }

    @ApiOperation("修改商品上架")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "商品上架id", dataType = "Long", required = true)
    })
    @PutMapping("/{id}")
    @ApiHideBodyProperty("productSpecification")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody ProductShelf productShelf) {
        log.debug("根据id修改商品上架\t id:{} param:{}", id, productShelf);

        ResponseEntity entity = productShelfFegin.update(id, productShelf);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }

    @ApiOperation(value = "根据Id查找商品上架", response = ProductShelf.class)
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "商品上架id", dataType = "Long", required = true)
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        log.debug("根据Id查找商品上架\t id:{}", id);

        ResponseEntity<ProductShelf> entity = productShelfFegin.findById(id);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }

    @ApiOperation("根据商品上架Ids删除商品上架")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "ids", value = "多个商品上架Id以英文逗号分隔", dataType = "String", required = true)
    @DeleteMapping("/{ids}")
    public ResponseEntity batchDelete(@PathVariable("ids") String ids) {
        log.debug("根据商品上架Ids删除商品上架\t param:{}", ids);

        ResponseEntity entity = productShelfFegin.batchDelete(ids);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "根据条件分页查询商品上架信息列表", response = ProductShelf.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "查询条件", dataType = "ProductShelfParam")
    @PostMapping("/pages")
    public ResponseEntity search(@RequestBody ProductShelfParam param) {
        log.debug("查询商品上架信息列表\t param:{}", param);

        ResponseEntity<Pages<ProductShelf>> entity = productShelfFegin.pages(param);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }

}