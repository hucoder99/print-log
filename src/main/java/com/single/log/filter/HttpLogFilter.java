package com.single.log.filter;


import com.google.common.base.Stopwatch;
import com.single.log.module.HttpLogInfo;
import com.single.log.wrapper.RequestParamWrapper;
import com.single.log.wrapper.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * HTTP日志过滤器
 *
 * @author single
 * @date 2022/8/3
 */
public class HttpLogFilter implements Filter {

	public final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * true只打印请求日志，false都打印
	 */
	private final boolean onlyPrintReqLog;

	public HttpLogFilter(boolean onlyPrintReqLog) {
		this.onlyPrintReqLog = onlyPrintReqLog;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		Stopwatch stopwatch = Stopwatch.createStarted();

		HttpLogInfo logInfo = new HttpLogInfo();
		HttpServletRequest wrapperHttpServletRequest = printReqLog(request, logInfo);

		// 对response进行包装，获取返回值
		ResponseWrapper responseWrapper = new ResponseWrapper(response);
		// 执行业务逻辑
		executeBiz(filterChain, wrapperHttpServletRequest, stopwatch, logInfo, responseWrapper);

		logInfo.setDuration(stopwatch.elapsed(TimeUnit.MILLISECONDS));
		// 处理响应并打印日志
		resetResponseAndPrintLog(response, logInfo, responseWrapper);
	}

	private void executeBiz(FilterChain filterChain,
							HttpServletRequest request,
							Stopwatch stopwatch,
							HttpLogInfo logInfo,
							ResponseWrapper responseWrapper) throws IOException, ServletException {

		boolean hadEx = false;
		try {
			// 执行业务逻辑
			filterChain.doFilter(request, responseWrapper);
		} catch (Throwable throwable) {
			hadEx = true;
			printErrorLog(stopwatch, logInfo, throwable);
			throw throwable;
		} finally {
			if (hadEx) {
				responseWrapper.close();
			}
		}
	}

	private void resetResponseAndPrintLog(
			HttpServletResponse response, HttpLogInfo logInfo, ResponseWrapper responseWrapper) throws IOException {

		byte[] bytes = new byte[0];

		try {
			bytes = responseWrapper.bytes();
			logInfo.setResult(responseWrapper.getResult(bytes));
		} finally {
			logResponseIfConfig(logInfo);
			// 重新设置响应信息
			response.setContentType(responseWrapper.getContentType());
			response.setContentLength(bytes.length);
			response.getOutputStream().write(bytes);
			responseWrapper.close();
		}
	}


	private void printErrorLog(Stopwatch stopwatch, HttpLogInfo logInfo, Throwable throwable) {
		logInfo.setDuration(stopwatch.elapsed(TimeUnit.MILLISECONDS));
		logInfo.setException(throwable.getMessage());

		logResponseIfConfig(logInfo);
	}

	private void logResponseIfConfig(HttpLogInfo logInfo) {
		if (!onlyPrintReqLog) {
			logInfo.printOutputLog();
		}
	}

	private HttpServletRequest printReqLog(HttpServletRequest request, HttpLogInfo logInfo) {
		try {
			// 对请求进行包装，获取请求参数
			RequestParamWrapper wrapper = new RequestParamWrapper(request);
			if (Objects.nonNull(wrapper.getRequestWrapper())) {
				request = wrapper.getRequestWrapper();
			}
			logInfo.setParam(wrapper.getParams());
			// 网关已经获取到ip
			logInfo.setRequestIp(request.getHeader("ip"));
			logInfo.setUrl(request.getRequestURI());
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			// 只打印请求日志
			if (this.onlyPrintReqLog) {
				logInfo.printInputLog();
			}
		}

		return request;
	}

}
