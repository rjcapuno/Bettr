package com.lotus.mp2.bettr;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lotus.mp2.dao.UserDAOImpl;
import com.lotus.mp2.exceptions.InvalidInputException;

@Path("api")
public class BettrAPI {
	private static final String OK = "success: true";
	private static final String FAIL = "success: false, error: ";
	
	@Path("login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response login(@FormParam("username") String username, @FormParam("password") String password) {
		
		try {
			UserDAOImpl.validateCredentials(username, password);
		} catch (InvalidInputException e){
			return Response.status(200).entity(FAIL + e.getMessage()).build();
		}
		
		return Response.status(200).entity(OK).build();
	}

}
