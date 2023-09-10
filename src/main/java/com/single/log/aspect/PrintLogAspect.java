package com.single.log.aspect;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.base.Stopwatch;

import com.single.log.annotation.PrintLog;
import com.single.log.module.AspectLogInfo;
import com.single.log.util.JoinPointUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 打印日志切面，只打印有@PrintLog注解的方法
 *
 * @author single
 * @date 2022/8/3
 */
@Aspect
public class PrintLogAspect {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Pointcut("@annotation(com.single.log.annotation.PrintLog)")
	private void logPrint() {
	}

	@Around("logPrint()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		Stopwatch stopwatch = Stopwatch.createStarted();

		try {
			Object proceed = joinPoint.proceed();
			printLog(joinPoint, stopwatch, null, proceed);
			return proceed;
		} catch (Throwable throwable) {
			printLog(joinPoint, stopwatch, throwable.getMessage(), null);
			throw throwable;
		}
	}

	/**
	 * 判断是否打印入参出参日志
	 *
	 * @param joinPoint    切点信息
	 * @param stopwatch    Stopwatch
	 * @param exceptionMsg 异常信息
	 * @param result       返回结果
	 */
	private void printLog(ProceedingJoinPoint joinPoint, Stopwatch stopwatch, String exceptionMsg, Object result) {
		try {
			stopwatch.stop();

			MethodSignature signature = (MethodSignature) joinPoint.getSignature();

			AspectLogInfo logInfo = new AspectLogInfo();
			logInfo.setMethod(signature.toLongString());
			logInfo.setParam(JoinPointUtils.toString(joinPoint.getArgs()));

			PrintLog printLog = signature.getMethod().getAnnotation(PrintLog.class);
			//只打印请求日志
			if (printLog.onlyPrintInputParam()) {
				// 打印输入值
				logInfo.printInputLog();
				return;
			}

			logInfo.setDuration(stopwatch.elapsed(TimeUnit.MILLISECONDS));
			if (StrUtil.isNotEmpty(exceptionMsg)) {
				logInfo.setException(exceptionMsg);
			} else {
				logInfo.setResult(JSONUtil.toJsonStr(result));
			}
			// 打印输出参数
			logInfo.printOutputLog();
		} catch (Throwable ex) {
			logger.error(ex.getMessage(), ex);
		}

	}

}
