package com.lhiot.ims.healthygood.api.customplan;

import com.leon.microx.web.result.Tips;
import com.leon.microx.web.session.Sessions;
import com.leon.microx.web.swagger.ApiHideBodyProperty;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.healthygood.feign.customplan.CustomPlanFeign;
import com.lhiot.ims.healthygood.feign.customplan.entity.CustomPlanProduct;
import com.lhiot.ims.healthygood.feign.customplan.entity.CustomPlanSpecification;
import com.lhiot.ims.healthygood.feign.customplan.model.CustomPlanDetailResult;
import com.lhiot.ims.healthygood.feign.customplan.model.CustomPlanParam;
import com.lhiot.ims.healthygood.feign.customplan.model.CustomPlanPeriodResult;
import com.lhiot.ims.healthygood.feign.customplan.model.CustomPlanProductResult;
import com.lhiot.ims.healthygood.feign.customplan.type.ValidOrInvalid;
import com.lhiot.ims.rbac.aspect.LogCollection;
import com.lhiot.util.FeginResponseTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author hufan created in 2018/12/1 15:20
 **/
@Api(description = "定制计划接口")
@Slf4j
@RestController
public class CustomPlanApi {
    private final CustomPlanFeign customPlanFeign;

    public CustomPlanApi(CustomPlanFeign customPlanFeign) {
        this.customPlanFeign = customPlanFeign;
    }

    @LogCollection
    @ApiOperation("添加定制计划")
    @PostMapping("/custom-plans")
    @ApiHideBodyProperty({"id","createAt","createUser"})
    public ResponseEntity create(@Valid @RequestBody CustomPlanDetailResult customPlanDetailResult, Sessions.User user) {
        log.debug("添加定制计划\t param:{}", customPlanDetailResult);

        Tips tips = this.validatePeriod(customPlanDetailResult);
        if (tips.err()) {
            return ResponseEntity.badRequest().body(tips.getMessage());
        }
        List<CustomPlanPeriodResult> addPeriodList = (List<CustomPlanPeriodResult>) tips.getData();
        if (CollectionUtils.isEmpty(addPeriodList)) {
            return ResponseEntity.badRequest().body("请至少填写一个完整的套餐信息");
        }
        customPlanDetailResult.setPeriodList(new ArrayList<>());
        customPlanDetailResult.setPeriodList(addPeriodList);
        customPlanDetailResult.setCreateUser((String) user.getUser().get("name"));
        ResponseEntity entity = customPlanFeign.create(customPlanDetailResult);
        return FeginResponseTools.convertCreateResponse(entity);
    }

