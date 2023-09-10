package com.single.log.module;

/**
 * 基础日志信息
 *
 * @author single
 * @date 2022/8/3
 */
public class BaseLogInfo {

    /**
     * 参数
     */
    private String param;
    /**
     * 耗时(毫秒)
     */
    private Long duration;
    /**
     * 返回值
     */
    private String result;
    /**
     * 异常
     */
    private String exception;


    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

}
