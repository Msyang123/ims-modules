package com.lhiot.ims.datacenter.feign;

import com.leon.microx.web.result.Pages;

import com.lhiot.ims.datacenter.feign.entity.UiPosition;
import com.lhiot.ims.datacenter.model.UiPositionParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author hufan created in 2018/11/21 19:56
 **/
@Component
@FeignClient(value = "basic-data-service-v1-0-hufan")
public interface UiPositionFeign {

    /**
     * 添加位置
     */
    @RequestMapping(value = "/ui-positions", method = RequestMethod.POST)
    ResponseEntity create(@RequestBody UiPosition param);

    /**
     * 修改位置
     */
    @RequestMapping(value = "/ui-positions/{id}", method = RequestMethod.PUT)
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody UiPosition param);

    /**
     * 根据Id查找位置
     */
    @RequestMapping(value = "/ui-positions/{id}", method = RequestMethod.GET)
    ResponseEntity<UiPosition> findById(@PathVariable("id") Long id);

    /**
     * 根据Ids删除位置
     */
    @RequestMapping(value = "/ui-positions/{ids}", method = RequestMethod.DELETE)
    ResponseEntity batchDelete(@PathVariable("ids") String ids);


    /**
     * 根据条件分页查询位置信息列表
     */
    @RequestMapping(value = "/ui-positions/pages", method = RequestMethod.POST)
    ResponseEntity<Pages<UiPosition>> pages(@RequestBody UiPositionParam param);
}
