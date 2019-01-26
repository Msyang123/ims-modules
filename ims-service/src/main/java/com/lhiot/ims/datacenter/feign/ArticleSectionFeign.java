package com.lhiot.ims.datacenter.feign;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.datacenter.feign.entity.ArticleSection;
import com.lhiot.ims.datacenter.feign.entity.ArticleSectionRelation;
import com.lhiot.ims.datacenter.feign.model.ArticleSectionParam;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
     * @param articleSection 要添加的文章板块
     * @return 添加操作结果
     */
    @PostMapping("/article-sections")
    ResponseEntity create(@Valid @RequestBody ArticleSection articleSection);

    /**
     * 修改文章版块
     *
     * @param id             文章板块id
     * @param articleSection 要修改的文章板块
     * @return 修改操作结果
     */
    @PutMapping("/article-sections/{id}")
    ResponseEntity update(@PathVariable("id") Long id, @Valid @RequestBody ArticleSection articleSection);


    /**
     * 根据Id查找文章版块
     *
     * @param id                 文章板块id
     * @param includeArticles    是否包含文章信息
     * @param includeArticlesQty 包含的文章最大条数
     * @return ArticleSection 文章板块信息
     */
    @GetMapping("/article-sections/{id}")
    ResponseEntity<ArticleSection> single(@PathVariable("id") Long id,
                                          @RequestParam(value = "includeArticles", required = false) boolean includeArticles,
                                          @RequestParam(value = "includeArticlesQty", required = false) Long includeArticlesQty);


    /**
     * 根据Id删除文章版块
     *
     * @param ids 要删除的文章板块ids，英文逗号分隔
     * @return 删除操作的结果
     */
    @DeleteMapping("/article-sections/{ids}")
    ResponseEntity batchDelete(@PathVariable("ids") String ids);


    /**
     * 根据条件分页查询文章版块信息列表
     *
     * @param param 分页查询的条件
     * @return 分页查询结果
     */
    @PostMapping("/article-sections/pages")
    ResponseEntity<Pages<ArticleSection>> search(@RequestBody ArticleSectionParam param);

    /**
     * 添加文章版块与文章关系
     * @param articleSectionRelation
     * @return
     */
    @ApiOperation("添加文章版块与文章关系")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "articleSectionRelation", value = "文章版块与文章关系信息", dataType = "ArticleSectionRelation", required = true)
    @PostMapping("/article-section-relations")
    ResponseEntity createRelation(@RequestBody ArticleSectionRelation articleSectionRelation) ;

    /**
     * 批量删除版块与文章关系
     *
     * @param sectionId  文章版块Id
     * @param articleIds 多个文章Id以英文逗号分隔,为空则删除此版块所有文章关系
     * @return 删除操作结果
     */
    @DeleteMapping("/article-section-relations/batches")
    ResponseEntity deleteBatch(@RequestParam("sectionId") Long sectionId, @RequestParam(value = "articleIds", required = false) String articleIds);
}
