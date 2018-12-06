package com.lhiot.ims.healthygood.api.user;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tips;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.healthygood.feign.user.FruitDoctorFeign;
import com.lhiot.ims.healthygood.feign.user.entity.FruitDoctor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2018/12/6 20:13
 **/
@Api(description = "鲜果师管理接口")
@RestController
@Slf4j
public class FruitDoctorApi {
    private final FruitDoctorFeign fruitDoctorFeign;

    public FruitDoctorApi(FruitDoctorFeign fruitDoctorFeign) {
        this.fruitDoctorFeign = fruitDoctorFeign;
    }



    @ApiOperation(value = "查询鲜果师成员详情", response = FruitDoctor.class)
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "鲜果师id", dataType = "Long", required = true)
    @GetMapping("/fruit-doctors//{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        log.debug("查询鲜果师成员详情\t id:{}", id);

        ResponseEntity<FruitDoctor> entity = fruitDoctorFeign.findById(id);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }

    @ApiOperation(value = "修改鲜果师成员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "鲜果师id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = ApiParamType.BODY, name = "fruitDoctor", value = "要修改鲜果师成员信息", dataType = "FruitDoctor", required = true)
    })
    @PutMapping("/fruit-doctors//{id}")
    public ResponseEntity updateById(@PathVariable("id") Long id, @RequestBody FruitDoctor fruitDoctor) {
        log.debug("修改鲜果师成员信息\t id:{},param:{}", id, fruitDoctor);

        ResponseEntity entity = fruitDoctorFeign.updateById(id, fruitDoctor);
        return !entity.getStatusCode().isError() ? ResponseEntity.ok().build() : ResponseEntity.badRequest().body(Tips.warn("修改鲜果师成员信息失败!"));
    }

    @PostMapping("/fruit-doctors/pages")
    @ApiOperation(value = "查询鲜果师成员分页列表", response = FruitDoctor.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "fruitDoctor", value = "要查询的鲜果师信息", dataType = "FruitDoctor", required = true)
    public ResponseEntity search(@RequestBody FruitDoctor fruitDoctor) {
        log.debug("查询鲜果师成员分页列表\t param:{}", fruitDoctor);

        ResponseEntity<Pages<FruitDoctor>> entity = fruitDoctorFeign.search(fruitDoctor);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }
}