package com.lhiot.ims.healthygood.feign.user;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.healthygood.feign.user.entity.FruitDoctor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2018/12/6 20:06
 **/
@FeignClient(value = "healthy-good-service-v2-0-hufan")
@Component
public interface FruitDoctorFeign {
    /**
     * 查询鲜果师成员分页列表
     *
     * @param fruitDoctor 分页查询条件
     * @return 分页查询结果
     */
    @PostMapping("/fruit-doctors/pages")
    ResponseEntity<Pages<FruitDoctor>> search(@RequestBody FruitDoctor fruitDoctor);

    /**
     * 查询鲜果师成员详情
     *
     * @param id 鲜果师id
     * @return 查询结果
     */
    @GetMapping("/fruit-doctors/{id}")
    ResponseEntity<FruitDoctor> findById(@PathVariable("id") Long id);

    /**
     * 修改鲜果师成员信息
     *
     * @param id          鲜果师id
     * @param fruitDoctor 要修改的鲜果师成员
     * @return 修改操作的结果
     */
    @PutMapping("/fruit-doctors/{id}")
    ResponseEntity updateById(@PathVariable("id") Long id, @RequestBody FruitDoctor fruitDoctor);

}
