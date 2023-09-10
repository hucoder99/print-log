package com.single.log.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;

/**
 * 打印参数工具类
 *
 * @author single
 * @date 2022/8/3
 */
public final class JoinPointUtils {

	/**
	 * 默认的拼接字符
	 */
	private static  final String DEFAULT_JOIN_CHAR = ",";

	private static final Logger LOGGER = LoggerFactory.getLogger(JoinPointUtils.class);

	public static final int CONVERT_UNIT_BYTE = 1024;

	/**
	 * 打印参数
	 *
	 * @param args 参数
	 * @return 返回参数字符串
	 */
	public static String toString(Object[] args) {
		try {
			if (ArrayUtil.isEmpty(args)) {
				return StrUtil.EMPTY;
			}

			StringBuilder stringBuilder = new StringBuilder();
			// 过滤部分不能直接打印的参数，如request
			for (int var3 = 0; var3 < args.length; var3++) {
				stringBuilder.append(toString(args[var3]));
				if (var3 < args.length - 1) {
					stringBuilder.append(DEFAULT_JOIN_CHAR);
				}
			}

			return stringBuilder.toString();
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}

		return StrUtil.EMPTY;
	}

	private static String toString(Object object) {
		if (Objects.isNull(object)) {
			return StrUtil.NULL;
		}
		try {
			// 字节数组参数
			if (object instanceof byte[]) {
				byte[] bytes = (byte[]) object;
				// 超过指定长度的字节数组，禁止打印详情
				if (bytes.length > CONVERT_UNIT_BYTE) {
					return object.toString();
				}
				return Arrays.toString(bytes);
			} else if (object instanceof HttpServletRequest) {
				return HttpServletRequest.class.getSimpleName();
			} else if (object instanceof HttpServletResponse) {
				return HttpServletResponse.class.getSimpleName();
			} else if (object instanceof MultipartFile) {
				return MultipartFile.class.getSimpleName();
			}

			return JSONUtil.toJsonStr(object);
		} catch (Exception ex) {
			LOGGER.error("参数toString异常", ex);
		}

		return object.toString();
	}

}
