package com.lotus.mp2.dao;

import java.util.List;

import com.lotus.mp2.bet.BetInterface;
import com.lotus.mp2.event.EventInterface;

public interface CustomerDAOInterface {
	public List<BetInterface> getBetHistory(long customerId);
	
	public List<EventInterface> getEventBySport(String sport);
}
