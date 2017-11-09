package com.lotus.mp2.bet;

import java.math.BigDecimal;
import java.util.Date;

import com.lotus.mp2.utils.Result;

public interface TransactionInterface {
	public long getId();
	
	BigDecimal getStake();
	
	long getEventId();
	
	String getpredicted();
	
	String getTransactionId();
	
	long getCustomerId();
	
	Date getPlacementDate();
	
	Result getResult();
	
	void setResult(Result result);
}
