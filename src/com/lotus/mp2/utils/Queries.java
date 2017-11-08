package com.lotus.mp2.utils;

public class Queries {
	public static final String GET_ALL_USERS_QUERY = "SELECT * FROM users";
	public static final String GET_USER_BY_USERNAME_QUERY = "SELECT * FROM users WHERE LOWER(username) = ?";
	public static final String GET_EVENT_BY_EVENTCODE_QUERY = "SELECT * FROM events WHERE LOWER(eventcode) = ?";
	public static final String ADD_USER_QUERY = "INSERT INTO users(id, type, username, firstname, lastname, password, balance) " + 
			"VALUES(users_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";
	public static final String ADD_EVENT_QUERY = "INSERT INTO events(id, eventcode, sport, competitor1, competitor2, eventdate, issettled, winner) " + 
			"VALUES(events_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
	
	public static final String GET_TRANSACTION_BY_ID_QUERY = "SELECT * FROM transactions WHERE LOWER(transactionid) = ?";
	public static final String ADD_TRANSACTION_QUERY = "INSERT INTO transactions(id, transactionid, username, eventcode, placementdate, stake, "
			+ "predicted, result) VALUES(transactions_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
}
