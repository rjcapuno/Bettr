package com.lotus.mp2.bet;

import java.math.BigDecimal;
import java.util.Date;

import com.lotus.mp2.utils.Result;

public class Bet implements BetInterface{
	private BigDecimal stake;
	private String eventCode;
	private String outcome;
	private Date placementDate;
	private Result result;
	
	public Bet(BigDecimal stake, String eventCode, String outcome, Date placementDate, Result result) {
		this.stake = stake;
		this.eventCode = eventCode;
		this.outcome = outcome;
		this.placementDate = placementDate;
		this.result = result;
	}
	
	@Override
	public BigDecimal getStake() {
		return this.stake;
	}

	@Override
	public String getEventCode() {
		return this.eventCode;
	}

	@Override
	public String getOutcome() {
		return this.outcome;
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