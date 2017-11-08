package com.lotus.mp2.utils;

import org.apache.commons.lang3.StringUtils;

import com.lotus.mp2.dao.UserDAO;
import com.lotus.mp2.event.EventInterface;
import com.lotus.mp2.exceptions.InvalidInputException;
import com.lotus.mp2.user.User;
import com.lotus.mp2.user.customer.CustomerInterface;

import static com.lotus.mp2.utils.Constants.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InputValidator {
	private static UserDAO userDAO = new UserDAO();
	
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
		
		List<User> users = userDAO.getUserByUsername(username);
		if(!users.isEmpty()) {
			throw new InvalidInputException("Username already exists");
		}
		
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
		if(StringUtils.isEmpty(eventCode)) {
			throw new InvalidInputException("Event code cannot be empty");
		}
		
		if(eventCode.length() != EVENT_CODE_LENGTH) {
			throw new InvalidInputException("Event code must be EXACTLY 5 characters long");
		}
		
		if(eventCode.contains(" ")) {
			throw new InvalidInputException("Event code cannot contain spaces");
		}
		
		if(doesEventCodeExists(eventCode)) {
			throw new InvalidInputException("Event code already exists");
		}
		
		return true;
		
	}
	
	public static boolean doesEventCodeExists(String eventCode) {
		List<EventInterface> events = userDAO.getEventByEventCode(eventCode);
		
		if(events.isEmpty()) {
			return false;
		}
		
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
	
	public static boolean isValidSport(String sport) throws InvalidInputException {
		if(StringUtils.isEmpty(sport)) {
			throw new InvalidInputException("Sport cannot be empty");
		}
		
		for(Sport s : Sport.values()) {
			if(sport.equalsIgnoreCase(s.toString())) {
				return true;
			}
		}
		
		throw new InvalidInputException("Invalid sport");
	}
	
	public static boolean isValidCompetitor(String competitor) throws InvalidInputException {
		if(StringUtils.isBlank(competitor)) {
			throw new InvalidInputException("Name cannot be empty");
		}
		
		return true;
	}
	
	public static boolean isValidDate(String eventDate) throws InvalidInputException {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		try {
			Date date = dateFormat.parse(eventDate);
			calendar.setTime(date);
			
			if(!isDateFuture(calendar)) {
				throw new InvalidInputException("Event date should be in the future");
			}
			
		} catch (ParseException e) {
			throw new InvalidInputException("Invalid date format");
		}
		return true;
	}
	
	public static boolean isDateFuture(Calendar calendar) throws InvalidInputException {
		if(calendar.after(Calendar.getInstance())) {
			return true;
		}
		return false;
	}
	
	public static boolean isValidPredicted(String predicted, String eventCode) throws InvalidInputException {
		final int FIRST_INDEX = 0;
		
		if(StringUtils.isEmpty(predicted)) {
			throw new InvalidInputException("Winner cannot be empty");
		}
		
		List<EventInterface> events = userDAO.getEventByEventCode(eventCode);
		if(events.isEmpty()) {
			throw new InvalidInputException("Event does not exist");
		} 
		
		String competitor1 = events.get(FIRST_INDEX).getCompetitor1();
		String competitor2 = events.get(FIRST_INDEX).getCompetitor2();
		if(predicted.equalsIgnoreCase(competitor1) || predicted.equalsIgnoreCase(competitor2)) {
			return true;
		}
		
		throw new InvalidInputException("Invalid outcome");
	}
	
	public static boolean isValidBetEventCode(String eventCode) throws InvalidInputException {
		if(!doesEventCodeExists(eventCode)) {
			throw new InvalidInputException("Event not found");
		}
		
		return true;
	}

	public static boolean isValidBetStake(BigDecimal stake, String username) throws InvalidInputException{
		final BigDecimal ZERO = new BigDecimal(0);
		
		if(stake.equals(null)) {
			throw new InvalidInputException("Stake cannot be empty");
		}
		
		if(stake.compareTo(ZERO) == 0) {
			throw new InvalidInputException("Stake cannot be 0");
		}
		
		if(stake.compareTo(MIN_STAKE) < 0 || stake.compareTo(MAX_STAKE) > 0) {
			throw new InvalidInputException("Stake must be between 10 and 1000");
		}
		
		List<User> users = userDAO.getUserByUsername(username);
		if(users.isEmpty()) {
			throw new InvalidInputException("Invalid username");
		}
		
		CustomerInterface customer = (CustomerInterface) users.get(0);
		BigDecimal balance = customer.getBalance();
		
		if(balance.subtract(stake).compareTo(ZERO) < 0) {
			throw new InvalidInputException("Stake must be <= to balance");
		}
		
		return true;

	}
}
