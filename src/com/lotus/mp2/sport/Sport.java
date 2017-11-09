package com.lotus.mp2.sport;

public class Sport implements SportInterface{
	private String sportName;
	private String sportCode;
	
	public Sport(String sportName, String sportCode) {
		this.sportName = sportName;
		this.sportCode = sportCode;
	}

	public String getSportName() {
		return sportName;
	}

	public void setSportName(String sportName) {
		this.sportName = sportName;
	}

	public String getSportCode() {
		return sportCode;
	}

	public void setSportCode(String sportCode) {
		this.sportCode = sportCode;
	}
}
