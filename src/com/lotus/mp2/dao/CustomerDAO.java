package com.lotus.mp2.dao;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import com.lotus.mp2.bet.BetInterface;
import com.lotus.mp2.event.EventInterface;
import com.lotus.mp2.utils.DAOUtils;
import com.sun.jersey.spi.resource.Singleton;

import static com.lotus.mp2.utils.Constants.*;
import static com.lotus.mp2.utils.Queries.*;

@Singleton
public class CustomerDAO implements CustomerDAOInterface{
	
	private Connection setUpConnection() throws SQLException {
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
	
	public List<BetInterface> getBetHistory(long customerId) {
		List<BetInterface> bets = new LinkedList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = setUpConnection();
			statement = connection.prepareStatement(GET_BET_HISTORY_QUERY);
			statement.setLong(1, customerId);
			ResultSet result = statement.executeQuery();
			
			while(result.next()) {
				bets.add(DAOUtils.convertToBetObject(result));
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
		System.out.println();
		
		return bets;
	}
	
	public List<EventInterface> getEventBySport(String sport) {
		List<EventInterface> events = new LinkedList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = setUpConnection();
			statement = connection.prepareStatement(GET_EVENT_BY_SPORT_QUERY);
			statement.setString(1, sport.toLowerCase());
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
