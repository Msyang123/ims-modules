package com.lhiot.ims.healthygood.feign.user;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.healthygood.feign.user.entity.RegisterApplication;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author hufan created in 2018/12/6 12:19
 **/
@FeignClient(value = "healthy-good-service-v2-0")
@Component
public interface FruitDoctorQualificationFeign {

    /**
     * 根据id更新鲜果师申请记录
     *
     * @param id                  鲜果师申请id
     * @param registerApplication 要修改的鲜果师申请记录
     * @return 修改操作的结果
     */

    @PutMapping("/fruit-doctors/qualifications/{id}")
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody RegisterApplication registerApplication);

    /**
     * 根据条件分页查询鲜果师申请记录列表
     *
     * @param registerApplication 分页查询条件
     * @return 分页查询结果
     */
    @PostMapping("/fruit-doctors/qualifications/pages")
    ResponseEntity<Pages<RegisterApplication>> search(@RequestBody RegisterApplication registerApplication);
}