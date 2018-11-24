package com.lhiot.ims.rbac.aspect;

import com.leon.microx.util.Maps;
import com.leon.microx.web.session.Sessions;
import com.lhiot.ims.rbac.service.ImsOperationLogService;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LogCollectionAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogCollectionAspect.class);
    private final ImsOperationLogService imsOperationLogService;

    @Autowired
    public LogCollectionAspect(ImsOperationLogService imsOperationLogService) {
        this.imsOperationLogService = imsOperationLogService;
    }

    /**
     * 切点
     * 定义拦截规则：拦截包下面的所有类中，有@PostMapping注解的方法
     */
    @Pointcut("@annotation(com.lhiot.ims.rbac.aspect.LogCollection)")
    public void MethodPointcut() {
    }

    /**
     * 切面
     *
     * @param joinPoint
     */
    @Around("MethodPointcut()")
    public void doBefore(ProceedingJoinPoint joinPoint) throws Exception {
        logger.info("LogCollectionAspect");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 有格式的打印日志，收集日志信息到ES
        logger.info(System.currentTimeMillis() + "|" + request.getRequestURL().toString() + "|" + request.getMethod() + "|" +
                joinPoint.getSignature().getDeclaringTypeName() + "|" + Arrays.toString(joinPoint.getArgs()));

        String content = "请求URL：" + request.getRequestURL() + " ==> 请求类型：" + request.getMethod() + " ==> 请求参数：" + joinPoint.getArgs()[0].toString();
        Sessions.User sessionUser = Arrays.stream(joinPoint.getArgs()).filter(para -> para instanceof Sessions.User).findFirst().map(para -> (Sessions.User) para).orElse(null);

        long userId = 0L;
        if (Objects.nonNull(sessionUser)) {
            userId = (long)sessionUser.getUser().get("id");
        }
        // 写操作日志
        imsOperationLogService.create(Maps.of("content", content, "userId", userId, "ip", request.getRemoteAddr(), "description", descriptionExpression(joinPoint)));

    }

    /**
     * 获取api方法中的中文描述
     *
     * @param joinPoint
     * @return
     * @throws Exception
     */
    public static String descriptionExpression(ProceedingJoinPoint joinPoint) throws Exception {

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