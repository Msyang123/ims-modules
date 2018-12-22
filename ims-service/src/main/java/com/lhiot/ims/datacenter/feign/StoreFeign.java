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
@FeignClient(value = "basic-data-service-v1-0")
public interface StoreFeign {

    /**
     * 根据id查询门店
     *
     * @param id              门店id
     * @param applicationType 应用类型
     * @return 查询结果
     */
    @GetMapping("/stores/{id}")
    ResponseEntity<Store> findStore(@PathVariable("id") Long id, @RequestParam("applicationType") String applicationType);

    /**
     * 根据门店编码查询门店信息
     *
     * @param code            门店编码
     * @param applicationType 应用类型
     * @return 查询结果
     */
    @GetMapping("/stores/code/{code}")
    ResponseEntity<Store> findStoreByCode(@PathVariable("code") String code, @RequestParam("applicationType") String applicationType);

    /**
     * 添加门店
     *
     * @param store 要添加的门店
     * @return 添加操作的结果
     */
    @PostMapping("/stores")
    ResponseEntity create(@RequestBody Store store);

    /**
     * 根据id更新门店
     *
     * @param id    门店id
     * @param store 要修改的门店
     * @return 修改操作的结果
     */
    @PutMapping("/stores/{id}")
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody Store store);

    /**
     * 根据id删除门店
     *
     * @param id 门店id
     * @return 删除操作结果
     */
    @DeleteMapping("/stores/{id}")
    ResponseEntity deleteById(@PathVariable("id") Long id);

    /**
     * 根据位置查询门店所有列表根据距离排序
     *
     * @param param 分页查询条件
     * @return 分页查询结果
     */
    @PostMapping("/stores/search")
    ResponseEntity<Pages<Store>> search(@RequestBody StoreSearchParam param);
}
