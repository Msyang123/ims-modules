package com.lhiot.ims.ordercenter.api;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.session.Sessions;
import com.leon.microx.web.swagger.ApiHideBodyProperty;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.ordercenter.feign.DeliveryFeign;
import com.lhiot.ims.ordercenter.feign.entity.DeliverFeeRuleDetail;
import com.lhiot.ims.ordercenter.feign.model.DeliverFeeRuleParam;
import com.lhiot.ims.ordercenter.feign.model.DeliverFeeRulesResult;
import com.lhiot.ims.ordercenter.feign.model.DeliverFeeSearchParam;
import com.lhiot.ims.ordercenter.feign.type.UpdateWay;
import com.lhiot.util.FeginResponseTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * @author hufan created in 2018/12/15 19:35
 **/
@Api(description = "邮费规格配置接口")
@RestController
@Slf4j
public class DeliveryFeeApi {
    private final DeliveryFeign deliveryFeign;

    @Autowired
    public DeliveryFeeApi(DeliveryFeign deliveryFeign) {
        this.deliveryFeign = deliveryFeign;
    }

    @ApiOperation("添加配送费计算规则")
    @PostMapping("/delivery-fee-rule")
    @ApiHideBodyProperty({"id", "createBy"})
    public ResponseEntity createRule(@RequestBody @Valid DeliverFeeRuleParam deliverFeeRuleParam, Sessions.User user) {
        log.debug("添加配送费计算规则\t param:{}", deliverFeeRuleParam);

        deliverFeeRuleParam.setCreateBy((String) user.getUser().get("name"));
        ResponseEntity entity = deliveryFeign.createRule(deliverFeeRuleParam);
        return FeginResponseTools.convertNoramlResponse(entity);
    }

    @ApiOperation("修改配送费计算规则")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "配送费规则Id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = ApiParamType.BODY, name = "deliverFeeRuleParam", value = "需要修改的配送费规则模板以及详细规则", dataType = "DeliverFeeRuleParam", required = true)
    })
    @PutMapping("/delivery-fee-rule/{id}")
    @ApiHideBodyProperty({"id", "createBy", "detailList"})
    public ResponseEntity updateRules(@PathVariable("id") Long ruleId, @RequestBody @Valid DeliverFeeRuleParam deliverFeeRuleParam) {
        log.debug("修改配送费计算规则\t id:{} param:{}", ruleId, deliverFeeRuleParam);

        ResponseEntity entity = deliveryFeign.updateRules(ruleId, deliverFeeRuleParam);
        return FeginResponseTools.convertUpdateResponse(entity);
    }

    @ApiOperation(value = "后台管理查询配送费规则列表", response = DeliverFeeRulesResult.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "查询条件", dataType = "DeliverFeeSearchParam", required = true)
    @PostMapping("/delivery-fee-rule/pages")
    public ResponseEntity query(@RequestBody DeliverFeeSearchParam param) {
        log.debug("查询商品分类信息列表\t param:{}", param);

        ResponseEntity entity = deliveryFeign.query(param);
        if (entity.getStatusCode().isError() || Objects.isNull(entity.getBody())) {
            return ResponseEntity.badRequest().body(Objects.isNull(entity.getBody()) ? "基础服务调用失败" : entity.getBody());
        }
        Pages<DeliverFeeRulesResult> pages = (Pages<DeliverFeeRulesResult>) entity.getBody();
        List<DeliverFeeRulesResult> deliverFeeRulesResultList = pages.getArray();
        if (!CollectionUtils.isEmpty(deliverFeeRulesResultList)) {
            deliverFeeRulesResultList.stream().forEach(deliverFeeRulesResult -> {
                List<DeliverFeeRuleDetail> detailList = deliverFeeRulesResult.getDetailList();
                if (!CollectionUtils.isEmpty(detailList)) {
                    detailList.forEach(deliverFeeRuleDetail -> deliverFeeRuleDetail.setUpdateWay(UpdateWay.UPDATE));
                }
            });
        }
        return ResponseEntity.ok(entity.getBody());
    }

    @ApiOperation("根据配送费详细规则Id删除")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "配送费详细规则Id", dataType = "Long", required = true)
    @DeleteMapping("/delivery-fee-rule/detail/{id}")
    public ResponseEntity deleteDetail(@PathVariable("id") Long id) {
        log.debug("根据配送费详细规则Id删除\t param:{}", id);

        ResponseEntity entity = deliveryFeign.deleteDetail(id);
        return FeginResponseTools.convertDeleteResponse(entity);
    }

    @ApiOperation("根据配送费规则模板Id删除")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "ids", value = "配送费规则Ids,逗号分隔", dataType = "String", required = true)
    @DeleteMapping("/delivery-fee-rule/{ids}")
    public ResponseEntity deleteRule(@PathVariable("ids") String ids) {
        log.debug("根据配送费规则模板Id删除\t param:{}", ids);

        ResponseEntity entity = deliveryFeign.deleteRule(ids);
        return FeginResponseTools.convertDeleteResponse(entity);
    }
}