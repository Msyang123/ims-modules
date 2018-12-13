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
     * @param id
     * @param settlementApplication
     * @return
     */
    @PutMapping("/fruit-doctors/settlement/{id}")
    ResponseEntity updateSettlement(@PathVariable("id") Long id, @RequestBody SettlementApplication settlementApplication);

    /**
     * 结算申请分页查询
     *
     * @param settlementApplication
     * @return
     */
    @PostMapping("/fruit-doctors/settlement/pages")
    ResponseEntity<Pages<SettlementApplication>> search(@RequestBody SettlementApplication settlementApplication);
}
