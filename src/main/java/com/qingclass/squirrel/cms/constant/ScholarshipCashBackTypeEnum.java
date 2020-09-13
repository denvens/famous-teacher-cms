package com.qingclass.squirrel.cms.constant;



public enum ScholarshipCashBackTypeEnum { 
	
	ScholarshipAmount(0, "奖学金金额"),
	ScholarshipRatio(1, "奖学金比例");
	
	private Integer key;
	private String value;

	private ScholarshipCashBackTypeEnum(Integer key, String value) {
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

	public static String getValue(Integer key) {
		for (ScholarshipCashBackTypeEnum st : ScholarshipCashBackTypeEnum.values()) {
			if (key.equals(st.key)) {
				return st.value;
			}
		}
		return "";
	}
}
