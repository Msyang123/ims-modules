package com.lhiot.ims.datacenter.api;

import com.leon.microx.web.swagger.ApiHideBodyProperty;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.datacenter.feign.ArticleSectionFeign;
import com.lhiot.ims.datacenter.feign.entity.ArticleSection;
import com.lhiot.ims.datacenter.feign.model.ArticleSectionParam;
import com.lhiot.ims.rbac.aspect.LogCollection;
import com.lhiot.util.FeginResponseTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author hufan created in 2018/12/17 18:26
 **/
@Api(description = "文章板块接口")
@RestController
@Slf4j
public class ArticleSectionApi {
    private final ArticleSectionFeign articleSectionFeign;

    @Autowired
    public ArticleSectionApi(ArticleSectionFeign articleSectionFeign) {
        this.articleSectionFeign = articleSectionFeign;
    }

    @LogCollection
    @ApiOperation("添加文章版块")
    @PostMapping("/article-sections")
    @ApiHideBodyProperty({"id", "uiPosition", "createAt", "articleList"})
    public ResponseEntity create(@Valid @RequestBody ArticleSection articleSection) {
        log.debug("添加文章版块\t param:{}", articleSection);

        ResponseEntity entity = articleSectionFeign.create(articleSection);
        return FeginResponseTools.convertCreateResponse(entity);
    }


    @LogCollection
    @ApiOperation("修改文章版块")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "文章版块Id", dataType = "Long", required = true)
    })
    @PutMapping("/article-sections/{id}")
    @ApiHideBodyProperty({"id", "uiPosition", "createAt", "articleList"})
    public ResponseEntity update(@PathVariable("id") Long id, @Valid @RequestBody ArticleSection articleSection) {
        log.debug("修改文章版块\t id:{} param:{}", id, articleSection);


        ResponseEntity entity = articleSectionFeign.update(id, articleSection);
        return FeginResponseTools.convertUpdateResponse(entity);
    }


    @ApiOperation(value = "根据Id查找文章版块", response = ArticleSection.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "文章版块Id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = ApiParamType.QUERY, name = "includeArticles", dataType = "Boolean", value = "是否加载版块下文章信息(为空则默认为false)"),
            @ApiImplicitParam(paramType = ApiParamType.QUERY, name = "includeArticlesQty", dataType = "Long", value = "加载文章最大条数(includeArticles为true起作用，为空则加载所有)")
    })
    @GetMapping("/article-sections/{id}")
    public ResponseEntity single(@PathVariable("id") Long id,
                                 @RequestParam(value = "includeArticles", required = false) boolean includeArticles,
                                 @RequestParam(value = "includeArticlesQty", required = false) Long includeArticlesQty) {
        log.debug("根据Id查找文章版块\t param:{}", id);

        ResponseEntity entity = articleSectionFeign.single(id, includeArticles, includeArticlesQty);
        return FeginResponseTools.convertNoramlResponse(entity);
    }


    @LogCollection
    @ApiOperation("根据Id删除文章版块")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "ids", value = "多个文章版块Id以英文逗号分隔", dataType = "String", required = true)
    @DeleteMapping("/article-sections/{ids}")
    public ResponseEntity batchDelete(@PathVariable("ids") String ids) {
        log.debug("根据Id删除文章版块\t param:{}", ids);

        ResponseEntity entity = articleSectionFeign.batchDelete(ids);
        return FeginResponseTools.convertDeleteResponse(entity);
    }


    @ApiOperation(value = "根据条件分页查询文章版块信息列表", response = ArticleSection.class, responseContainer = "Set")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "查询条件", dataType = "ArticleSectionParam")
    @PostMapping("/article-sections/pages")
    public ResponseEntity search(@RequestBody ArticleSectionParam param) {
        log.debug("根据条件分页查询文章版块信息列表\t param:{}", param);

        ResponseEntity entity = articleSectionFeign.search(param);
        return FeginResponseTools.convertNoramlResponse(entity);
    }
}