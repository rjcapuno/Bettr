package com.lotus.mp2.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lotus.mp2.user.User;

public interface UserDAOInterface {
	List<User> getUserByUsername(String username);
	Connection setUpConnection() throws SQLException;
}
