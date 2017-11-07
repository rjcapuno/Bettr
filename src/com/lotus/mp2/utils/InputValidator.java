package com.lotus.mp2.utils;

import org.apache.commons.lang3.StringUtils;

import com.lotus.mp2.exceptions.InvalidInputException;

import static com.lotus.mp2.utils.Constants.*;

public class InputValidator {
	
	public static boolean isValidUsername(String username) throws InvalidInputException {
		if(StringUtils.isBlank(username)) {
			throw new InvalidInputException("Name cannot be empty");
		}
		
		if(username.length() > USERNAME_MAX_LENGTH) {
			throw new InvalidInputException("Username cannod exceed 10 characters");
		}
		
		if(username.contains(" ")) {
			throw new InvalidInputException("Username cannot contain spaces");
		}
		
		//TODO if exists
		
		return true;
	}
	
	public static boolean isValidName(String name) throws InvalidInputException {
		if(StringUtils.isBlank(name)) {
			throw new InvalidInputException("Name cannot be empty");
		}
		
		if(!StringUtils.isAlphaSpace(name)) {
			throw new InvalidInputException("Name cannot contain numbers");
		}
		
		if(hasWhitespaceAtEnds(name)) {
			throw new InvalidInputException("Name cannot have whitespaces at start/end");
		}
		
		return true;
	}
	
	public static boolean hasWhitespaceAtEnds(String input) {
		int FIRST = 0;
		int LAST = input.length() - 1;
		
		if(input.charAt(FIRST) == ' ' || input.charAt(LAST) == ' ') {
			return true;
		}
		
		return false;
	}
	
	public static boolean isValidPassword(String password) throws InvalidInputException {
		if(StringUtils.isEmpty(password)) {
			throw new InvalidInputException("Password cannot be empty");
		}
		
		if(StringUtils.length(password) < PASSWORD_MIN_LENGTH || 
				StringUtils.length(password) > PASSWORD_MAX_LENGTH) {
			throw new InvalidInputException("Password must be 7 to 10 non- whitespace "
					+ "characters in length");
		}
		
		return true;
	}
	
	public static boolean isValidEventCode(String eventCode) throws InvalidInputException {
		if(eventCode.length() != EVENT_CODE_LENGTH) {
			throw new InvalidInputException("Event code must be EXACTLY 5 characters long");
		}
		
		if(eventCode.contains(" ")) {
			throw new InvalidInputException("Event code cannot contain spaces");
		}
		
		//TODO if exists
		
		return true;
	}
	
	public static boolean isValidUserType(String type) throws InvalidInputException {
		if(StringUtils.isEmpty(type)) {
			throw new InvalidInputException("User type cannot be empty");
		}
		
		try {
			UserType.valueOf(type.toUpperCase());
		} catch(IllegalArgumentException e) {
			throw new InvalidInputException("Invalid user type");
		}
		
		return true;
	}
	
}
