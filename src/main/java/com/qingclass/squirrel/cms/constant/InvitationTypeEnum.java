package com.qingclass.squirrel.cms.constant;



public enum InvitationTypeEnum { 

	InvitationCoupon("0", "邀请送券"),
	InvitationCash("1", "邀请送现金");

	private String key;
	private String value;

	private InvitationTypeEnum(String key, String value) {
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
		for (InvitationTypeEnum st : InvitationTypeEnum.values()) {
			if (key.equals(st.key)) {
				return st.value;
			}
		}
		return "";
	}
}
