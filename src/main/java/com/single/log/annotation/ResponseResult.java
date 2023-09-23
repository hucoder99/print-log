package com.single.log.annotation;


import com.single.log.module.R;

import java.lang.annotation.*;

/**
 * @author single
 * 接口返回结果增强  会通过拦截器拦截后放入标记，在WebResponseBodyHandler进行结果处理
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseResult {

	/**
	 * 包装结果类型
	 *
	 * @return Class
	 */
	Class<?> value() default R.class;

	/**
	 * 是否包装结果，默认为true
	 *
	 * @return 包装结果返回true
	 */
	boolean isWrap() default true;

}
