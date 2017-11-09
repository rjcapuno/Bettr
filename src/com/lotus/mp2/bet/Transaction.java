package com.lotus.mp2.bet;

import java.math.BigDecimal;
import java.util.Date;

import com.lotus.mp2.utils.Result;

public class Transaction implements TransactionInterface{
	private long id;
	private BigDecimal stake;
	private long eventId;
	private String predicted;
	private String transactionId;
	private long customerId;
	private Date placementDate;
	private Result result;
	
	public Transaction(long id, BigDecimal stake, long eventId, String predicted, Date placementDate,
			String transactionId, long customerId, Result result) {
		this.id = id;
		this.stake = stake;
		this.eventId = eventId;
		this.predicted = predicted;
		this.transactionId = transactionId;
		this.customerId = customerId;
		this.placementDate = placementDate;
		this.result = result;
	}
	
	@Override
	public long getId() {
		return this.id;
	}
	
	@Override
	public BigDecimal getStake() {
		return this.stake;
	}

	@Override
	public long getEventId() {
		return this.eventId;
	}

	@Override
	public String getpredicted() {
		return this.predicted;
	}
	
	@Override
	public String getTransactionId() {
		return transactionId;
	}
	@Override
	public long getCustomerId() {
		return customerId;
	}
	
	@Override
	public Date getPlacementDate() {
		return this.placementDate;
	}

	@Override
	public Result getResult() { 
		return this.result;
	}

	@Override
	public void setResult(Result result) {
		this.result = result;
	}
}
