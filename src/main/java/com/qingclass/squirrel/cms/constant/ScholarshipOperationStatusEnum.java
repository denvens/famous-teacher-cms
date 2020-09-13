package com.qingclass.squirrel.cms.constant;



public enum ScholarshipOperationStatusEnum { 

	NotIssued(0, "未发放"),
	Issue(1, "已手动发放");

	private Integer key;
	private String value;

	private ScholarshipOperationStatusEnum(Integer key, String value) {
		this.key = key;
		this.value = value;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static String getValue(String key) {
		for (ScholarshipOperationStatusEnum st : ScholarshipOperationStatusEnum.values()) {
			if (key.equals(st.key)) {
				return st.value;
			}
		}
		return "";
	}
}
