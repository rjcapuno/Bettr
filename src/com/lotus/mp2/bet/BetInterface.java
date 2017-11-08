package com.lotus.mp2.bet;

import java.math.BigDecimal;
import java.util.Date;

import com.lotus.mp2.utils.Result;


public interface BetInterface {
	BigDecimal getStake();
	String getEventCode();
	String getOutcome();
	Date getPlacementDate();
	Result getResult();
	void setResult(Result result);
}
