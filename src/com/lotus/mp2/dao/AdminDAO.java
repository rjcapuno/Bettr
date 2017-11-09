package com.lotus.mp2.dao;

import static com.lotus.mp2.utils.Queries.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import com.lotus.mp2.bet.TransactionInterface;
import com.lotus.mp2.event.EventInterface;
import com.lotus.mp2.user.User;
import com.lotus.mp2.utils.DAOUtils;
import com.sun.jersey.spi.resource.Singleton;

import static com.lotus.mp2.utils.Constants.*;

@Singleton
public class AdminDAO implements AdminDAOInterface{
	
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
	
	public List<User> getUsersList() {
		List<User> users = new LinkedList<>();
		Connection connection = null;
		Statement statement = null;
		
		try {
			connection = setUpConnection();
			statement = connection.createStatement();
			ResultSet result = statement.executeQuery(GET_ALL_USERS_QUERY);
			
			while(result.next()) {
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
		System.out.println();
		
		return users;
	}
	
	public List<TransactionInterface> getBetList() {
		List<TransactionInterface> transactions = new LinkedList<>();
		Connection connection = null;
		Statement statement = null;
		
		try {
			connection = setUpConnection();
			statement = connection.createStatement();
			ResultSet result = statement.executeQuery(GET_ALL_BETS_QUERY);
			
			while(result.next()) {
				transactions.add(DAOUtils.convertToTransactionObject(result));
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
		
		return transactions;
	}
	
	public List<TransactionInterface> getBetbyEventId(long eventId) {
		List<TransactionInterface> transactions = new LinkedList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = setUpConnection();
			statement = connection.prepareStatement(GET_BET_BY_EVENT_ID_QUERY);
			statement.setLong(1, eventId);
			ResultSet result = statement.executeQuery();
			
			while(result.next()) {
				transactions.add(DAOUtils.convertToTransactionObject(result));
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
		
		return transactions;
	}
	
	public List<TransactionInterface> getBetbyCustomerId(long customerId) {
		List<TransactionInterface> transactions = new LinkedList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = setUpConnection();
			statement = connection.prepareStatement(GET_BET_BY_CUSTOMER_ID_QUERY);
			statement.setLong(1, customerId);
			ResultSet result = statement.executeQuery();
			
			while(result.next()) {
				transactions.add(DAOUtils.convertToTransactionObject(result));
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
		
		return transactions;
	}
	
	public boolean addUser(User user) {
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = setUpConnection();
			statement = connection.prepareStatement(ADD_USER_QUERY);
			statement.setString	(1, user.getType().toString());
			statement.setString(2, user.getUsername());
			statement.setString(3, user.getFirstName());
			statement.setString(4, user.getLastName());
			statement.setString	(5, user.getPassword());
			
			switch(user.getType()) {
			case ADMIN : statement.setBigDecimal(6, null);
				break;
				
			case CUSTOMER : statement.setBigDecimal(6, new BigDecimal(DEFAULT_BALANCE));
				break;
			}
			
			statement.executeUpdate();
			connection.commit();
			
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public boolean addEvent(EventInterface event) {
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = setUpConnection();
			statement = connection.prepareStatement(ADD_EVENT_QUERY);
			statement.setString	(1, event.getEventCode());
			statement.setString	(2, event.getCategory().toString());
			statement.setString	(3, event.getCompetitor1());
			statement.setString	(4, event.getCompetitor2());
			statement.setTimestamp(5, new java.sql.Timestamp(event.getEventDate().getTime()));
			statement.setBoolean(6, event.isSettled());
			statement.setString	(7, event.getWinner());
			statement.executeUpdate();
			connection.commit();
			
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public boolean updateResult(String outcome, String eventCode) {
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = setUpConnection();
			statement = connection.prepareStatement(UPDATE_RESULT_QUERY);
			statement.setString	(1, outcome);
			statement.setString	(2, eventCode.toLowerCase());
			statement.executeUpdate();
			connection.commit();
			
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public boolean updateBalance(BigDecimal balance, String username) {
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = setUpConnection();
			statement = connection.prepareStatement(UPDATE_BALANCE_QUERY);
			statement.setBigDecimal	(1, balance);
			statement.setString	(2, username.toLowerCase());
			statement.executeUpdate();
			connection.commit();
			
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}
