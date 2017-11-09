package com.lotus.mp2.utils;

public enum Sports {
	FOOTBALL("FOOT"), BASKETBALL("BASK"), TENNIS("TENN"), BOXING("BOXI");
	
	private final String code;

	Sports(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
}
