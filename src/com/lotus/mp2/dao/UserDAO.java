package com.lotus.mp2.dao;

import java.math.BigDecimal;
import java.sql.*;
import static com.lotus.mp2.utils.Constants.*;
import static com.lotus.mp2.utils.Queries.*;

import java.util.LinkedList;
import java.util.List;

import com.lotus.mp2.user.User;
import com.lotus.mp2.user.admin.Admin;
import com.lotus.mp2.user.customer.Customer;
import com.lotus.mp2.utils.UserType;

public class UserDAO implements UserDAOInterface{
	private static UserDAO instance = null;

	public static UserDAO getInstance() {
		if (instance == null) {
			instance = new UserDAO();
		}
		return instance;
	}
	
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
			statement.setString	(1, username);
			ResultSet result = statement.executeQuery();
			result.next();
			users.add(convertToUserObject(result));
			
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
	
	private User convertToUserObject(ResultSet result) throws SQLException {
		String userType = result.getString("type");
		String username = result.getString("username");
		String firstName = result.getString("firstname");
		String lastName = result.getString("lastname");
		String password = result.getString("password");
		BigDecimal balance = result.getBigDecimal("balance");
		
		User user = null;
		if(userType.equalsIgnoreCase(UserType.ADMIN.toString())) {
			user = new Admin(username, firstName, lastName, password);
		} else {
			user = new Customer(username, firstName, lastName, password, balance);
		}
		
		return user;
	}
}
