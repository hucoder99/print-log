package com.single.log.wrapper;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * 请求包装类
 *
 * @author single
 * @date 2022/8/3
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    public final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 请求body内容
     */
    private byte[] requestBody;

    public RequestWrapper(HttpServletRequest request) {
        super(request);

        try {
            this.requestBody = StreamUtils.copyToByteArray(request.getInputStream());
        } catch (IOException ex) {
            this.requestBody = new byte[0];
            logger.error("read body io ex", ex);
        }

    }

    public String getRequestBody() {
        return ArrayUtil.isEmpty(this.requestBody) ? StrUtil.EMPTY : new String(this.requestBody);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.requestBody);

        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }

}
