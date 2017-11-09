package com.lotus.mp2.bet;

import java.math.BigDecimal;
import java.util.Date;

import com.lotus.mp2.utils.Result;


public interface BetInterface {
	public long getId();
	
	BigDecimal getStake();
	
	String getEventCode();
	
	String getpredicted();
	
	public Date getPlacementDate();
	
	public Result getBetResult();
}
