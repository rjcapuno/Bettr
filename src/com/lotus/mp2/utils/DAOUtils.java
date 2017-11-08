package com.lotus.mp2.utils;

import static com.lotus.mp2.utils.Constants.DATE_FORMAT;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lotus.mp2.event.Event;
import com.lotus.mp2.event.EventInterface;
import com.lotus.mp2.exceptions.InvalidInputException;
import com.lotus.mp2.user.User;
import com.lotus.mp2.user.admin.Admin;
import com.lotus.mp2.user.customer.Customer;

public class DAOUtils {
	public static User convertToUserObject(ResultSet result) throws SQLException {
		long id = result.getLong("id");
		UserType userType = UserType.valueOf(result.getString("type").toUpperCase());
		String username = result.getString("username");
		String firstName = result.getString("firstname");
		String lastName = result.getString("lastname");
		String password = result.getString("password");
		BigDecimal balance = result.getBigDecimal("balance");
		
		User user = null;
		
		switch(userType) {
		case ADMIN: user = new Admin(id, username, firstName, lastName, password, userType);
			break;
			
		case CUSTOMER: user = new Customer(id, username, firstName, lastName, password, userType, balance);
			break;
		}

		return user;
	}
	
	public static EventInterface convertToEventObject(ResultSet result) throws SQLException {
		String eventCode = result.getString("eventcode");
		Sport category = Sport.valueOf(result.getString("sport"));
		String competitor1 = result.getString("competitor1");
		String competitor2 = result.getString("competitor2");
		Date eventDate = result.getDate("eventdate");
		boolean isSettled = result.getBoolean("issettled");
		String winner = result.getString("winner");
		
		EventInterface event = new Event(eventCode, category, competitor1,
		 competitor2, eventDate, isSettled, winner);
		
		return event;
	}
	
	public static Date convertStringToDate(String stringDate) throws InvalidInputException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		Date date = null;
		
		try {
			date = dateFormat.parse(stringDate);
		} catch (ParseException e) {
			throw new InvalidInputException("Invalid date format");
		}
		
		return date;
	}

}
