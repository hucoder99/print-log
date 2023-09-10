package com.single.log.annotation;

import java.lang.annotation.*;

/**
 * 打印日志注解
 * 标有当前注解的方法则进行日志打印
 *
 * @author single
 * @date 2022/8/3
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PrintLog {

    /**
     * true只打印输入参数，否则输人和输出都打印
     *
     * @return boolean
     */
    boolean onlyPrintInputParam() default true;

}
