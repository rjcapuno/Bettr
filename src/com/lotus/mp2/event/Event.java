package com.lotus.mp2.event;

import java.util.Date;

import com.lotus.mp2.utils.Sport;


public class Event implements EventInterface{
	private String eventCode;
	private Sport category;
	private String competitor1;
	private String competitor2;
	private boolean isSettled;
	private Date eventDate;
	private String winner;
	
	public Event(String eventCode, Sport category, String competitor1,
			String competitor2, Date eventDate, boolean isSettled, String winner) {
		this.eventCode = eventCode;
		this.category = category;
		this.competitor1 = competitor1;
		this.competitor2 = competitor2;
		this.eventDate = eventDate;
		this.isSettled = isSettled;
		this.winner = winner;
	}

	@Override
	public boolean isSettled() {
		return isSettled;
	}

	@Override
	public void setSettled(boolean isSettled) {
		this.isSettled = isSettled;
	}

	@Override
	public String getWinner() {
		return winner;
	}

	@Override
	public void setWinner(String winner) {
		this.winner = winner;
	}

	@Override
	public String getEventCode() {
		return eventCode;
	}

	@Override
	public Sport getCategory() {
		return category;
	}

	@Override
	public String getCompetitor1() {
		return competitor1;
	}

	@Override
	public String getCompetitor2() {
		return competitor2;
	}

	@Override
	public Date getEventDate() {
		return eventDate;
	}
	
	
}
