package com.lhiot.ims.healthygood.feign.user;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.healthygood.feign.user.entity.SettlementApplication;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author hufan created in 2018/12/7 11:43
 **/
@FeignClient(value = "healthy-good-service-v2-0-hufan")
@Component
public interface FruitDoctorSettlementFeign {

    /**
     * 结算申请修改
     *
     * @param id                    结算申请id
     * @param settlementApplication 要修改的结算申请
     * @return 修改操作的结果
     */
    @PutMapping("/fruit-doctors/settlement/{id}")
    ResponseEntity updateSettlement(@PathVariable("id") Long id, @RequestBody SettlementApplication settlementApplication);

    /**
     * 结算申请分页查询
     *
     * @param settlementApplication 分页查询条件
     * @return 分页查询结果
     */
    @PostMapping("/fruit-doctors/settlement/pages")
    ResponseEntity<Pages<SettlementApplication>> search(@RequestBody SettlementApplication settlementApplication);

    /**
     * 结算申请退款
     * @param id 结算申请id
     * @return 结算申请退款的结果
     */
    @PutMapping("/fruit-doctors/settlement/refund/{id}")
    ResponseEntity refund(@PathVariable("id") Long id);
}
