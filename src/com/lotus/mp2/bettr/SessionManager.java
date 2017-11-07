package com.lotus.mp2.bettr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
public class SessionManager {
	static HttpSession session = null;
	
	protected static void createSession(HttpServletRequest request, String username) {
		session = request.getSession(true);
		session.setAttribute("username", username);
	}
	
	protected static boolean hasSession(HttpServletRequest request) {
		session = request.getSession();
		if(session.isNew()) {
			return true;
		}
		return false;
	}

}
