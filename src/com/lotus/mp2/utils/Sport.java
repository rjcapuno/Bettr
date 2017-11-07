package com.lotus.mp2.utils;

public enum Sport {
	FOOTBALL("FOOT"), BASKETBALL("BASK"), TENNIS("TENN"), BOXING("BOXI");
	
	private final String code;

	Sport(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
}
