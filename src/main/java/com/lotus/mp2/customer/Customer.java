package com.lotus.mp2.customer;

import java.math.BigDecimal;

class Customer implements User{
	private String username;
	private String firstName;
	private String lastName;
	private String password;
	private BigDecimal balance;
	
	public Customer(String username, String firstName, String lastName, String password,
			BigDecimal balance) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.balance = balance;
	}

	@Override
	public
	BigDecimal getBalance() {
		return balance;
	}

	@Override
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
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