    @GetMapping("/custom-plans/{id}")
    @ApiOperation(value = "定制计划详细信息", response = CustomPlanDetailResult.class)
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "定制计划id", dataType = "Long", required = true)
    public ResponseEntity customPlans(@PathVariable Long id) {
        log.debug("定制计划详细信息\t param:{}", id);

        ResponseEntity entity = customPlanFeign.findById(id);
        if (entity.getStatusCode().isError() || Objects.isNull(entity.getBody())) {
            return ResponseEntity.badRequest().body("基础服务调用失败");
        }
        CustomPlanDetailResult customPlanDetailResult = (CustomPlanDetailResult) entity.getBody();
        List<CustomPlanPeriodResult> periodList =  customPlanDetailResult.getPeriodList();
        if (!CollectionUtils.isEmpty(periodList)){
            periodList.stream().sorted(Comparator.comparing(CustomPlanPeriodResult::getPlanPeriod));
            periodList.forEach(periodResult -> {
                periodResult.setIndex(periodResult.getPlanPeriod() == 7 ? 0 : 1);
                if (!CollectionUtils.isEmpty(periodResult.getSpecificationList())) {
                    periodResult.getSpecificationList().stream().sorted(Comparator.comparing(CustomPlanSpecification::getQuantity));
                    periodResult.getSpecificationList().forEach(customPlanSpecification -> customPlanSpecification.setIndex(periodResult.getSpecificationList().indexOf(customPlanSpecification)));
                }
                if (!CollectionUtils.isEmpty(periodResult.getProducts())) {
                    periodResult.getProducts().stream().sorted(Comparator.comparing(CustomPlanProductResult::getDayOfPeriod));
                    periodResult.getProducts().forEach(planProductResult -> planProductResult.setIndex(periodResult.getProducts().indexOf(planProductResult)));
                }
            });
            for (int i = 0; i < periodList.size(); i++) {
                if (CollectionUtils.isEmpty(periodList.get(i).getSpecificationList()) || CollectionUtils.isEmpty(periodList.get(i).getProducts())) {
                    periodList.remove(i);
                    i--;
                }
            }
        }
        return ResponseEntity.ok(customPlanDetailResult);
    }

    @LogCollection
    @ApiOperation("修改定制计划")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "定制计划id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = ApiParamType.BODY, name = "customPlanDetailResult", value = "定制计划相关信息", dataType = "CustomPlanDetailResult", required = true)
    })
    @PutMapping("/custom-plans/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody CustomPlanDetailResult customPlanDetailResult) {
        log.debug("修改定制计划\t param:{}", customPlanDetailResult);

        Tips tips = this.validatePeriod(customPlanDetailResult);
        if (tips.err()) {
            return ResponseEntity.badRequest().body(tips.getMessage());
        }
        List<CustomPlanPeriodResult> addPeriodList = (List<CustomPlanPeriodResult>) tips.getData();
        if (CollectionUtils.isEmpty(addPeriodList)) {
            return ResponseEntity.badRequest().body("请至少填写一个完整的套餐信息");
        }
        customPlanDetailResult.setPeriodList(new ArrayList<>());
        customPlanDetailResult.setPeriodList(addPeriodList);

        ResponseEntity entity = customPlanFeign.update(id, customPlanDetailResult);
        return FeginResponseTools.convertUpdateResponse(entity);
    }

    @LogCollection
    @ApiOperation("定制计划上下架")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "定制计划id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = ApiParamType.QUERY, name = "status", value = "定制计划状态", dataType = "ValidOrInvalid", required = true)
    })
    @PutMapping("/custom-plans/{id}/status")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestParam("status") ValidOrInvalid status) {
        log.debug("定制计划上下架\t id:{}, param:{}", id, status);

        CustomPlanDetailResult customPlanDetailResult = new CustomPlanDetailResult();
        customPlanDetailResult.setStatus(status);
        ResponseEntity entity = customPlanFeign.update(id, customPlanDetailResult);
        return FeginResponseTools.convertUpdateResponse(entity);
    }

    @LogCollection
    @ApiOperation("根据ids删除定制计划")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "ids", value = "多个定制计划id以英文逗号分隔", dataType = "String", required = true)
    @DeleteMapping("/custom-plans/{ids}")
    public ResponseEntity batchDelete(@PathVariable("ids") String ids) {
        log.debug("批量删除定制计划\t param:{}", ids);

        ResponseEntity entity = customPlanFeign.batchDelete(ids);
        return FeginResponseTools.convertDeleteResponse(entity);
    }

    @ApiOperation(value = "根据条件分页查询定制计划信息列表", response = CustomPlanDetailResult.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "查询条件", dataType = "CustomPlanParam")
    @PostMapping("/custom-plans/pages")
    public ResponseEntity search(@RequestBody CustomPlanParam param) {
        log.debug("根据条件分页查询定制计划信息列表\t param:{}", param);

        ResponseEntity entity = customPlanFeign.search(param);
        return FeginResponseTools.convertNoramlResponse(entity);
    }

    @LogCollection
    @ApiOperation("修改定制计划周期类型信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "定制计划id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = ApiParamType.BODY, name = "customPlanDetailResult", value = "定制计划信息", dataType = "CustomPlanDetailResult", required = true)
    })
    @PutMapping("/custom-plan-periods/{id}")
    public ResponseEntity updatePeriod(@PathVariable("id") Long id, @RequestBody CustomPlanDetailResult customPlanDetailResult) {
        log.debug("修改定制计划周期类型信息\t param:{}", customPlanDetailResult);

        Tips tips = this.validatePeriod(customPlanDetailResult);
        if (tips.err()) {
            return ResponseEntity.badRequest().body(tips.getMessage());
        }
        List<CustomPlanPeriodResult> addPeriodList = (List<CustomPlanPeriodResult>) tips.getData();
        if (CollectionUtils.isEmpty(addPeriodList)) {
            return ResponseEntity.badRequest().body("请至少填写一个完整的套餐信息");
        }
        customPlanDetailResult.setPeriodList(new ArrayList<>());
        customPlanDetailResult.setPeriodList(addPeriodList);

        ResponseEntity entity = customPlanFeign.updatePeriod(id, customPlanDetailResult);
        return FeginResponseTools.convertUpdateResponse(entity);
    }

    /**
     * 如果某一周期套餐价格不为空或其中有配置至少一个套餐，那么必须填该周期套餐所有价格和套餐商品配置都必须填写完整
     * 返回未填写完整的信息
     * @param customPlanDetailResult
     * @return String
     */
    private Tips<List<CustomPlanPeriodResult>> validatePeriod(CustomPlanDetailResult customPlanDetailResult) {
        List<CustomPlanPeriodResult> periodList = customPlanDetailResult.getPeriodList();
        List<CustomPlanPeriodResult> addPeriodList = new ArrayList<>();
        for (CustomPlanPeriodResult periodResult : periodList ){
            List<CustomPlanSpecification> specificationList = periodResult.getSpecificationList();
            List<CustomPlanProductResult> productList = periodResult.getProducts();
            List<CustomPlanSpecification> priceIsNullList = specificationList.stream().filter(specification -> (Objects.isNull(specification.getPrice()) || specification.getPrice() == 0)).collect(Collectors.toList());
            List<CustomPlanProductResult> productIsNullList = productList.stream().filter(product -> (Objects.isNull(product.getShelfId()) || product.getShelfId() == 0)).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(priceIsNullList) && CollectionUtils.isEmpty(productIsNullList)) {
                // 所有价格和商品信息都不为空
                addPeriodList.add(periodResult);
            } else if (priceIsNullList.size() == 3 && productIsNullList.size() == periodResult.getPlanPeriod()) {
                // 所有价格和商品信息都为空
            } else {
                return Tips.warn("套餐信息不完整");
            }
        }
        return  Tips.<List<CustomPlanPeriodResult>>empty().data(addPeriodList);
    }
}