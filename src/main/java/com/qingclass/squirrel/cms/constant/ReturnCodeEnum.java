package com.qingclass.squirrel.cms.constant;


/**
 * 0.成功<br/>
 * 1.失败<br/>
 * 2.请求参数为空<br/>
 * @author suiss
 * 
 */
public enum ReturnCodeEnum { 
	
	TRUE("true", "成功"),
	FALSE("false", "失败"),
	INVALID("1001", "登录失效请重新登录"),
	DENIED("-1", "权限不足");

	private String key;
	private String value;

	private ReturnCodeEnum(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static String getValue(String key) {
		for (ReturnCodeEnum st : ReturnCodeEnum.values()) {
			if (key.equals(st.key)) {
				return st.value;
			}
		}
		return "";
	}
}
