package com.lotus.mp2.dao;

import java.util.List;

import com.lotus.mp2.exceptions.InvalidInputException;
import com.lotus.mp2.user.User;
import com.lotus.mp2.utils.UserType;

public class UserDAOImpl {
	private static final int FIRST_INDEX = 0;
	private static UserDAO userDAO = new UserDAO();
	
	public static boolean validateCredentials(String username, String password) throws InvalidInputException {
		List<User> users = userDAO.getUserByUsername(username);
		
		if(users.isEmpty()) {
			throw new InvalidInputException("Invalid credentials");
		}
		
		User user = users.get(FIRST_INDEX);
		if(!user.getPassword().equals(password)) {
			throw new InvalidInputException("Invalid credentials");
		}
		
		return true;
	}
	
	public static UserType getUserType(String username) throws InvalidInputException {
		List<User> users = userDAO.getUserByUsername(username);
		
		if(users.isEmpty()) {
			throw new InvalidInputException("User not found");
		}
		
		return users.get(FIRST_INDEX).getType();
	}
	
	public static Long getUserId(String username ) throws InvalidInputException{
		List<User> users = userDAO.getUserByUsername(username);
		
		if(users.isEmpty()) {
			throw new InvalidInputException("User not found");
		}
		
		return users.get(FIRST_INDEX).getId();
	}
}
