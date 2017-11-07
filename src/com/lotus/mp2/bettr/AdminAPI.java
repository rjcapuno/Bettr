package com.lotus.mp2.bettr;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lotus.mp2.dao.AdminDAO;
import com.lotus.mp2.dao.UserDAO;
import com.lotus.mp2.exceptions.InvalidInputException;
import com.lotus.mp2.user.User;
import com.lotus.mp2.user.admin.Admin;
import com.lotus.mp2.user.customer.Customer;
import com.lotus.mp2.utils.InputValidator;
import com.lotus.mp2.utils.UserType;


@Path("api/admin")
public class AdminAPI {
	private static final String OK = "success: true";
	private static final String FAIL = "success: false, error: ";
	private static final String EMPTY = "{}";
	private static final boolean SUCCESS = true;
	private static final int FIRST_INDEX = 0;
	
	private static AdminDAO adminDAO = new AdminDAO();
	private static UserDAO userDAO = new UserDAO();

	@Path("user")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers() {
		List<User> users = adminDAO.getUsersList();
		
		if(users.isEmpty()) {
			return Response.status(200).entity(Collections.EMPTY_LIST).build();
		}
		
		return Response.status(200).entity(users).build();
	}
	
	@Path("user/{username}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserByUsername(@PathParam("username") String username) {
		List<User> users = userDAO.getUserByUsername(username);
		
		if(users.isEmpty()) {
			return Response.status(200).entity(Collections.EMPTY_LIST).build();
		}
		
		return Response.status(200).entity(users.get(FIRST_INDEX)).build();
	}
	
	@Path("create")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createUser(@FormParam("type") String type, @FormParam("username") String username,
			@FormParam("firstname") String firstName, @FormParam("lastname") String lastName,
			@FormParam("password") String password) {
		
		try {
			InputValidator.isValidUserType(type);
			InputValidator.isValidUsername(username);
			InputValidator.isValidName(firstName);
			InputValidator.isValidName(lastName);
			InputValidator.isValidPassword(password);
		} catch (InvalidInputException e) {
			return Response.status(400).entity(FAIL + e.getMessage()).build();
		}
		
		User user = null;
		if(type.equalsIgnoreCase("admin")) {
			user = new Admin(username, firstName, lastName, password, UserType.ADMIN);
		} else {
			user = new Customer(username, firstName, lastName, password, UserType.CUSTOMER, new BigDecimal(0));
		}

		if(adminDAO.addUser(user) != SUCCESS) {
			return Response.status(400).entity(FAIL).build();
		}
		
		return Response.status(200).entity(OK).build();
	}
	
	
}
