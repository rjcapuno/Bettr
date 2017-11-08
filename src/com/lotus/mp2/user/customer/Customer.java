package com.lotus.mp2.user.customer;

import java.math.BigDecimal;

import com.lotus.mp2.utils.UserType;

public class Customer implements CustomerInterface{
	private Long id;
	private String username;
	private String firstName;
	private String lastName;
	private String password;
	private UserType userType;
	private BigDecimal balance;
	
	public Customer(Long id, String username, String firstName, String lastName, String password,
			UserType userType, BigDecimal balance) {
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.userType = userType;
		this.balance = balance;
	}

	@Override
	public BigDecimal getBalance() {
		return balance;
	}
	
	@Override
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
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
