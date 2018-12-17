package com.lhiot.ims.datacenter.feign;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.datacenter.feign.entity.Dictionary;
import com.lhiot.ims.datacenter.feign.entity.DictionaryEntry;
import com.lhiot.ims.datacenter.feign.model.SearchParameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2018/12/17 18:35
 **/
@Component
@FeignClient(value = "basic-data-service-v1-0")
public interface DictionaryFeign {

    /**
     * 添加一个字典
     *
     * @param dictionary
     * @return
     */
    @PostMapping("/dictionaries/")
    ResponseEntity add(@RequestBody Dictionary dictionary);

    /**
     * 删除字典（级联删除字典下所有子项）
     *
     * @param code
     * @return
     */
    @DeleteMapping("/dictionaries/{code}")
    ResponseEntity remove(@PathVariable("code") String code);

    /**
     * 修改字典信息
     *
     * @param code
     * @param dictionary
     * @return
     */
    @PutMapping("/dictionaries/{code}")
    ResponseEntity update(@PathVariable("code") String code, @RequestBody Dictionary dictionary);

    /**
     * 查询一个字典数据
     *
     * @param code
     * @param includeEntries
     * @return
     */
    @GetMapping("/dictionaries/{code}")
    ResponseEntity<Dictionary> dictionary(@PathVariable("code") String code, @RequestParam(value = "includeEntries", required = false) boolean includeEntries);

    /**
     * 分页查询数据字典
     *
     * @param search
     * @return
     */
    @PostMapping("/dictionaries/pages")
    ResponseEntity<Pages<Dictionary>> dictionaries(@RequestBody SearchParameter search);

    /**
     * 给字典添加一个子项
     *
     * @param dictCode
     * @param entry
     * @return
     */
    @PostMapping("/dictionaries/{dictCode}/entries")
    ResponseEntity addEntry(@PathVariable("dictCode") String dictCode, @RequestBody DictionaryEntry entry);


    /**
     * 删除字典子项（如果子项code为空，则删除此字典下所有子项）
     *
     * @param dictCode
     * @param code
     * @return
     */
    @DeleteMapping("/dictionaries/{dictCode}/entries")
    ResponseEntity removeEntry(@PathVariable("dictCode") String dictCode, @RequestParam(value = "code", required = false) String code);

    /**
     * 修改字典子项信息
     *
     * @param dictCode
     * @param code
     * @param entry
     * @return
     */
    @PutMapping("/dictionaries/{dictCode}/entries/{code}")
    ResponseEntity updateEntry(@PathVariable("dictCode") String dictCode, @PathVariable("code") String code, @RequestBody DictionaryEntry entry);
}
