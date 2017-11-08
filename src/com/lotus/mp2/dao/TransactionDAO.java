package com.lotus.mp2.dao;

import static com.lotus.mp2.utils.Constants.DB_PASSWORD;
import static com.lotus.mp2.utils.Constants.DB_USERNAME;
import static com.lotus.mp2.utils.Queries.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.lotus.mp2.bet.TransactionInterface;
import com.lotus.mp2.utils.DAOUtils;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
public class TransactionDAO implements TransactionDAOInterface{
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
	
	public List<TransactionInterface> getTransactionById(String transactionId) {
		List<TransactionInterface> transactions = new LinkedList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = setUpConnection();
			statement = connection.prepareStatement(GET_TRANSACTION_BY_ID_QUERY);
			transactionId = transactionId.toLowerCase();
			statement.setString	(1, transactionId);
			ResultSet result = statement.executeQuery();
			result.next();
			transactions.add(DAOUtils.convertToTransactionObject(result));
			
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
		
		return transactions;
	}
	
	public boolean addTransaction(TransactionInterface transaction) {
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = setUpConnection();
			statement = connection.prepareStatement(ADD_TRANSACTION_QUERY);
			statement.setString	(1, transaction.getTransactionId());
			statement.setString	(2, transaction.getUsername());
			statement.setString	(3, transaction.getEventCode());
			statement.setTimestamp(4, new java.sql.Timestamp(transaction.getPlacementDate().getTime()));
			statement.setBigDecimal(5, transaction.getStake());
			statement.setString(6, transaction.getpredicted());
			statement.setString	(7, transaction.getResult().toString());
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
