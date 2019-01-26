package com.lhiot.ims.contentcenter.feign;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.contentcenter.feign.entity.Feedback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2019/1/25 17:04
 **/
@FeignClient("content-center-service-v1-0")
@Component
public interface FeedbackFeign {
    /**
     * 用户反馈创建
     *
     * @param feedback 用户反馈
     * @return
     */
    @PostMapping("/feedback")
    ResponseEntity create(@RequestBody Feedback feedback);

    /**
     * 依据id更新用户反馈
     *
     * @param id       主键id
     * @param feedback 用户反馈
     * @return
     */
    @PutMapping("/feedback/{id}")
    ResponseEntity updateById(@PathVariable("id") Long id, @RequestBody Feedback feedback);

    /**
     * 根据id查询用户反馈
     *
     * @param id 主键id
     * @return
     */
    @GetMapping("/feedback/{id}")
    ResponseEntity<Feedback> findFeedback(@PathVariable("id") Long id);

    /**
     * 查询用户反馈分页列表
     *
     * @param feedback 查询用户反馈参数
     * @return
     */
    @PostMapping("/feedback/pages")
    ResponseEntity<Pages<Feedback>> pageQuery(@RequestBody Feedback feedback);

}
