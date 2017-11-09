package com.lotus.mp2.dao;

import java.sql.*;
import static com.lotus.mp2.utils.Constants.*;
import static com.lotus.mp2.utils.Queries.*;

import java.util.LinkedList;
import java.util.List;

import com.lotus.mp2.event.EventInterface;
import com.lotus.mp2.user.User;
import com.lotus.mp2.utils.DAOUtils;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
public class UserDAO implements UserDAOInterface{
	
	@Override
	public Connection setUpConnection() throws SQLException {
		Connection connection = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", DB_USERNAME, DB_PASSWORD);
			connection.setAutoCommit(false);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Database connection failed");
		} 
		
		return connection;
	}
	
	@Override
	public List<User> getUserByUsername(String username) {
		List<User> users = new LinkedList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = setUpConnection();
			statement = connection.prepareStatement(GET_USER_BY_USERNAME_QUERY);
			username = username.toLowerCase();
			statement.setString	(1, username);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				users.add(DAOUtils.convertToUserObject(result));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return users;
	}
	
	@Override
	public List<User> getUserByCustomerId(long customerId) {
		List<User> users = new LinkedList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = setUpConnection();
			statement = connection.prepareStatement(GET_USER_BY_CUSTOMER_ID_QUERY);
			statement.setLong(1, customerId);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				users.add(DAOUtils.convertToUserObject(result));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return users;
	}
	
	public List<EventInterface> getEventByEventCode(String eventCode) {
		List<EventInterface> events = new LinkedList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = setUpConnection();
			statement = connection.prepareStatement(GET_EVENT_BY_EVENTCODE_QUERY);
			eventCode = eventCode.toLowerCase();
			statement.setString	(1, eventCode);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				events.add(DAOUtils.convertToEventObject(result));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return events;
	}
	
	public List<EventInterface> getEventByEventId(long eventId) {
		List<EventInterface> events = new LinkedList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = setUpConnection();
			statement = connection.prepareStatement(GET_EVENT_BY_EVENT_ID_QUERY);
			statement.setLong	(1, eventId);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				events.add(DAOUtils.convertToEventObject(result));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return events;
	}
	
	public List<EventInterface> getAllEvents() {
		List<EventInterface> events = new LinkedList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = setUpConnection();
			statement = connection.prepareStatement(GET_ALL_EVENTS_QUERY);
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				events.add(DAOUtils.convertToEventObject(result));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return events;
	}
	

}
