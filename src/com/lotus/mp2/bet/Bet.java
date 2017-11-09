package com.lotus.mp2.bet;

import java.math.BigDecimal;
import java.util.Date;

import com.lotus.mp2.utils.Result;

public class Bet implements BetInterface{
	private long id;
	private BigDecimal stake;
	private String eventCode;
	private String predicted;
	private Date placementDate;
	private Result betResult;
	
	public Bet(long id, BigDecimal stake, String eventCode, String predicted, Date placementDate, Result betResult) {
		this.stake = stake;
		this.eventCode = eventCode;
		this.predicted = predicted;
		this.placementDate = placementDate;
		this.betResult = betResult;
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
	public String getEventCode() {
		return this.eventCode;
	}

	@Override
	public String getpredicted() {
		return this.predicted;
	}
	
	@Override
	public Date getPlacementDate() {
		return this.placementDate;
	}
	
	@Override
	public Result getBetResult() {
		return this.betResult;
	}
}