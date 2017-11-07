package com.lotus.mp2.utils;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lotus.mp2.user.User;
import com.lotus.mp2.user.admin.Admin;
import com.lotus.mp2.user.customer.Customer;

public class DAOUtils {
	public static User convertToUserObject(ResultSet result) throws SQLException {
		UserType userType = UserType.valueOf(result.getString("type").toUpperCase());
		String username = result.getString("username");
		String firstName = result.getString("firstname");
		String lastName = result.getString("lastname");
		String password = result.getString("password");
		BigDecimal balance = result.getBigDecimal("balance");
		
		User user = null;
		
		switch(userType) {
		case ADMIN: user = new Admin(username, firstName, lastName, password, userType);
			break;
			
		case CUSTOMER: user = new Customer(username, firstName, lastName, password, userType, balance);
			break;
		}

		return user;
	}
}
