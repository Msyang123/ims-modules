package com.lhiot.ims.datacenter.api;

import com.leon.microx.util.Maps;
import com.leon.microx.web.result.Pages;
import com.leon.microx.web.result.Tips;
import com.leon.microx.web.swagger.ApiHideBodyProperty;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.datacenter.feign.ArticleFeign;
import com.lhiot.ims.datacenter.feign.entity.Article;
import com.lhiot.ims.datacenter.feign.model.ArticleParam;
import com.lhiot.ims.rbac.aspect.LogCollection;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author hufan created in 2018/12/7 18:45
 **/
@Api(description = "文章管理接口")
@Slf4j
@RestController
public class ArticleApi {
    private final ArticleFeign articleFeign;

    public ArticleApi(ArticleFeign articleFeign) {
        this.articleFeign = articleFeign;
    }

    @LogCollection
    @ApiOperation("添加文章")
    @PostMapping("/articles")
    public ResponseEntity create(@RequestBody Article article) {
        log.debug("添加文章\t param:{}", article);

        ResponseEntity entity = articleFeign.create(article);
        if (entity.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(Tips.warn(entity.getBody().toString()));
        }
        String location = entity.getHeaders().getLocation().toString();
        Long id = Long.valueOf(location.substring(location.lastIndexOf('/') + 1));
        return ResponseEntity.created(entity.getHeaders().getLocation()).body(Maps.of("id", id));
    }


    @LogCollection
    @ApiOperation("修改文章")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "文章Id", dataType = "Long", required = true)
    })
    @PutMapping("/articles/{id}")
    @ApiHideBodyProperty("id")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody Article article) {
        log.debug("修改文章\t id:{} param:{}", id, article);

        ResponseEntity entity = articleFeign.update(id, article);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(Tips.warn(entity.getBody().toString())) : ResponseEntity.ok().build();

    }


    @ApiOperation(value = "根据Id查找文章", response = Article.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "文章Id", dataType = "Long", required = true)
    })
    @GetMapping("/articles/{id}")
    public ResponseEntity single(@PathVariable("id") Long id) {
        log.debug("根据Id查找商品规格\t id:{}", id);

        ResponseEntity<Article> entity = articleFeign.single(id, false);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(Tips.warn(entity.getBody().toString())) : ResponseEntity.ok(entity.getBody());

    }


    @LogCollection
    @ApiOperation("根据Id删除文章")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "ids", value = "多个文章Id以英文逗号分隔", dataType = "String", required = true)
    @DeleteMapping("/articles/{ids}")
    public ResponseEntity batchDelete(@PathVariable("ids") String ids) {
        log.debug("根据Id删除文章\t param:{}", ids);

        ResponseEntity entity = articleFeign.batchDelete(ids);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(entity.getBody()) : ResponseEntity.noContent().build();
    }


    @ApiOperation(value = "根据条件分页查询文章信息列表", response = Article.class, responseContainer = "Set")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.BODY, name = "param", value = "查询条件", dataType = "ArticleParam")
    })
    @PostMapping("/articles/pages")
    public ResponseEntity search(@RequestBody ArticleParam param) {
        log.debug("查询商品规格信息列表\t param:{}", param);

        ResponseEntity<Pages<Article>> entity = articleFeign.search(param);
        return entity.getStatusCode().isError() ? ResponseEntity.badRequest().body(Tips.warn(entity.getBody().toString())) : ResponseEntity.ok(entity.getBody());
    }
}