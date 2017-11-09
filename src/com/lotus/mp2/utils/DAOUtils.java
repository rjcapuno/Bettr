package com.lotus.mp2.utils;

import static com.lotus.mp2.utils.Constants.DATE_FORMAT;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.lotus.mp2.bet.Bet;
import com.lotus.mp2.bet.BetInterface;
import com.lotus.mp2.bet.Transaction;
import com.lotus.mp2.bet.TransactionInterface;
import com.lotus.mp2.dao.UserDAO;
import com.lotus.mp2.dao.UserDAOInterface;
import com.lotus.mp2.event.Event;
import com.lotus.mp2.event.EventInterface;
import com.lotus.mp2.exceptions.InvalidInputException;
import com.lotus.mp2.user.User;
import com.lotus.mp2.user.admin.Admin;
import com.lotus.mp2.user.customer.Customer;

public class DAOUtils {
	static UserDAOInterface userDAO = new UserDAO();
	
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
		Calendar calendar = Calendar.getInstance();
		
		long id = result.getLong("id");
		String eventCode = result.getString("eventcode");
		Sports category = Sports.valueOf(result.getString("sport").toUpperCase());
		String competitor1 = result.getString("competitor1");
		String competitor2 = result.getString("competitor2");
		calendar.setTimeInMillis(result.getTimestamp("eventdate").getTime());
		boolean isSettled = result.getBoolean("issettled");
		String winner = result.getString("winner");
		
		EventInterface event = new Event(id, eventCode, category, competitor1,
		 competitor2, calendar.getTime(), isSettled, winner);
		
		return event;
	}
	
	public static TransactionInterface convertToTransactionObject1(ResultSet result) throws SQLException {
		Calendar calendar = Calendar.getInstance();
		
		long id = result.getLong("id");
		long eventId = result.getLong("eventid");
		long customerId = result.getLong("customerid");
		BigDecimal stake = result.getBigDecimal("stake");
		String predicted  = result.getString("predicted");
		String transactionId = result.getString("transactionid");
		Result betResult = Result.valueOf(result.getString("result").toUpperCase());
		calendar.setTimeInMillis(result.getTimestamp("placementdate").getTime());
		
		TransactionInterface transaction = new Transaction(id, stake, eventId, predicted, calendar.getTime(),
				transactionId, customerId, betResult);
		
		return transaction;
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
	
	public static TransactionInterface convertToTransactionObject(ResultSet result) throws SQLException {
		long id = result.getLong("id");
		String transactionId = result.getString("transactionId");
		long customerId = result.getLong("customerId");
		long eventId = result.getLong("eventid");
		Date placementDate = result.getTimestamp("placementdate");
		BigDecimal stake  = result.getBigDecimal("stake");
		String predicted = result.getString("predicted");
		Result betResult = Result.valueOf(result.getString("result").toUpperCase());
		
		TransactionInterface transaction = new Transaction(id, stake, eventId, predicted, placementDate, 
				transactionId, customerId, betResult);
		
		return transaction;
	}
	
	public static BetInterface convertToBetObject(ResultSet result) throws SQLException {
		long id = result.getLong("id");
		long eventId = result.getLong("eventid");
		Date placementDate = result.getTimestamp("placementdate");
		BigDecimal stake  = result.getBigDecimal("stake");
		String predicted = result.getString("predicted");
		Result betResult = Result.valueOf(result.getString("result").toUpperCase());
		
		List<EventInterface> events = userDAO.getEventByEventId(eventId);
		
		BetInterface bet = new Bet(id, stake, events.get(0).getEventCode(), predicted, placementDate, betResult);
		
		return bet;
	}

}
