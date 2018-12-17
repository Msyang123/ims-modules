package com.lhiot.ims.datacenter.feign;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.datacenter.feign.entity.Store;
import com.lhiot.ims.datacenter.feign.model.StoreSearchParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2018/12/17 16:50
 **/
@Component
@FeignClient(value = "basic-data-service-v1-0-hufan")
public interface StoreFeign {

    /**
     * 根据id查询门店
     *
     * @param id
     * @param applicationType
     * @return
     */
    @GetMapping("/stores/{id}")
    ResponseEntity<Store> findStore(@PathVariable("id") Long id, @RequestParam("applicationType") String applicationType);

    /**
     * 根据门店编码查询门店信息
     *
     * @param code
     * @param applicationType
     * @return
     */
    @GetMapping("/stores/code/{code}")
    ResponseEntity<Store> findStoreByCode(@PathVariable("code") String code, @RequestParam("applicationType") String applicationType);

    /**
     * 添加门店
     *
     * @param store
     * @return
     */
    @PostMapping("/stores")
    ResponseEntity create(@RequestBody Store store);

    /**
     * 根据id更新门店
     *
     * @param id
     * @param store
     * @return
     */
    @PutMapping("/stores/{id}")
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody Store store);

    /**
     * 根据id删除门店
     *
     * @param id
     * @return
     */
    @DeleteMapping("/stores/{id}")
    ResponseEntity deleteById(@PathVariable("id") Long id);

    /**
     * 根据位置查询门店所有列表根据距离排序
     *
     * @param param
     * @return
     */
    @PostMapping("/stores/search")
    ResponseEntity<Pages<Store>> search(@RequestBody StoreSearchParam param);
}
