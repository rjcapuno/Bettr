package com.lotus.mp2.customer;

import java.math.BigDecimal;

public interface User {
	BigDecimal getBalance();

	void setBalance(BigDecimal balance);

	String getUsername();

	String getFirstName();

	String getLastName();
	
	String getPassword();
}
