package com.qingclass.squirrel.cms.constant;



public enum ScholarshipTypeEnum { 

	GeneralScholarship(0, "普通奖学金"),
	SpecialScholarship(1, "特殊奖学金");

	private Integer key;
	private String value;

	private ScholarshipTypeEnum(Integer key, String value) {
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
		for (ScholarshipTypeEnum st : ScholarshipTypeEnum.values()) {
			if (key.equals(st.key)) {
				return st.value;
			}
		}
		return "";
	}
}
