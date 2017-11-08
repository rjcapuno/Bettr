package com.lotus.mp2.bet;

import java.math.BigDecimal;
import java.util.Date;

import com.lotus.mp2.utils.Result;

public class Transaction extends Bet implements TransactionInterface{
	private String transactionId;
	private String username;
	
	public Transaction(BigDecimal stake, String eventCode, String outcome, Date placementDate,
			String transactionId, String username, Result result) {
		super(stake, eventCode, outcome, placementDate, result);
		this.transactionId = transactionId;
		this.username = username;
	}
	
	@Override
	public String getTransactionId() {
		return transactionId;
	}
	@Override
	public String getUsername() {
		return username;
	}
}
