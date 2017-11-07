package com.lotus.mp2.utils;

public class Queries {
	public static final String GET_USER_BY_USERNAME_QUERY = "SELECT * FROM users WHERE username = ?";
	public static final String ADD_USER_QUERY = "INSERT INTO users(id, type, username, firstname, lastname, password, balance) " + 
			"VALUES(users_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";
}
