package com.lotus.mp2.user.admin;

import com.lotus.mp2.user.User;

public class Admin implements User{
	private String username;
	private String firstName;
	private String lastName;
	private String password;
	
	public Admin(String username, String firstName, String lastName, String password) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
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
}
