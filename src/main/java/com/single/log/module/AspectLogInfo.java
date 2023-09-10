package com.single.log.module;


import cn.hutool.core.util.StrUtil;

/**
 * 切面日志
 *
 * @author single
 * @date 2022/8/3
 */
public class AspectLogInfo extends BaseLogInfo implements InOutputLog {

    /**
     * 切面当前方法
     */
    private String method;


    @Override
    public String outputLog() {
        StringBuilder stringBuilder = new StringBuilder("\n");
        stringBuilder.append("【方法】: ").append(getMethod()).append("\n");
        stringBuilder.append("【参数】: ").append(getParam()).append("\n");
        stringBuilder.append("【耗时】: ").append(getDuration()).append("ms").append("\n");
        if (StrUtil.isNotEmpty(getException())) {
            stringBuilder.append("【异常】: ").append(getException()).append("\n");
        } else {
            stringBuilder.append("【结果】: ").append(getResult()).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public String inputLog() {
        StringBuilder stringBuilder = new StringBuilder("\n");
        stringBuilder.append("【方法】: ").append(this.method).append("\n");
        stringBuilder.append("【参数】: ").append(getParam()).append("\n");
        return stringBuilder.toString();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}
