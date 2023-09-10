package com.single.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author wanghu
 * @date 2022/5/20
 */
@Slf4j
@AutoConfiguration
public class TaskExecutorConfiguration implements AsyncConfigurer {

	/**
	 * 获取当前机器的核数, 不一定准确 请根据实际场景 CPU密集 || IO 密集
	 */
	public static final int CPU_NUM = Runtime.getRuntime().availableProcessors();

	@Value("${thread.pool.corePoolSize:}")
	private Optional<Integer> corePoolSize;

	@Value("${thread.pool.maxPoolSize:}")
	private Optional<Integer> maxPoolSize;

	@Value("${thread.pool.queueCapacity:}")
	private Optional<Integer> queueCapacity;

	@Value("${thread.pool.awaitTerminationSeconds:}")
	private Optional<Integer> awaitTerminationSeconds;

	@Override
	@Bean("asyncExecutor")
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		// 核心线程大小 默认区 CPU 数量
		taskExecutor.setCorePoolSize(corePoolSize.orElse(CPU_NUM));
		// 最大线程大小 默认区 CPU * 2 数量
		taskExecutor.setMaxPoolSize(maxPoolSize.orElse(CPU_NUM * 2));
		// 队列最大容量
		taskExecutor.setQueueCapacity(queueCapacity.orElse(500));
		taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
		taskExecutor.setAwaitTerminationSeconds(awaitTerminationSeconds.orElse(60));
		taskExecutor.setThreadNamePrefix("ASYNC-Thread-");
		// 透传请求上下文
		taskExecutor.setTaskDecorator(new ContextCopyingDecorator());
		taskExecutor.initialize();
		return taskExecutor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return (ex, method, params) -> {
			log.error("execute class = {}, method = {} async task error, message={}",
					method.getDeclaringClass().getSimpleName(), method.getName(), ex.getMessage(), ex);
		};
	}

	private static class ContextCopyingDecorator implements TaskDecorator {

		@Override
		public Runnable decorate(Runnable runnable) {
			RequestAttributes context = RequestContextHolder.getRequestAttributes();
			if (Objects.isNull(context)) {
				return runnable;
			}

			return () -> {
				try {
					RequestContextHolder.setRequestAttributes(context);
					runnable.run();
				} finally {
					RequestContextHolder.resetRequestAttributes();
				}
			};
		}

	}

}
