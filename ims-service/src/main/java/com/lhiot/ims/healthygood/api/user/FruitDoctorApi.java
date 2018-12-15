package com.lhiot.ims.healthygood.api.user;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.session.Sessions;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.healthygood.feign.user.FruitDoctorFeign;
import com.lhiot.ims.healthygood.feign.user.FruitDoctorQualificationFeign;
import com.lhiot.ims.healthygood.feign.user.FruitDoctorSettlementFeign;
import com.lhiot.ims.healthygood.feign.user.entity.FruitDoctor;
import com.lhiot.ims.healthygood.feign.user.entity.RegisterApplication;
import com.lhiot.ims.healthygood.feign.user.entity.SettlementApplication;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.Instant;

/**
 * @author hufan created in 2018/12/6 20:13
 **/
@Api(description = "鲜果师管理接口")
@RestController
@Slf4j
public class FruitDoctorApi {
    private final FruitDoctorFeign fruitDoctorFeign;
    private final FruitDoctorQualificationFeign fruitDoctorQualificationFeign;
    private final FruitDoctorSettlementFeign fruitDoctorSettlementFeign;

    public FruitDoctorApi(FruitDoctorFeign fruitDoctorFeign, FruitDoctorQualificationFeign fruitDoctorQualificationFeign, FruitDoctorSettlementFeign fruitDoctorSettlementFeign) {
        this.fruitDoctorFeign = fruitDoctorFeign;
        this.fruitDoctorQualificationFeign = fruitDoctorQualificationFeign;
        this.fruitDoctorSettlementFeign = fruitDoctorSettlementFeign;
    }

    @ApiOperation(value = "查询鲜果师管理详情", response = FruitDoctor.class)
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "鲜果师id", dataType = "Long", required = true)
    @GetMapping("/fruit-doctors/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        log.debug("查询鲜果师管理详情\t id:{}", id);

        ResponseEntity<FruitDoctor> entity = fruitDoctorFeign.findById(id);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }

    @ApiOperation(value = "修改鲜果师管理信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "鲜果师id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = ApiParamType.BODY, name = "fruitDoctor", value = "要修改鲜果师管理信息", dataType = "FruitDoctor", required = true)
    })
    @PutMapping("/fruit-doctors/{id}")
    public ResponseEntity updateById(@PathVariable("id") Long id, @RequestBody FruitDoctor fruitDoctor) {
        log.debug("修改鲜果师管理信息\t id:{},param:{}", id, fruitDoctor);

        ResponseEntity entity = fruitDoctorFeign.updateById(id, fruitDoctor);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body("修改鲜果师管理信息失败!") : ResponseEntity.ok().build();
    }

    @PostMapping("/fruit-doctors/pages")
    @ApiOperation(value = "查询鲜果师管理分页列表", response = FruitDoctor.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "fruitDoctor", value = "要查询的鲜果师信息", dataType = "FruitDoctor", required = true)
    public ResponseEntity search(@RequestBody FruitDoctor fruitDoctor) {
        log.debug("查询鲜果师管理分页列表\t param:{}", fruitDoctor);

        ResponseEntity<Pages<FruitDoctor>> entity = fruitDoctorFeign.search(fruitDoctor);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }


    @ApiOperation(value = "根据id更新鲜果师申请记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "主键id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = "body", name = "registerApplication", value = "要更新的鲜果师申请记录", required = true, dataType = "RegisterApplication")
    })
    @PutMapping("/fruit-doctors/qualifications/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody RegisterApplication registerApplication, Sessions.User user) {
        log.debug("根据id更新鲜果师申请记录\t id:{} param:{}", id, registerApplication);

        registerApplication.setAuditAt(Date.from(Instant.now()));
        registerApplication.setAuditUser(user.getUser().get("name").toString());
        ResponseEntity entity = fruitDoctorQualificationFeign.update(id, registerApplication);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body("修改鲜果师申请记录失败") : ResponseEntity.ok().build();
    }

    @ApiOperation(value = "根据条件分页查询鲜果师申请记录列表", response = RegisterApplication.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "registerApplication", value = "鲜果师申请信息", dataType = "RegisterApplication", required = true)
    @PostMapping("/fruit-doctors/qualifications/pages")
    public ResponseEntity search(@RequestBody RegisterApplication registerApplication) {
        log.debug("根据条件分页查询鲜果师申请记录列表\t param:{}", registerApplication);

        ResponseEntity<Pages<RegisterApplication>> entity = fruitDoctorQualificationFeign.search(registerApplication);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }

    @ApiOperation(value = "结算申请修改")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "结算申请id", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = ApiParamType.BODY, name = "settlementApplication", value = "要修改的结算申请信息", dataType = "SettlementApplication", required = true)
    })
    @PutMapping("/fruit-doctors/settlement/{id}")
    public ResponseEntity updateSettlement(@PathVariable("id") Long id, @RequestBody SettlementApplication settlementApplication) {
        log.debug("结算申请修改\t id:{},param:{}", id, settlementApplication);

        ResponseEntity entity = fruitDoctorSettlementFeign.updateSettlement(id, settlementApplication);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok().build();
    }

    @ApiOperation(value = "结算申请分页查询")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "settlementApplication", value = "结算申请分页查询条件", dataType = "SettlementApplication", required = true)
    @PostMapping("/fruit-doctors/settlement/pages")
    public ResponseEntity search(@RequestBody SettlementApplication settlementApplication) {
        log.debug("结算申请分页查询\t param:{}", settlementApplication);

        ResponseEntity<Pages<SettlementApplication>> entity = fruitDoctorSettlementFeign.search(settlementApplication);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.ok(entity.getBody());
    }
}