package com.lotus.mp2.event;

import java.util.Date;

import com.lotus.mp2.utils.Sports;

public interface EventInterface {
	public long getId();
	
	boolean isSettled();

	void setSettled(boolean isSettled);

	String getWinner();

	void setWinner(String winner);

	String getEventCode();

	Sports getCategory();

	String getCompetitor1();

	String getCompetitor2();

	Date getEventDate();
}
