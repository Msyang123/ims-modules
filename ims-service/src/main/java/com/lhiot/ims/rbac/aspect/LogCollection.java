package com.lhiot.ims.rbac.aspect;

import java.lang.annotation.*;

/**
 * @author hufan created in 2018/11/17 8:54
 **/
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LogCollection {
}
