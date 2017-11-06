package com.lotus.mp2.dao;

import java.util.List;

import com.lotus.mp2.exceptions.InvalidInputException;
import com.lotus.mp2.user.User;

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
}
