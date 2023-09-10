package com.single.log.wrapper;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 响应包装类
 *
 * @author single
 * @date 2022/8/3
 */
public class ResponseWrapper extends HttpServletResponseWrapper {

    public final Logger logger = LoggerFactory.getLogger(getClass());

    private final ServletOutputStream servletOutputStream;
    private final ByteArrayOutputStream byteArrayOutputStream;
    private final PrintWriter printWriter;


    /**
     * Constructs a response adaptor wrapping the given response.
     *
     * @param response HttpServletResponse
     * @throws IllegalArgumentException if the response is null
     */
    public ResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
        byteArrayOutputStream = new ByteArrayOutputStream();
        servletOutputStream = new ServletOutputStreamWrapper(byteArrayOutputStream);
        printWriter = new PrintWriter(new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8));
    }


    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return servletOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return printWriter;
    }


    @Override
    public void flushBuffer() throws IOException {
        if (Objects.nonNull(servletOutputStream)) {
            servletOutputStream.flush();
        }
        if (Objects.nonNull(printWriter)) {
            printWriter.flush();
        }
    }

    @Override
    public void reset() {
        byteArrayOutputStream.reset();
    }

    /**
     * 返回值字节数组
     *
     * @return 字节数组
     */
    public byte[] bytes() {
        try {
            flushBuffer();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return new byte[0];
    }

    public String getResult(byte[] resultBytes) {
        try {
            byte[] bytes = ObjectUtil.defaultIfNull(resultBytes, bytes());
            String result = new String(bytes, StandardCharsets.UTF_8);
            if (StrUtil.isNotEmpty(result) && bytes.length != result.getBytes().length) {
                // 文件流
                return String.valueOf(bytes.length);
            }
            return result;
        } catch (Throwable ex) {
            logger.error(ex.getMessage(), ex);
        }
        return StrUtil.EMPTY;
    }

    /**
     * 关闭流
     */
    public void close() {
        if (Objects.nonNull(this.printWriter)) {
            this.printWriter.close();
        }
        if (Objects.nonNull(this.servletOutputStream)) {
            try {
                this.servletOutputStream.close();
            } catch (IOException ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
        if (Objects.nonNull(this.byteArrayOutputStream)) {
            try {
                this.byteArrayOutputStream.close();
            } catch (IOException ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    }

    private static class ServletOutputStreamWrapper extends ServletOutputStream {

        private final ByteArrayOutputStream byteArrayOutputStream;

        ServletOutputStreamWrapper(ByteArrayOutputStream byteArrayOutputStream) {
            this.byteArrayOutputStream = byteArrayOutputStream;
        }

        @Override
        public void write(int b) throws IOException {
            this.byteArrayOutputStream.write(b);
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {

        }
    }

}
