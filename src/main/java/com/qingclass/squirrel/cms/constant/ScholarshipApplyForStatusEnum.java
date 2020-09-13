package com.qingclass.squirrel.cms.constant;



public enum ScholarshipApplyForStatusEnum { 
	
	NotFinished(0, "未全勤"),
	WaitApplyFor(1, "已全勤,待申请"),
	Expired(2, "已过期"),
	ApplyFor(3, "已申请,审核中"),
	Pass(4, "审核通过,发放成功"),
	Refuse(5, "审核拒绝"),
	Other(6,"Other");
	
	private Integer key;
	private String value;

	private ScholarshipApplyForStatusEnum(Integer key, String value) {
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
		for (ScholarshipApplyForStatusEnum st : ScholarshipApplyForStatusEnum.values()) {
			if (key.equals(st.key)) {
				return st.value;
			}
		}
		return "";
	}
}
