package com.lhiot.ims.datacenter.api;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.swagger.ApiHideBodyProperty;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.datacenter.feign.DictionaryFeign;
import com.lhiot.ims.datacenter.feign.entity.Dictionary;
import com.lhiot.ims.datacenter.feign.entity.DictionaryEntry;
import com.lhiot.ims.datacenter.feign.model.SearchParameter;
import com.lhiot.ims.rbac.aspect.LogCollection;
import com.lhiot.util.FeginResponseTools;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2018/12/17 18:37
 **/
@Api(description = "数据字典接口")
@RestController
@Slf4j
public class DictionaryApi {
    private final DictionaryFeign dictionaryFeign;

    @Autowired
    public DictionaryApi(DictionaryFeign dictionaryFeign) {
        this.dictionaryFeign = dictionaryFeign;
    }

    @LogCollection
    @ApiOperation("添加一个字典")
    @PostMapping("/dictionaries/")
    @ApiHideBodyProperty({"id", "children", "entries"})
    public ResponseEntity add(@RequestBody Dictionary dictionary) {
        log.debug("添加一个字典\t param:{}", dictionary);

        ResponseEntity entity = dictionaryFeign.add(dictionary);
        return FeginResponseTools.convertCreateResponse(entity);
    }

    @LogCollection
    @ApiOperation("删除字典（级联删除字典下所有子项）")
    @DeleteMapping("/dictionaries/{code}")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "code", value = "字典code", required = true, dataType = "String")
    public ResponseEntity remove(@PathVariable("code") String code) {
        log.debug("删除字典（级联删除字典下所有子项）\t param:{}", code);

        ResponseEntity entity = dictionaryFeign.remove(code);
        return FeginResponseTools.convertDeleteResponse(entity);
    }

    @LogCollection
    @PutMapping("/dictionaries/{code}")
    @ApiOperation("修改字典信息")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "code", value = "字典code", required = true, dataType = "String")
    @ApiHideBodyProperty({"id", "code", "children", "entries"})
    public ResponseEntity update(@PathVariable("code") String code, @RequestBody Dictionary dictionary) {
        log.debug("修改字典信息\t id:{} param:{}", code, dictionary);

        ResponseEntity entity = dictionaryFeign.update(code, dictionary);
        return FeginResponseTools.convertUpdateResponse(entity);
    }

    @GetMapping("/dictionaries/{code}")
    @ApiOperation(value = "查询一个字典数据", response = Dictionary.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "code", value = "字典code", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = ApiParamType.QUERY, name = "includeEntries", value = "是否加载字典子项", dataType = "Boolean")
    })
    public ResponseEntity dictionary(@PathVariable("code") String code, @RequestParam(value = "includeEntries", required = false) boolean includeEntries) {
        log.debug("查询一个字典数据\t code:{},param:{}", code, includeEntries);

        ResponseEntity entity = dictionaryFeign.dictionary(code, includeEntries);
        return FeginResponseTools.convertNoramlResponse(entity);
    }

    @ApiOperation("分页查询字典数据")
    @PostMapping("/dictionaries/pages")
    @ApiHideBodyProperty("entryCode")
    @ApiResponses({
            @ApiResponse(code = 0, message = "字典数据", response = Dictionary.class, responseContainer = "Set"),
            @ApiResponse(code = 200, message = "字典分页", response = Pages.class)
    })
    public ResponseEntity dictionaries(@RequestBody SearchParameter search) {
        log.debug("分页查询字典数据\t param:{}", search);

        ResponseEntity entity = dictionaryFeign.dictionaries(search);
        return FeginResponseTools.convertNoramlResponse(entity);
    }

    @LogCollection
    @ApiOperation("给字典添加一个子项")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "dictCode", value = "字典code", required = true, dataType = "String")
    @PostMapping("/dictionaries/{dictCode}/entries")
    @ApiHideBodyProperty("id")
    public ResponseEntity addEntry(@PathVariable("dictCode") String dictCode, @RequestBody DictionaryEntry entry) {
        log.debug("给字典添加一个子项\t code:{}, param:{}", dictCode, entry);

        ResponseEntity entity = dictionaryFeign.addEntry(dictCode, entry);
        return FeginResponseTools.convertNoramlResponse(entity);
    }

    @LogCollection
    @ApiOperation("删除字典子项（如果子项code为空，则删除此字典下所有子项）")
    @DeleteMapping("/dictionaries/{dictCode}/entries")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "dictCode", value = "字典code", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = ApiParamType.QUERY, name = "code", value = "字典子项code", dataType = "String")
    })
    public ResponseEntity removeEntry(@PathVariable("dictCode") String dictCode, @RequestParam(value = "code", required = false) String code) {
        log.debug("删除字典子项（如果子项code为空，则删除此字典下所有子项）\t dictCode:{},code:{}", dictCode, code);

        ResponseEntity entity = dictionaryFeign.removeEntry(dictCode, code);
        return FeginResponseTools.convertDeleteResponse(entity);
    }

    @LogCollection
    @ApiOperation("修改字典子项信息")
    @PutMapping("/dictionaries/{dictCode}/entries/{code}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "dictCode", value = "字典code", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "code", value = "字典子项code", required = true, dataType = "String")
    })
    @ApiHideBodyProperty({"id", "code", "dictCode"})
    public ResponseEntity updateEntry(@PathVariable("dictCode") String dictCode, @PathVariable("code") String code, @RequestBody DictionaryEntry entry) {
        log.debug("修改字典子项信息\t id:{} param:{}, entry:{}", dictCode, code, entry);

        ResponseEntity entity = dictionaryFeign.updateEntry(dictCode, code, entry);
        return FeginResponseTools.convertUpdateResponse(entity);
    }
}