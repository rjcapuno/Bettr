package com.lotus.mp2.dao;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import com.lotus.mp2.user.User;

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
			result.next();
			System.out.println("//" + result.getString("username"));
	
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
		
		return null;
	}
}
