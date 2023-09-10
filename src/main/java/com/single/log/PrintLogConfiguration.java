package com.single.log;

import com.single.log.aspect.PrintLogAspect;
import com.single.log.filter.HttpLogFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 日志配置
 *
 * @author single
 * @date 2022/8/3
 */
@ConditionalOnWebApplication
@Configuration
public class PrintLogConfiguration {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * true只打印请求不打印响应，false请求和响应都打印
	 */
	@Value("${http.log.print.req:true}")
	private boolean printReqLog;

	/**
	 * 要打印日志的路径，默认打印app路径日志
	 */
	@Value("${http.log.print.url:/app/*}")
	private String[] printLogUrl;

	@Bean
	public PrintLogAspect printLogAspect() {
		return new PrintLogAspect();
	}

	@Bean
	@ConditionalOnProperty(value = "http.log.print.enable", havingValue = "true")
	public FilterRegistrationBean<HttpLogFilter> reqLogFilterRegistrationBean() {
		FilterRegistrationBean<HttpLogFilter> bean = new FilterRegistrationBean<>(new HttpLogFilter(this.printReqLog));
		bean.addUrlPatterns(this.printLogUrl);

		logger.info("初始HttpLogFilter success, printReqLog = {}, printLogUrl = {}", this.printReqLog, this.printLogUrl);
		return bean;
	}

}
