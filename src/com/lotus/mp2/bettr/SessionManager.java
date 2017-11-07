package com.lotus.mp2.bettr;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
public class SessionManager {
	static HttpSession session = null;
	
	protected static boolean createSession(HttpServletRequest request, long id) {
		session = request.getSession(true);
		Date createTime = new Date(session.getCreationTime());
		Date lastAccessTime = new Date(session.getLastAccessedTime());
		String userId = String.valueOf(id);
		session.setAttribute("UserId", userId);
		
		return true;		
	}
	
	protected static boolean doesSessionExist(HttpServletRequest request) {
		session = request.getSession(true);
		session.isNew();
		return false;
	}

}
