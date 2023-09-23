package com.single.log.module;

/**
 * 状态枚举
 *
 * @author single
 * @date 2022/6/7
 */
public enum DeletedEnum  {

	/**
	 *
	 */
	DELETED("1", "已删"),
	NOT_DELETE("0", "未删");
	private final String value;
	private final String desc;


	DeletedEnum(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}

}
