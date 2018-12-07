package com.lhiot.ims.datacenter.feign;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.datacenter.feign.entity.Advertisement;
import com.lhiot.ims.datacenter.feign.model.AdvertisementParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2018/11/21 20:10
 **/
@Component
@FeignClient(value = "basic-data-service-v1-0")
public interface AdvertisementFeign {

    /**
     * 添加广告
     */
    @PostMapping(value = "/advertisements")
    ResponseEntity create(@RequestBody Advertisement param);

    /**
     * 修改广告
     */
    @PutMapping(value = "/advertisements/{id}")
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody Advertisement param);

    /**
     * 根据Id查找广告
     */
    @GetMapping(value = "/advertisements/{id}")
    ResponseEntity<Advertisement> findById(@PathVariable("id") Long id);

    /**
     * 根据Ids删除广告
     */
    @DeleteMapping(value = "/advertisements/{ids}")
    ResponseEntity batchDelete(@PathVariable("ids") String ids);


    /**
     * 根据条件分页查询广告信息列表
     */
    @PostMapping(value = "/advertisements/pages")
    ResponseEntity<Pages<Advertisement>> pages(@RequestBody AdvertisementParam param);

}
