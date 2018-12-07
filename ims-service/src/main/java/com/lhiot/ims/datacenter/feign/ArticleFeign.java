package com.lhiot.ims.datacenter.feign;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.datacenter.feign.entity.Article;
import com.lhiot.ims.datacenter.feign.model.ArticleParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2018/12/7 18:44
 **/
@Component
@FeignClient(value = "basic-data-service-v1-0")
public interface ArticleFeign {
    /**
     * 添加文章
     *
     * @param article
     * @return
     */
    @PostMapping("/articles")
    ResponseEntity create(@RequestBody Article article);


    /**
     * 修改文章
     *
     * @param id
     * @param article
     * @return
     */
    @PutMapping("/articles/{id}")
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody Article article);


    /**
     * 根据Id查找文章
     *
     * @param id
     * @param addReadAmount
     * @return
     */
    @GetMapping("/articles/{id}")
    ResponseEntity<Article> single(@PathVariable("id") Long id, @RequestParam(value = "addReadAmount", required = false) boolean addReadAmount);


    /**
     * 根据Id删除文章
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/articles/{ids}")
    ResponseEntity batchDelete(@PathVariable("ids") String ids);

    /**
     * 根据条件分页查询文章信息列表
     *
     * @param param
     * @return
     */
    @PostMapping("/articles/pages")
    ResponseEntity<Pages<Article>> search(@RequestBody ArticleParam param);
}
