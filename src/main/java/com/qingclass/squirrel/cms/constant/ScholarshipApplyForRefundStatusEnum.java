package com.qingclass.squirrel.cms.constant;



public enum ScholarshipApplyForRefundStatusEnum { 
	
	Wait(0, "待退款"),
	Success(1, "已退款"),
	Failed(2, "退款失败");
	
	private Integer key;
	private String value;

	private ScholarshipApplyForRefundStatusEnum(Integer key, String value) {
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
		for (ScholarshipApplyForRefundStatusEnum st : ScholarshipApplyForRefundStatusEnum.values()) {
			if (key.equals(st.key)) {
				return st.value;
			}
		}
		return "";
	}
}
