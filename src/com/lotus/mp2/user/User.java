package com.lotus.mp2.user;

import com.lotus.mp2.utils.UserType;

public interface User {
	Long getId();

	String getUsername();

	String getFirstName();

	String getLastName();
	
	String getPassword();
	
	UserType getType();
}
