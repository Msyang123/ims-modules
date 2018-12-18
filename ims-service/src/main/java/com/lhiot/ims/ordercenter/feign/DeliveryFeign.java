package com.lhiot.ims.ordercenter.feign;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.ordercenter.feign.entity.DeliverNote;
import com.lhiot.ims.ordercenter.feign.model.DeliverFeeRuleParam;
import com.lhiot.ims.ordercenter.feign.model.DeliverFeeRulesResult;
import com.lhiot.ims.ordercenter.feign.model.DeliverFeeSearchParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2018/12/13 12:06
 **/
@FeignClient(value = "delivery-service-v1-0")
@Component
public interface DeliveryFeign {
    /**
     * 查询配送单详情
     *
     * @param code 配送单编码 为海鼎订单编码
     * @return 查询结果
     */
    @GetMapping("/delivery-notes/{code}")
    ResponseEntity<DeliverNote> localDetail(@PathVariable("code") String code);

    /**
     * 添加配送费计算规则
     *
     * @param deliverFeeRuleParam 要添加的配送费计算规则
     * @return 添加操作的结果
     */
    @PostMapping("/delivery-fee-rule")
    ResponseEntity createRule(@RequestBody DeliverFeeRuleParam deliverFeeRuleParam);

    /**
     * 修改配送费计算规则
     *
     * @param ruleId              配送费计算规则id
     * @param deliverFeeRuleParam 要修改的配送费计算规则
     * @return 修改操作的结果
     */
    @PutMapping("/delivery-fee-rule/{id}")
    ResponseEntity updateRules(@PathVariable("id") Long ruleId, @RequestBody DeliverFeeRuleParam deliverFeeRuleParam);

    /**
     * 后台管理查询配送费规则列表
     *
     * @param param 分页查询条件
     * @return 分页查询结果
     */
    @PostMapping("/delivery-fee-rule/query")
    ResponseEntity<Pages<DeliverFeeRulesResult>> query(@RequestBody DeliverFeeSearchParam param);

    /**
     * 根据配送费详细规则Id删除
     *
     * @param id 配送费计算规则id
     * @return 查询结果
     */
    @DeleteMapping("/delivery-fee-rule/detail/{id}")
    ResponseEntity deleteDetail(@PathVariable("id") Long id);

    /**
     * 根据配送费规则模板Id删除
     *
     * @param id 配送费计算规则id
     * @return 删除操作结果
     */
    @DeleteMapping("/delivery-fee-rule/{id}")
    ResponseEntity deleteRule(@PathVariable("id") Long id);
}
