package com.lhiot.ims.datacenter.feign;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.datacenter.feign.entity.Advertisement;
import com.lhiot.ims.datacenter.model.AdvertisementParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author hufan created in 2018/11/21 20:10
 **/
@Component
@FeignClient(value = "basic-data-service-v1-0-hufan")
public interface AdvertisementFeign {

    /**
     * 添加广告
     */
    @RequestMapping(value = "/advertisements", method = RequestMethod.POST)
    ResponseEntity create(@RequestBody Advertisement param);

    /**
     * 修改广告
     */
    @RequestMapping(value = "/advertisements/{id}", method = RequestMethod.PUT)
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody Advertisement param);

    /**
     * 根据Id查找广告
     */
    @RequestMapping(value = "/advertisements/{id}", method = RequestMethod.GET)
    ResponseEntity<Advertisement> findById(@PathVariable("id") Long id);

    /**
     * 根据Ids删除广告
     */
    @RequestMapping(value = "/advertisements/{ids}", method = RequestMethod.DELETE)
    ResponseEntity batchDelete(@PathVariable("ids") String ids);


    /**
     * 根据条件分页查询广告信息列表
     */
    @RequestMapping(value = "/advertisements/pages", method = RequestMethod.POST)
    ResponseEntity<Pages<Advertisement>> pages(@RequestBody AdvertisementParam param);

}
