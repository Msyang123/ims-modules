package com.lhiot.ims.datacenter.api;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.datacenter.feign.AdvertisementFeign;
import com.lhiot.ims.datacenter.feign.entity.Advertisement;
import com.lhiot.ims.datacenter.model.AdvertisementParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2018/11/22 16:13
 **/
@Api("广告管理接口")
@Slf4j
@RestController
@RequestMapping("/advertisements")
public class AdvertisementApi {
    private final AdvertisementFeign advertisementFeign;

    public AdvertisementApi(AdvertisementFeign advertisementFeign) {
        this.advertisementFeign = advertisementFeign;
    }

    @ApiOperation("添加广告")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "advertisement", value = "广告信息", dataType = "Advertisement", required = true)
    @PostMapping("/")
    public ResponseEntity create(@RequestBody Advertisement advertisement) {
        log.debug("添加广告\t param:{}", advertisement);

        ResponseEntity entity = advertisementFeign.create(advertisement);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }

    @ApiOperation("修改广告")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "广告id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = ApiParamType.BODY, name = "advertisement", value = "Advertisement", dataType = "Advertisement", required = true)
    })
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody Advertisement advertisement) {
        log.debug("根据id修改广告\t id:{} param:{}", id, advertisement);

        ResponseEntity entity = advertisementFeign.update(id, advertisement);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }

    @ApiOperation(value = "根据Id查找广告", response = Advertisement.class)
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "广告id", dataType = "Long", required = true)
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        log.debug("根据Id查找广告\t id:{}", id);

        ResponseEntity entity = advertisementFeign.findById(id);
        if (entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(entity.getBody());
        }
        Advertisement advertisement = (Advertisement) entity.getBody();
        return ResponseEntity.ok(advertisement);
    }

    @ApiOperation("根据广告Ids删除广告")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "ids", value = "多个广告Id以英文逗号分隔", dataType = "String", required = true)
    @DeleteMapping("/{ids}")
    public ResponseEntity batchDelete(@PathVariable("ids") String ids) {
        log.debug("根据广告Ids删除广告\t param:{}", ids);

        ResponseEntity entity = advertisementFeign.batchDelete(ids);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "根据条件分页查询广告信息列表", response = Advertisement.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "查询条件", dataType = "AdvertisementParam")
    @PostMapping("/pages")
    public ResponseEntity search(@RequestBody AdvertisementParam param) {
        log.debug("查询广告信息列表\t param:{}", param);

        ResponseEntity<Pages<Advertisement>> entity = advertisementFeign.pages(param);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }

}