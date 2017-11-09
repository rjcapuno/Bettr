package com.lotus.mp2.utils;

import java.math.BigDecimal;

public final class Constants {
	public static final String DB_USERNAME = "bettr";
	public static final String DB_PASSWORD = "1234";
	
	public static final String DATE_FORMAT = "MM/dd/yyyy HH:mm";
	
	public static final BigDecimal MIN_STAKE = new BigDecimal(10);
	public static final BigDecimal MAX_STAKE = new BigDecimal(1000);
	
	public static final String OK = "success: true";
	public static final String FAIL = "success: false, error: ";
	public static final String FORBIDDEN = "forbidden";
	
	public static final boolean SUCCESS = true;
	
	public static final int EVENT_CODE_LENGTH = 5;
	public static final int USERNAME_MAX_LENGTH = 10;
	public static final int PASSWORD_MIN_LENGTH = 7;
	public static final int PASSWORD_MAX_LENGTH = 10;
	public static final int TRANSACTION_ID_LENGTH = 5;
	public static final int FIRST_INDEX = 0;
	
	public static final int DEFAULT_BALANCE = 0;	
	public static final long DEFAULT_ID = 0;	
	
}
