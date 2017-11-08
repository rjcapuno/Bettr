package com.lotus.mp2.user.admin;

import com.lotus.mp2.user.User;
import com.lotus.mp2.utils.UserType;

public class Admin implements User{
	private Long id;
	private String username;
	private String firstName;
	private String lastName;
	private String password;
	private UserType userType;
	
	public Admin(Long id, String username, String firstName, String lastName, String password, UserType userType) {
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.userType = userType;
	}
	
	@Override
	public Long getId() {
		return id;
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
