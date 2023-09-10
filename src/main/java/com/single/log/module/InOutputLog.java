package com.single.log.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author single
 * @date 2022/8/3
 */
public interface InOutputLog {

    Logger LOGGER = LoggerFactory.getLogger(InOutputLog.class);

    /**
     * 输出日志
     *
     * @return 返回输出的日志
     */
    String outputLog();

    /**
     * 输入日志
     *
     * @return 返回输入的日志
     */
    String inputLog();

    /**
     * 打印输出日志
     */
    default void printOutputLog() {
        LOGGER.info(outputLog());
    }

    /**
     * 打印输入日志
     */
    default void printInputLog() {
        LOGGER.info(inputLog());
    }

}
