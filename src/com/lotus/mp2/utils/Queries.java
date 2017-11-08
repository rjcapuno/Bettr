package com.lotus.mp2.utils;

public class Queries {
	public static final String GET_USER_BY_USERNAME_QUERY = "SELECT * FROM users WHERE LOWER(username) = ?";
	public static final String GET_EVENT_BY_EVENTCODE_QUERY = "SELECT * FROM events WHERE LOWER(eventcode) = ?";
	public static final String ADD_USER_QUERY = "INSERT INTO users(id, type, username, firstname, lastname, password, balance) " + 
			"VALUES(users_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";
	public static final String ADD_EVENT_QUERY = "INSERT INTO events(id, eventcode, sport, competitor1, competitor2, eventdate, issettled, winner) " + 
			"VALUES(events_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
}
