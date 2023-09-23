package com.single.log.module;

/**
 * @author single
 * @date 2019/2/1
 */
public interface CommonConstants {

	/**
	 * 删除
	 */
	String STATUS_DEL = "1";


	/**
	 * 未删除
	 */
	String STATUS_NOT_DEL = "0";

	/**
	 * 正常
	 */
	String STATUS_NORMAL = "1";

	/**
	 * 停用
	 */
	String STATUS_LOCK = "1";

	/**
	 * 未锁定
	 */
	String STATUS_UNLOCK = "0";

	/**
	 * 菜单树根节点
	 */
	Long MENU_TREE_ROOT_ID = -1L;

	/**
	 * 菜单
	 */
	String MENU = "0";

	/**
	 * 编码
	 */
	String UTF8 = "UTF-8";

	/**
	 * JSON 资源
	 */
	String CONTENT_TYPE = "application/json; charset=utf-8";;

	/**
	 * header 中版本信息
	 */
	String VERSION = "VERSION";

	/**
	 * 成功标记
	 */
	Integer SUCCESS = 200;

	/**
	 * 失败标记
	 */
	Integer FAIL = 1;

	/**
	 * 验证码前缀
	 */
	String DEFAULT_CODE_KEY = "DEFAULT_CODE_KEY_";

	/**
	 * 当前页
	 */
	String CURRENT = "pageNo";

	/**
	 * size
	 */
	String SIZE = "pageSize";

	/*** 默认每个页面条数*/
	Integer DEFAULT_PAGE_SIZE = 10;
	/*** 默认页面*/
	Integer DEFAULT_PAGE_NO = 1;

	String EMPTY = "";
	String POINT = ".";
	String COMMA = ",";
	String UNDER_LINE = "_";
	String MINUS = "-";
	String QUESTION = "?";
	String PERCENTAGE = "%";
	int FIRST_ELEMENT = 0;
	int ZERO = 0;
	String COLON = ":";
	String TILDE = "~";

	/**
	 * 默认的拼接字符
	 */
	String DEFAULT_JOIN_CHAR = ",";
	/*** 默认顶级的父级id*/
	Integer TOP_PARENT_ID = 0;

	String SUCCESS_MSG = "操作成功";

	String TENANT_ID = "TENANT-ID";

	/**
	 * 租户ID
	 */
	Integer TENANT_ID_1 = 1;

	Integer USER_TAG_LIMIT = 10;

}
