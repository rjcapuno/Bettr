package com.lotus.mp2.user.customer;

import java.math.BigDecimal;

import com.lotus.mp2.user.User;

public interface CustomerInterface extends User{
	public BigDecimal getBalance();

	public void setBalance(BigDecimal balance);
}
