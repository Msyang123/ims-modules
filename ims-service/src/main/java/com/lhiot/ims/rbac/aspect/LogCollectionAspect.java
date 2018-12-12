package com.lhiot.ims.rbac.aspect;

import com.leon.microx.web.session.Sessions;
import com.lhiot.ims.rbac.domain.ImsOperationLog;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author hufan created in 2018/11/17 9:01
 **/
@Aspect
@Component
@Slf4j
public class LogCollectionAspect {
    private ApplicationEventPublisher publisher;
    private final Integer MAX_CONTENT_LENGTH = 2048;

    @Autowired
    public LogCollectionAspect(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    /**
     * 切面
     *
     * @param joinPoint
     */
    @AfterReturning(returning = "result", pointcut = "@annotation(logCollection)")
    public void afterReturn(JoinPoint joinPoint, ResponseEntity result, LogCollection logCollection) throws Exception {
        log.info("LogCollectionAspect");

        if (result.getStatusCode().equals(HttpStatus.OK) && result.hasBody()) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            // 有格式的打印日志，收集日志信息到ES
            log.info(System.currentTimeMillis() + "|" + request.getRequestURL().toString() + "|" + request.getMethod() + "|" +
                    joinPoint.getSignature().getDeclaringTypeName() + "|" + Arrays.toString(joinPoint.getArgs()));


            String content = "请求URL：" + request.getRequestURL() + " ==> 请求类型：" + request.getMethod() + " ==> 请求参数：" + joinPoint.getArgs()[0].toString() + " ==> 返回结果：" + result.getBody();
            if (content.length() > MAX_CONTENT_LENGTH-1) {
                content = content.substring(0,2047);
            }
            String ip = request.getRemoteAddr();
            String description = descriptionExpression(joinPoint);
            Sessions.User sessionUser = Arrays.stream(joinPoint.getArgs()).filter(para -> para instanceof Sessions.User).findFirst().map(para -> (Sessions.User) para).orElse(null);

            long userId = 0L;
            if (Objects.nonNull(sessionUser)) {
                userId = (long) sessionUser.getUser().get("id");
            }
            ImsOperationLog imsOperationLog = new ImsOperationLog();
            imsOperationLog.setContent(content);
            imsOperationLog.setUserId(userId);
            imsOperationLog.setIp(ip);
            imsOperationLog.setDescription(description);
            publisher.publishEvent(imsOperationLog);
        }

    }

    /**
     * 获取api方法中的中文描述
     *
     * @param joinPoint
     * @return
     * @throws Exception
     */
    public static String descriptionExpression(JoinPoint joinPoint) throws Exception {

        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();

        Class targetClass = Class.forName(targetName);
        Method[] method = targetClass.getMethods();
        String methode = "";
        for (Method m : method) {
            if (m.getName().equals(methodName)) {
                Class[] tmpCs = m.getParameterTypes();
                if (tmpCs.length == arguments.length) {
                    ApiOperation methodCache = m.getAnnotation(ApiOperation.class);
                    if (methodCache != null) {
                        methode = methodCache.value();
                    }
                    break;
                }
            }
        }
        return methode;
    }

}