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
     * @param article 要添加的文章
     * @return 添加操作结果
     */
    @PostMapping("/articles")
    ResponseEntity create(@RequestBody Article article);


    /**
     * 修改文章
     *
     * @param id 文章id
     * @param article 要修改的文章
     * @return 修改操作结果
     */
    @PutMapping("/articles/{id}")
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody Article article);


    /**
     * 根据Id查找文章
     *
     * @param id 文章id
     * @param addReadAmount 是否增加阅读数
     * @return Article 文章结果
     */
    @GetMapping("/articles/{id}")
    ResponseEntity<Article> single(@PathVariable("id") Long id, @RequestParam(value = "addReadAmount", required = false) boolean addReadAmount);


    /**
     * 根据Ids删除文章
     *
     * @param ids 要删除的文章ids，英文逗号分隔
     * @return 批量删除操作的结果
     */
    @DeleteMapping("/articles/{ids}")
    ResponseEntity batchDelete(@PathVariable("ids") String ids);

    /**
     * 根据条件分页查询文章信息列表
     *
     * @param param 分页查询条件
     * @return 分页查询结果
     */
    @PostMapping("/articles/pages")
    ResponseEntity<Pages<Article>> search(@RequestBody ArticleParam param);
}
