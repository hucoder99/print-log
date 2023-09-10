package com.single.log.module;

import cn.hutool.core.util.StrUtil;

/**
 * 请求日志
 *
 * @author single
 * @date 2022/8/3
 */
public class HttpLogInfo extends BaseLogInfo implements InOutputLog {

    /**
     * 请求url
     */
    private String url;

    /**
     * 请求ip
     */
    private String requestIp;

    @Override
    public String outputLog() {
        StringBuilder stringBuilder = new StringBuilder("\n【响应】\n");
        stringBuilder.append("【请求接口】: ").append(getUrl()).append("\n");
        stringBuilder.append("【请求IP  】: ").append(getRequestIp()).append("\n");
        stringBuilder.append("【请求参数】: ").append(getParam()).append("\n");
        stringBuilder.append("【请求耗时】: ").append(getDuration()).append("ms").append("\n");
        if (StrUtil.isNotEmpty(getException())) {
            stringBuilder.append("【请求异常】: ").append(getException()).append("\n");
        } else {
            stringBuilder.append("【返回值】: ").append(getResult()).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public String inputLog() {
        StringBuilder stringBuilder = new StringBuilder("\n【请求】\n");
        stringBuilder.append("【请求接口】: ").append(getUrl()).append("\n");
        stringBuilder.append("【请求IP  】: ").append(getRequestIp()).append("\n");
        stringBuilder.append("【请求参数】: ").append(getParam()).append("\n");
        return stringBuilder.toString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

}
