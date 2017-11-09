package com.lotus.mp2.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lotus.mp2.event.EventInterface;
import com.lotus.mp2.user.User;

public interface UserDAOInterface {
	List<User> getUserByUsername(String username);

	public List<EventInterface> getEventByEventCode(String eventCode);
	
	List<EventInterface> getEventByEventId(long eventId);
	
	List<EventInterface> getAllEvents();
	
	public List<User> getUserByCustomerId(long customerId);
	
	Connection setUpConnection() throws SQLException;
}
