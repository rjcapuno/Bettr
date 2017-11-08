package com.lotus.mp2.bettr;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

@Path("api/customer")
public class CustomerAPI {
	
	@Context
	HttpServletRequest request;
	
	
}
