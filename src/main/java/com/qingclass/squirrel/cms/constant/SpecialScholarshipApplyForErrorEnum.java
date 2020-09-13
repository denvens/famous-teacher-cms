package com.qingclass.squirrel.cms.constant;



public enum SpecialScholarshipApplyForErrorEnum { 
	
	AlreadyApplyFor(0, "已经申请过奖学金"),
	OpenIdIsError(1, "openId错误或不存在"),
	PresetSpecialScholarship(2,"已经设置特殊奖学金"),
	AlreadyApplyForButFailure(3, "当前状态是发放失败,请联系运营"),
	AlreadyApplyForButApplyForIng(4, "当前状态是申请中,请联系运营"),
	AlreadyApplyForSpecialScholarship(5, "已经申请过特殊奖学金");
	
	private Integer key;
	private String value;

	private SpecialScholarshipApplyForErrorEnum(Integer key, String value) {
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
		for (SpecialScholarshipApplyForErrorEnum st : SpecialScholarshipApplyForErrorEnum.values()) {
			if (key.equals(st.key)) {
				return st.value;
			}
		}
		return "";
	}
}
