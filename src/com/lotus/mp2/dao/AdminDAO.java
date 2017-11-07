package com.lotus.mp2.dao;

import static com.lotus.mp2.utils.Queries.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import com.lotus.mp2.user.User;
import com.lotus.mp2.utils.DAOUtils;

import static com.lotus.mp2.utils.Constants.*;

public class AdminDAO {
	private static final String GET_ALL_USERS_QUERY = "SELECT * FROM users";
	private static CustomerDAO instance = null;

	public static CustomerDAO getInstance() {
		if (instance == null) {
			instance = new CustomerDAO();
		}
		return instance;
	}
	
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
}
