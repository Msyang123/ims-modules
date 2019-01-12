package com.lhiot.ims.datacenter.api;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.swagger.ApiHideBodyProperty;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.dc.dictionary.DictionaryClient;
import com.lhiot.dc.dictionary.module.Dictionary;
import com.lhiot.ims.datacenter.feign.StoreFeign;
import com.lhiot.ims.datacenter.feign.entity.Store;
import com.lhiot.ims.datacenter.feign.model.StoreSearchParam;
import com.lhiot.util.FeginResponseTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author hufan created in 2018/12/17 16:49
 **/
@Api(description = "门店接口")
@RestController
@Slf4j
public class StoreApi {
    private final StoreFeign storeFeign;
    private DictionaryClient dictionaryClient;

    @Autowired
    public StoreApi(StoreFeign storeFeign, DictionaryClient dictionaryClient) {
        this.storeFeign = storeFeign;
        this.dictionaryClient = dictionaryClient;
    }

    @ApiOperation(value = "根据id查询门店", notes = "根据id查询门店", response = Store.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "主键id", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = ApiParamType.QUERY, name = "applicationType", value = "应用类型", dataType = "String")
    })
    @GetMapping("/stores/{id}")
    public ResponseEntity findStore(@PathVariable("id") Long id, @RequestParam("applicationType") String applicationType) {
        log.debug("根据id查询门店\t id:{}, param:{}", id, applicationType);

        ResponseEntity entity = storeFeign.findStore(id, applicationType);
        return FeginResponseTools.convertNoramlResponse(entity);
    }

    @ApiOperation(value = "根据门店编码查询门店信息", response = Store.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "code", value = "门店编码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = ApiParamType.QUERY, name = "applicationType", value = "应用类型", dataType = "String")
    })
    @GetMapping("/stores/code/{code}")
    public ResponseEntity findStoreByCode(@PathVariable("code") String code, @RequestParam(value = "applicationType", required = false) String applicationType) {
        log.debug("根据门店编码查询门店信息\t code:{}, param{}", code, applicationType);

        ResponseEntity entity = storeFeign.findStoreByCode(code, applicationType);
        return FeginResponseTools.convertNoramlResponse(entity);
    }

    @ApiOperation(value = "添加门店")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "store", value = "要添加的门店", required = true, dataType = "Store")
    @PostMapping("/stores")
    public ResponseEntity create(@RequestBody Store store) {
        log.debug("添加门店\t param:{}", store);
        ResponseEntity entity = storeFeign.create(store);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok().build();
    }


    @ApiOperation(value = "根据id更新门店")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "要更新的门店id", required = true, dataType = "Long")
    @PutMapping("/stores/{id}")
    @ApiHideBodyProperty("id")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody Store store) {
        log.debug("根据id更新门店\t param:{}", store);

        store.setId(id);
        ResponseEntity entity = storeFeign.update(id, store);
        return FeginResponseTools.convertUpdateResponse(entity);
    }

    @ApiOperation(value = "根据id删除门店")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "要删除门店的id", required = true, dataType = "Long")
    @DeleteMapping("/stores/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        log.debug("根据ids删除门店\t param:{}", id);

        ResponseEntity entity = storeFeign.deleteById(id);
        return FeginResponseTools.convertDeleteResponse(entity);
    }

    @PostMapping("/stores/pages")
    @ApiOperation(value = "根据位置查询门店所有列表根据距离排序", response = Store.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "查询条件", dataType = "StoreSearchParam", required = true)
    public ResponseEntity search(@RequestBody StoreSearchParam param) {
        log.debug("根据位置查询门店列表\t param:{}", param);

        ResponseEntity entity = storeFeign.search(param);
        return FeginResponseTools.convertNoramlResponse(entity);
    }

    @ApiOperation(value = "查询所有门店区域", response = String.class, responseContainer = "List")
    @GetMapping("/store-areas")
    public ResponseEntity findList() {
        log.debug("查询所有门店区域");
        List<Map<String,String>> storeAreas = new ArrayList<>();
        Optional<Dictionary> optional = dictionaryClient.dictionary("storeAreas");
        if (optional.isPresent()) {
            Dictionary dictionary = optional.get();
            dictionary.getEntries().forEach(item -> {
                Map<String,String> area=new HashMap<>();
                area.put("area", item.getCode());
                area.put("areaName", item.getName());
                storeAreas.add(area);
            });
        }
        return ResponseEntity.ok(Pages.of(storeAreas));
    }

}