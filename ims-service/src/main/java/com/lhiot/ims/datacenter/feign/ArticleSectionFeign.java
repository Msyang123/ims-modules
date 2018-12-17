package com.lhiot.ims.datacenter.feign;

import com.leon.microx.web.result.Pages;
import com.lhiot.ims.datacenter.feign.entity.ArticleSection;
import com.lhiot.ims.datacenter.feign.model.ArticleSectionParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author hufan created in 2018/12/17 17:29
 **/
@Component
@FeignClient("basic-data-service-v1-0")
public interface ArticleSectionFeign {
    /**
     * 添加文章版块
     *
     * @param articleSection
     * @return
     */
    @PostMapping("/article-sections")
    ResponseEntity create(@Valid @RequestBody ArticleSection articleSection);

    /**
     * 修改文章版块
     *
     * @param id
     * @param articleSection
     * @return
     */
    @PutMapping("/article-sections/{id}")
    ResponseEntity update(@PathVariable("id") Long id, @Valid @RequestBody ArticleSection articleSection);


    /**
     * 根据Id查找文章版块
     *
     * @param id
     * @param includeArticles
     * @param includeArticlesQty
     * @return
     */
    @GetMapping("/article-sections/{id}")
    ResponseEntity<ArticleSection> single(@PathVariable("id") Long id,
                                          @RequestParam(value = "includeArticles", required = false) boolean includeArticles,
                                          @RequestParam(value = "includeArticlesQty", required = false) Long includeArticlesQty);


    /**
     * 根据Id删除文章版块
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/article-sections/{ids}")
    ResponseEntity batchDelete(@PathVariable("ids") String ids);


    /**
     * 根据条件分页查询文章版块信息列表
     *
     * @param param
     * @return
     */
    @PostMapping("/article-sections/pages")
    ResponseEntity<Pages<ArticleSection>> search(@RequestBody ArticleSectionParam param);
}
