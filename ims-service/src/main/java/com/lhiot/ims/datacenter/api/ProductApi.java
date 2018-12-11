package com.lhiot.ims.datacenter.api;

import com.leon.microx.util.Maps;
import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tips;
import com.leon.microx.web.swagger.ApiHideBodyProperty;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.datacenter.feign.ProductFegin;
import com.lhiot.ims.datacenter.feign.entity.Product;
import com.lhiot.ims.datacenter.feign.model.ProductParam;
import com.lhiot.ims.datacenter.feign.model.ProductResult;
import com.lhiot.ims.datacenter.service.ProductService;
import com.lhiot.ims.rbac.aspect.LogCollection;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Objects;

/**
 * @author hufan created in 2018/11/21 16:56
 **/
@Api(description = "商品接口")
@Slf4j
@RestController
@RequestMapping("/products")
public class ProductApi {
    private final ProductFegin productFegin;
    private final ProductService productService;

    @Autowired
    public ProductApi(ProductFegin productFegin, ProductService productService) {
        this.productFegin = productFegin;
        this.productService = productService;
    }

    @LogCollection
    @ApiOperation("添加商品")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "productResult", value = "商品信息", dataType = "ProductResult", required = true)
    @PostMapping("/")
    @ApiHideBodyProperty({"id", "createAt"})
    public ResponseEntity create(@RequestBody ProductResult productResult) {
        log.debug("添加商品\t param:{}", productResult);

        Tips tips = productService.create(productResult);
        return !tips.err() ? ResponseEntity.created(URI.create("/products/" + tips.getMessage())).body(Maps.of("id", tips.getMessage())) : ResponseEntity.badRequest().body(Tips.warn("添加失败"));
    }

    @LogCollection
    @ApiOperation("修改商品")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "商品id", dataType = "Long", required = true)
    @PutMapping("/{id}")
    @ApiHideBodyProperty({"id", "createAt"})
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody ProductResult productResult) {
        log.debug("根据id修改商品\t id:{} param:{}", id, productResult);

        Tips tips = productService.update(id, productResult);
        return tips.err() ? ResponseEntity.badRequest().body(Tips.warn(tips.getMessage())) : ResponseEntity.ok(Tips.info(tips.getMessage()));
    }

    @ApiOperation(value = "根据Id查找商品", response = ProductResult.class)
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "商品id", dataType = "Long", required = true)
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        log.debug("根据Id查找商品\t id:{}", id);

        Tips tips = productService.findProductById(id);
        return tips.err() ? ResponseEntity.badRequest().body(Tips.warn(tips.getMessage())) : ResponseEntity.ok(tips.getData());
    }

    @LogCollection
    @ApiOperation("根据商品Ids删除商品")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "ids", value = "多个商品Id以英文逗号分隔", dataType = "String", required = true)
    @DeleteMapping("/{ids}")
    public ResponseEntity batchDelete(@PathVariable("ids") String ids) {
        log.debug("根据商品Ids删除商品\t param:{}", ids);

        ResponseEntity entity = productFegin.batchDelete(ids);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "根据条件分页查询商品信息列表", response = Product.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "查询条件", dataType = "ProductParam")
    @PostMapping("/pages")
    public ResponseEntity search(@RequestBody ProductParam param) {
        log.debug("查询商品信息列表\t param:{}", param);

        ResponseEntity<Pages<Product>> entity = productFegin.pages(param);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(Tips.warn(entity.getBody().toString())) : ResponseEntity.ok(entity.getBody());
    }
}