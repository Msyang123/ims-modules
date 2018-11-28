package com.lhiot.ims.datacenter.api;

import com.leon.microx.util.Maps;
import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tips;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.datacenter.feign.ProductFegin;
import com.lhiot.ims.datacenter.feign.entity.Product;
import com.lhiot.ims.datacenter.model.ProductInfo;
import com.lhiot.ims.datacenter.model.ProductParam;
import com.lhiot.ims.datacenter.service.ProductService;
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
 * @author hufan created in 2018/11/21 16:56
 **/
@Api("商品接口")
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

    @ApiOperation("添加商品")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "productInfo", value = "商品信息", dataType = "ProductInfo", required = true)
    @PostMapping("/")
    public ResponseEntity create(@RequestBody ProductInfo productInfo) {
        log.debug("添加商品\t param:{}", productInfo);

        Tips tips = productService.create(productInfo);
        return Objects.nonNull(tips) ? ResponseEntity.ok(Maps.of("id", tips.getMessage())) : ResponseEntity.badRequest().body("添加失败");
    }

    @ApiOperation("修改商品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "商品id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = ApiParamType.BODY, name = "product", value = "Product", dataType = "Product", required = true)
    })
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody ProductInfo productInfo) {
        log.debug("根据id修改商品\t id:{} param:{}", id, productInfo);

        Tips tips = productService.update(id , productInfo);
        return tips.err() ? ResponseEntity.badRequest().body(tips.getMessage()) : ResponseEntity.ok(tips.getData());
    }

    @ApiOperation(value = "根据Id查找商品", response = Product.class)
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "商品id", dataType = "Long", required = true)
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        log.debug("根据Id查找商品\t id:{}", id);

        Tips tips = productService.findProductById(id);
        return tips.err() ? ResponseEntity.badRequest().body(tips.getMessage()) : ResponseEntity.ok(tips.getData());
    }

    @ApiOperation("根据商品Ids删除商品")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "ids", value = "多个商品Id以英文逗号分隔", dataType = "String", required = true)
    @DeleteMapping("/{ids}")
    public ResponseEntity batchDelete(@PathVariable("ids") String ids) {
        log.debug("根据商品Ids删除商品\t param:{}", ids);

        ResponseEntity entity = productFegin.batchDelete(ids);
        if (entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "根据条件分页查询商品信息列表", response = Product.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "查询条件", dataType = "ProductParam")
    @PostMapping("/pages")
    public ResponseEntity search(@RequestBody ProductParam param) {
        log.debug("查询商品信息列表\t param:{}", param);

        ResponseEntity<Pages<Product>> entity = productFegin.pages(param);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }
}