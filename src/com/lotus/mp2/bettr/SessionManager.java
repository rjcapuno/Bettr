package com.lotus.mp2.bettr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.lotus.mp2.dao.UserDAOImpl;
import com.lotus.mp2.exceptions.AccessDeniedException;
import com.lotus.mp2.exceptions.InvalidInputException;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
public class SessionManager {
	static HttpSession session = null;
	
	protected static void createSession(HttpServletRequest request, String username) throws InvalidInputException {
		session = request.getSession(true);
		
		String permission = UserDAOImpl.getUserType(username).toString();
		String userId = UserDAOImpl.getUserId(username).toString();
		
		session.setAttribute("username", username);
		session.setAttribute("permission", permission);
		session.setAttribute("userId", userId);
	}
	
	protected static boolean hasPermission(HttpServletRequest request, String permission) throws AccessDeniedException {
		if(!hasSession(request)) {
			session.invalidate();
			throw new AccessDeniedException("Access denied: log in first");
		}
		
		String sessionPermission = (String) session.getAttribute("permission");
		if(!sessionPermission.equalsIgnoreCase(permission)) {
			throw new AccessDeniedException("Permission denied");
		}
		
		return true;
	}
	
	protected static boolean hasSession(HttpServletRequest request) {
		session = request.getSession();
		if(session.isNew()) {
			return false;
		}
		return true;
	}
	
	protected static String getSessionUserId(HttpServletRequest request) {
		session = request.getSession();
		
		return (String)session.getAttribute("userId");
	}
	
	protected static String getSessionUsername(HttpServletRequest request) {
		session = request.getSession();
		
		return (String)session.getAttribute("username");
	}
	
	

}
