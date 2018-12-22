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
     *
     * @param param 要添加的广告信息
     * @return 添加操作结果
     */
    @PostMapping(value = "/advertisements")
    ResponseEntity create(@RequestBody Advertisement param);

    /**
     * 修改广告
     *
     * @param id    广告id
     * @param param 要修改的广告
     * @return 修改操作结果
     */
    @PutMapping(value = "/advertisements/{id}")
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody Advertisement param);

    /**
     * 根据Id查找广告
     *
     * @param id 广告id
     * @return Advertisement 广告结果
     */
    @GetMapping(value = "/advertisements/{id}")
    ResponseEntity<Advertisement> findById(@PathVariable("id") Long id);

    /**
     * 根据Ids删除广告
     *
     * @param ids 要删除的广告ids，英文逗号分隔
     * @return 删除操作结果
     */
    @DeleteMapping(value = "/advertisements/{ids}")
    ResponseEntity batchDelete(@PathVariable("ids") String ids);

    /**
     * 根据条件分页查询广告信息列表
     *
     * @param param 分页查询的条件
     * @return 分页查询结果
     */
    @PostMapping(value = "/advertisements/pages")
    ResponseEntity<Pages<Advertisement>> pages(@RequestBody AdvertisementParam param);

}
