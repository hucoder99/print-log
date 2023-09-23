package com.single.log.util;

import cn.hutool.json.JSONUtil;
import com.single.log.annotation.ResponseResult;
import com.single.log.module.R;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @desc 接口响应体处理器
 */
@ControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {
	/**
	 * 判断哪些需要拦截
	 */
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		ResponseResult responseResult = getResponseResult(returnType);
		return Objects.nonNull(responseResult) && responseResult.isWrap();
	}

	public ResponseResult getResponseResult(MethodParameter returnType) {
		Class<?> clazz = returnType.getContainingClass();
		Method method = returnType.getMethod();
		if (method != null && method.isAnnotationPresent(ResponseResult.class)) {
			return method.getAnnotation(ResponseResult.class);
		} else if (clazz.isAnnotationPresent(ResponseResult.class)) {
			return clazz.getAnnotation(ResponseResult.class);
		}
		return null;
	}


	@Override
	public Object beforeBodyWrite(Object body,
								  MethodParameter returnType,
								  MediaType selectedContentType,
								  Class<? extends HttpMessageConverter<?>> selectedConverterType,
								  ServerHttpRequest request,
								  ServerHttpResponse response) {

		ResponseResult responseResultAnn = getResponseResult(returnType);
		Class<?> resultClazz = responseResultAnn.value();
		if (resultClazz.isAssignableFrom(R.class)) {
			if (body instanceof String) {
				return JSONUtil.parseObj(R.ok(body)).toString();
			} else if (body instanceof R) {
				return body;
			} else {
				return R.ok(body);
			}
		}
		return body;
	}


}
