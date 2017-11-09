package com.lotus.mp2.utils;

public class Queries {
	public static final String GET_ALL_USERS_QUERY = "SELECT * FROM users";
	public static final String GET_USER_BY_USERNAME_QUERY = "SELECT * FROM users WHERE LOWER(username) = ?";
	public static final String GET_USER_BY_CUSTOMER_ID_QUERY = "SELECT * FROM users WHERE id = ?";
	
	
	public static final String GET_EVENT_BY_EVENTCODE_QUERY = "SELECT * FROM events WHERE LOWER(eventcode) = ?";
	public static final String ADD_USER_QUERY = "INSERT INTO users(id, type, username, firstname, lastname, password, balance) " + 
			"VALUES(users_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";
	public static final String ADD_EVENT_QUERY = "INSERT INTO events(id, eventcode, sport, competitor1, competitor2, eventdate, issettled, winner) " + 
			"VALUES(events_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
	
	public static final String GET_TRANSACTION_BY_ID_QUERY = "SELECT * FROM transactions WHERE LOWER(transactionid) = ?";
	public static final String ADD_TRANSACTION_QUERY = "INSERT INTO transactions(id, transactionid, customerid, eventid, placementdate, stake, "
			+ "predicted, result) VALUES(transactions_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
	public static final String UPDATE_TRANSACTION_RESULT_QUERY = "UPDATE transactions SET result = ? WHERE id = ?";
	
	public static final String GET_ALL_EVENTS_QUERY = "SELECT * from events";
	public static final String GET_EVENT_BY_EVENT_ID_QUERY = "SELECT * from events WHERE id = ?";
	public static final String GET_EVENT_BY_SPORT_QUERY = "SELECT * from events WHERE LOWER(sport) = ?";
	public static final String UPDATE_EVENT_ISSETTLED_QUERY = "UPDATE events SET issettled = ? WHERE id = ?";
	
	
	public static final String UPDATE_RESULT_QUERY = "UPDATE events SET winner = ? WHERE LOWER(eventcode) = ?";
	public static final String UPDATE_BALANCE_QUERY = "UPDATE users SET balance = ? WHERE LOWER(username) = ?";
	
	
	public static final String GET_ALL_BETS_QUERY = "SELECT *  from transactions ORDER BY placementdate";
	public static final String GET_BET_BY_EVENT_ID_QUERY = "SELECT * from transactions WHERE eventid = ? "
			+ "ORDER BY placementdate";
	public static final String GET_BET_BY_CUSTOMER_ID_QUERY = "SELECT * from transactions WHERE customerid = ? "
			+ "ORDER BY placementdate";
	public static final String GET_BET_HISTORY_QUERY = "SELECT *  from transactions WHERE customerid = ? ORDER BY placementdate";
	
	
}
