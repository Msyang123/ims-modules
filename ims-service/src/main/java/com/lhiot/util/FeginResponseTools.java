package com.lhiot.util;

import com.leon.microx.util.Maps;
import com.leon.microx.web.result.Tips;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

@Slf4j
public class FeginResponseTools {

    /**
     * 获取基础服务调用返回的responseEntity转换成Tips对象
     *
     * @param responseEntity
     * @param <T>
     * @return
     */
    public static <T> Tips<T> convertResponse(ResponseEntity<T> responseEntity) {

        if (Objects.isNull(responseEntity) || responseEntity.getStatusCode().isError()) {
            log.error("调用基础服务失败:{}", responseEntity);
            return Tips.warn((Objects.isNull(responseEntity) ? "服务内部错误" : (String) responseEntity.getBody()));
        }
        return new Tips<T>().data(responseEntity.getBody());
    }

    /**
     * 依据tips标准编码将tips转换成对应ResponseEntity对象
     *
     * @param tips
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity<Tips> returnTipsResponse(Tips<T> tips) {
        if (tips.err()) {
            return ResponseEntity.badRequest().body(tips);
        }
        return ResponseEntity.ok(tips);
    }

    /**
     * 获取基础服务新增调用返回的responseEntity 返回ResponseEntity对象
     *
     * @param responseEntity
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity convertCreateResponse(ResponseEntity<T> responseEntity) {

        if (Objects.isNull(responseEntity) || responseEntity.getStatusCode().isError()) {
            log.error("调用基础服务失败:{}", responseEntity);
            return ResponseEntity.badRequest().body(Objects.isNull(responseEntity) ? "服务内部错误" : (String) responseEntity.getBody());
        }
        String location = responseEntity.getHeaders().getLocation().toString();
        Long id = Long.valueOf(location.substring(location.lastIndexOf('/') + 1));
        return id > 0 ? ResponseEntity.created(responseEntity.getHeaders().getLocation()).body(Maps.of("id", id)) : ResponseEntity.badRequest().body(responseEntity.getBody());
    }

    /**
     * 获取基础服务修改调用返回的responseEntity 返回ResponseEntity对象
     *
     * @param responseEntity
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity convertUpdateResponse(ResponseEntity<T> responseEntity) {

        if (Objects.isNull(responseEntity) || responseEntity.getStatusCode().isError()) {
            log.error("调用基础服务失败:{}", responseEntity);
            return ResponseEntity.badRequest().body(Objects.isNull(responseEntity) ? "服务内部错误" : (String) responseEntity.getBody());
        }
        return ResponseEntity.ok().build();
    }

    /**
     * 获取基础服务删除调用返回的responseEntity 返回ResponseEntity对象
     *
     * @param responseEntity
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity convertDeleteResponse(ResponseEntity<T> responseEntity) {

        if (Objects.isNull(responseEntity) || responseEntity.getStatusCode().isError()) {
            log.error("调用基础服务失败:{}", responseEntity);
            return ResponseEntity.badRequest().body(Objects.isNull(responseEntity) ? "服务内部错误" : (String) responseEntity.getBody());
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * 获取基础服务删除调用返回的responseEntity 返回ResponseEntity对象
     *
     * @param responseEntity
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity convertSelectResponse(ResponseEntity<T> responseEntity) {

        if (Objects.isNull(responseEntity) || responseEntity.getStatusCode().isError()) {
            log.error("调用基础服务失败:{}", responseEntity);
            return ResponseEntity.badRequest().body(Objects.isNull(responseEntity) ? "服务内部错误" : (String) responseEntity.getBody());
        }
        return ResponseEntity.ok(responseEntity.getBody());
    }
}
