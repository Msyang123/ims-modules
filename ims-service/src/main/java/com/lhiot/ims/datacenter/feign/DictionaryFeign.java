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
     * @param dictionary 要添加的数据字典
     * @return 添加操作的结果
     */
    @PostMapping("/dictionaries/")
    ResponseEntity add(@RequestBody Dictionary dictionary);

    /**
     * 删除字典（级联删除字典下所有子项）
     *
     * @param code 要删除的字典编码
     * @return 删除操作结果
     */
    @DeleteMapping("/dictionaries/{code}")
    ResponseEntity remove(@PathVariable("code") String code);

    /**
     * 修改字典信息
     *
     * @param code 数据字典编码
     * @param dictionary 要修改的数据字典
     * @return 修改操作的结果
     */
    @PutMapping("/dictionaries/{code}")
    ResponseEntity update(@PathVariable("code") String code, @RequestBody Dictionary dictionary);

    /**
     * 查询一个字典数据
     *
     * @param code 数据字典编码
     * @param includeEntries 是否包含字典实体
     * @return Dictionary 字典信息
     */
    @GetMapping("/dictionaries/{code}")
    ResponseEntity<Dictionary> dictionary(@PathVariable("code") String code, @RequestParam(value = "includeEntries", required = false) boolean includeEntries);

    /**
     * 分页查询数据字典
     *
     * @param search 分页查询条件
     * @return <Pages<Dictionary> 分页查询结果
     */
    @PostMapping("/dictionaries/pages")
    ResponseEntity<Pages<Dictionary>> dictionaries(@RequestBody SearchParameter search);

    /**
     * 给字典添加一个子项
     *
     * @param dictCode 字典编码
     * @param entry 实体信息
     * @return 添加操作结果
     */
    @PostMapping("/dictionaries/{dictCode}/entries")
    ResponseEntity addEntry(@PathVariable("dictCode") String dictCode, @RequestBody DictionaryEntry entry);


    /**
     * 删除字典子项（如果子项code为空，则删除此字典下所有子项）
     *
     * @param dictCode 字典编码
     * @param code 实体编码
     * @return 删除操作结果
     */
    @DeleteMapping("/dictionaries/{dictCode}/entries")
    ResponseEntity removeEntry(@PathVariable("dictCode") String dictCode, @RequestParam(value = "code", required = false) String code);

    /**
     * 修改字典子项信息
     *
     * @param dictCode 字典编码
     * @param code 实体编码
     * @param entry 实体信息
     * @return 修改操作结果
     */
    @PutMapping("/dictionaries/{dictCode}/entries/{code}")
    ResponseEntity updateEntry(@PathVariable("dictCode") String dictCode, @PathVariable("code") String code, @RequestBody DictionaryEntry entry);
}
