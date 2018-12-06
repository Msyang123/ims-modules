package com.lhiot.ims.healthygood.api.user;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.healthygood.feign.user.FruitDoctorQualificationFeign;
import com.lhiot.ims.healthygood.feign.user.entity.RegisterApplication;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.Instant;

/**
 * @author hufan created in 2018/12/6 12:22
 **/
@Api(description = "鲜果师申请记录接口")
@RestController
@Slf4j
public class FruitDoctorQualificationApi {
    private final FruitDoctorQualificationFeign fruitDoctorQualificationFeign;

    @Autowired
    public FruitDoctorQualificationApi(FruitDoctorQualificationFeign fruitDoctorQualificationFeign) {
        this.fruitDoctorQualificationFeign = fruitDoctorQualificationFeign;
    }

    @ApiOperation(value = "根据id更新鲜果师申请记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "主键id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = "body", name = "registerApplication", value = "要更新的鲜果师申请记录", required = true, dataType = "RegisterApplication")
    })
    @PutMapping("/fruit-doctors/qualifications/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody RegisterApplication registerApplication){
        log.debug("根据id更新鲜果师申请记录\t id:{} param:{}", id,registerApplication);

        registerApplication.setAuditAt(Date.from(Instant.now()));
        ResponseEntity entity = fruitDoctorQualificationFeign.update(id, registerApplication);
        return  !entity.getStatusCode().isError() ? ResponseEntity.ok().build() : ResponseEntity.badRequest().body("修改鲜果师申请记录失败");
    }

    @ApiOperation(value = "根据条件分页查询鲜果师申请记录列表(后台)", response = RegisterApplication.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "registerApplication",value = "鲜果师申请信息",  dataType = "RegisterApplication",required = true)
    @PostMapping("/fruit-doctors/qualifications/pages")
    public ResponseEntity search(@RequestBody RegisterApplication registerApplication) {
        log.debug("根据条件分页查询鲜果师申请记录列表\t param:{}", registerApplication);

        ResponseEntity<Pages<RegisterApplication>> entity = fruitDoctorQualificationFeign.search(registerApplication);
        return !entity.getStatusCode().isError() ? ResponseEntity.ok(entity.getBody()) : ResponseEntity.badRequest().body(entity.getBody());
    }
}