package com.lhiot.ims.datacenter.api;

import com.leon.microx.util.Maps;
import com.leon.microx.web.result.Tips;
import com.leon.microx.web.swagger.ApiHideBodyProperty;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.datacenter.feign.ProductShelfFegin;
import com.lhiot.ims.datacenter.feign.entity.ProductShelf;
import com.lhiot.ims.datacenter.feign.model.ProductShelfParam;
import com.lhiot.ims.datacenter.feign.model.ProductShelfResult;
import com.lhiot.ims.datacenter.service.ProductShelfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * @author hufan created in 2018/11/21 16:57
 **/
@Api(description = "商品上架接口")
@Slf4j
@RestController
@RequestMapping("/product-shelves")
public class ProductShelfApi {
    private final ProductShelfFegin productShelfFegin;
    private final ProductShelfService productShelfService;

    @Autowired
    public ProductShelfApi(ProductShelfFegin productShelfFegin, ProductShelfService productShelfService) {
        this.productShelfFegin = productShelfFegin;
        this.productShelfService = productShelfService;
    }

    @ApiOperation("添加商品上架")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "productShelfResult", value = "商品上架信息", dataType = "ProductShelfResult", required = true)
    @PostMapping("/")
    @ApiHideBodyProperty("productSpecification")
    public ResponseEntity create(@RequestBody ProductShelfResult productShelfResult) {
        log.debug("添加商品上架\t param:{}", productShelfResult);

        Tips tips = productShelfService.create(productShelfResult);
        return tips.err() ? ResponseEntity.badRequest().body(Tips.warn(tips.getMessage())) : ResponseEntity.created(URI.create("/product-shelves/" + tips.getMessage())).body(Maps.of("id", tips.getMessage()));
    }

    @ApiOperation("修改商品上架")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "商品上架id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = ApiParamType.BODY, name = "productShelfResult", value = "商品上架信息", dataType = "ProductShelfResult", required = true)
    })
    @PutMapping("/{id}")
    @ApiHideBodyProperty("sectionIds")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody ProductShelfResult productShelfResult) {
        log.debug("根据id修改商品上架\t id:{} param:{}", id, productShelfResult);

        Tips tips = productShelfService.update(id, productShelfResult);
        return tips.err() ? ResponseEntity.badRequest().body(Tips.warn(tips.getMessage())) : ResponseEntity.ok(tips.getMessage());
    }

    @ApiOperation(value = "根据Id查找商品上架", response = ProductShelf.class)
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "商品上架id", dataType = "Long", required = true)
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        log.debug("根据Id查找商品上架\t id:{}", id);

        Tips tips = productShelfService.findById(id);
        if (tips.err()) {
            return ResponseEntity.badRequest().body(Tips.warn(tips.getMessage()));
        }
        ProductShelf productShelf = (ProductShelf) tips.getData();
        return ResponseEntity.ok(productShelf);
    }

    @ApiOperation("根据商品上架Ids删除商品上架")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "ids", value = "多个商品上架Id以英文逗号分隔", dataType = "String", required = true)
    @DeleteMapping("/{ids}")
    public ResponseEntity batchDelete(@PathVariable("ids") String ids) {
        log.debug("根据商品上架Ids删除商品上架\t param:{}", ids);

        ResponseEntity entity = productShelfFegin.batchDelete(ids);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "根据条件分页查询商品上架信息列表", response = ProductShelfResult.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "查询条件", dataType = "ProductShelfParam")
    @PostMapping("/pages")
    public ResponseEntity search(@RequestBody ProductShelfParam param) {
        log.debug("查询商品上架信息列表\t param:{}", param);

        Tips tips = productShelfService.pages(param);
        return tips.err() ? ResponseEntity.badRequest().body(Tips.warn(tips.getMessage())) : ResponseEntity.ok(tips.getData());
    }

}