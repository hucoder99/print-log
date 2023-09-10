package com.single.log.wrapper;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * 用于获取request参数
 *
 * @author single
 * @date 2022/8/3
 */
public class RequestParamWrapper {

	public final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 请求参数
	 */
	private String params;
	/**
	 * 经过包装的请求，获取json参数会用到
	 */
	private RequestWrapper requestWrapper;


	public RequestParamWrapper(HttpServletRequest request) {
		init(request);
	}

	private void init(HttpServletRequest request) {
		if (Objects.isNull(request)) {
			return;
		}
		// 设置请求参数
		Map<String, String[]> parameterMap = request.getParameterMap();
		this.params = JSONUtil.toJsonStr(parameterMap);

		// 获取json格式参数
		String contentType = request.getHeader("content-type");
		if (StrUtil.isEmpty(contentType)) {
			return;
		}

		try {
			if (contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
				setJsonParam(request);
			}
		} catch (Throwable ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	/**
	 * 获取json格式参数
	 *
	 * @param request HttpServletRequest
	 */
	private void setJsonParam(HttpServletRequest request) {
		this.requestWrapper = new RequestWrapper(request);

		String bodyString = this.requestWrapper.getRequestBody();
		if (StrUtil.isNotEmpty(bodyString)) {
			this.params = bodyString;
		}
	}

	public String getParams() {
		return params;
	}

	public RequestWrapper getRequestWrapper() {
		return requestWrapper;
	}

}
