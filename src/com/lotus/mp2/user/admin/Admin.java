package com.lotus.mp2.user.admin;

import com.lotus.mp2.user.User;
import com.lotus.mp2.utils.UserType;

public class Admin implements User{
	private String username;
	private String firstName;
	private String lastName;
	private String password;
	private UserType userType;
	
	public Admin(String username, String firstName, String lastName, String password, UserType userType) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.userType = userType;
	}
	
	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public UserType getType() {
		return userType;
	}
}
