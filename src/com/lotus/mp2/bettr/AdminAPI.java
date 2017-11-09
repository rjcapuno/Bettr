package com.lotus.mp2.bettr;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.lotus.mp2.utils.Constants.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lotus.mp2.bet.TransactionInterface;
import com.lotus.mp2.dao.AdminDAO;
import com.lotus.mp2.dao.UserDAO;
import com.lotus.mp2.event.Event;
import com.lotus.mp2.event.EventInterface;
import com.lotus.mp2.exceptions.AccessDeniedException;
import com.lotus.mp2.exceptions.InvalidInputException;
import com.lotus.mp2.user.User;
import com.lotus.mp2.user.admin.Admin;
import com.lotus.mp2.user.customer.Customer;
import com.lotus.mp2.user.customer.CustomerInterface;
import com.lotus.mp2.utils.DAOUtils;
import com.lotus.mp2.utils.InputValidator;
import com.lotus.mp2.utils.Sports;
import com.lotus.mp2.utils.UserType;


@Path("api/admin")
public class AdminAPI {
	private static final boolean DEFAULT_IS_SETTLED = false;
	private static final String DEFAULT_WINNER = null;
	
	private static final String PERMISSION = "ADMIN";
	
	private static AdminDAO adminDAO = new AdminDAO();
	private static UserDAO userDAO = new UserDAO();

	@Context
	HttpServletRequest request;
	
	@Path("user")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers() {
		
		try {
			SessionManager.hasPermission(request, PERMISSION);
		} catch (AccessDeniedException e) {
			return Response.status(403).entity(FORBIDDEN).build();
		}
		
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
		
		try {
			SessionManager.hasPermission(request, PERMISSION);
		} catch (AccessDeniedException e) {
			return Response.status(403).entity(FORBIDDEN).build();
		}
		
		List<User> users = userDAO.getUserByUsername(username);
		
		if(users.isEmpty()) {
			return Response.status(200).entity(Collections.EMPTY_LIST).build();
		}
		
		return Response.status(200).entity(users.get(FIRST_INDEX)).build();
	}
	
	@Path("user")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createUser(@FormParam("type") String type, @FormParam("username") String username,
			@FormParam("firstname") String firstName, @FormParam("lastname") String lastName,
			@FormParam("password") String password) {
		
		try {
			SessionManager.hasPermission(request, PERMISSION);
			InputValidator.isValidUserType(type);
			InputValidator.isValidUsername(username);
			InputValidator.isValidName(firstName);
			InputValidator.isValidName(lastName);
			InputValidator.isValidPassword(password);
		} catch (AccessDeniedException e) {
			return Response.status(400).entity(FORBIDDEN).build();
		} catch (InvalidInputException e) {
			return Response.status(400).entity(FAIL + e.getMessage()).build();
		}
		
		User user = null;
		if(type.equalsIgnoreCase("admin")) {
			user = new Admin(DEFAULT_ID, username, firstName, lastName, password, UserType.ADMIN);
		} else {
			user = new Customer(DEFAULT_ID, username, firstName, lastName, password, UserType.CUSTOMER, new BigDecimal(0));
		}

		if(adminDAO.addUser(user) != SUCCESS) {
			return Response.status(400).entity(FAIL).build();
		}
		
		return Response.status(200).entity(OK).build();
	}
	
	@Path("event")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createEvent(@FormParam("eventcode") String eventCode, @FormParam("sport") String sport, @FormParam("competitor1") String competitor1, 
			@FormParam("competitor2") String competitor2, @FormParam("eventdate") String eventDate) {
		Sports category = null;
		Date date = null;
		
		sport = sport.toUpperCase();
		try {
			SessionManager.hasPermission(request, PERMISSION);
			InputValidator.isValidEventCode(eventCode);
			InputValidator.isValidSport(sport);
			InputValidator.isValidCompetitor(competitor1);
			InputValidator.isValidCompetitor(competitor2);
			InputValidator.isValidDate(eventDate);
			
			category = Sports.valueOf(sport);
			date = DAOUtils.convertStringToDate(eventDate);
		} catch (AccessDeniedException e) {
			return Response.status(400).entity(FORBIDDEN).build();
		} catch (InvalidInputException e) {
			return Response.status(400).entity(FAIL + e.getMessage()).build();
		}
		
		EventInterface event = new Event(DEFAULT_ID, eventCode, category, competitor1, competitor2, date, DEFAULT_IS_SETTLED, DEFAULT_WINNER);
		
		if(adminDAO.addEvent(event) != SUCCESS) {
			return Response.status(400).entity(FAIL).build();
		}
		
		return Response.status(200).entity(OK).build();
	}
	
	@Path("event")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response getAllEvents() {
		
		try {
			SessionManager.hasPermission(request, PERMISSION);
		} catch (AccessDeniedException e) {
			return Response.status(403).entity(FORBIDDEN).build();
		}
		
		List<EventInterface> events = userDAO.getAllEvents();
		
		if(events.isEmpty()) {
			return Response.status(200).entity(Collections.EMPTY_LIST).build();
		}
		
		return Response.status(200).entity(events).build();
	}
	
	@Path("event/{eventcode}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response getEventbyEventCode(@PathParam("eventcode") String eventCode) {
		
		try {
			SessionManager.hasPermission(request, PERMISSION);
		} catch (AccessDeniedException e) {
			return Response.status(403).entity(FORBIDDEN).build();
		}
		
		List<EventInterface> events = userDAO.getEventByEventCode(eventCode);
		
		if(events.isEmpty()) {
			return Response.status(200).entity(Collections.EMPTY_LIST).build();
		}
		
		return Response.status(200).entity(events).build();
	}
	
	@Path("bet")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response getAllTransactions() {
		
		try {
			SessionManager.hasPermission(request, PERMISSION);
		} catch (AccessDeniedException e) {
			return Response.status(403).entity(FORBIDDEN).build();
		}
		
		List<TransactionInterface> transactions = adminDAO.getBetList();
		
		if(transactions.isEmpty()) {
			return Response.status(200).entity(Collections.EMPTY_LIST).build();
		}
		
		return Response.status(200).entity(transactions).build();
	}
	
	@Path("bet/{filter}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response getTransactionByEventCode(@PathParam("filter") String filter) {
		
		try {
			SessionManager.hasPermission(request, PERMISSION);
		} catch (AccessDeniedException e) {
			return Response.status(403).entity(FORBIDDEN).build();
		}
		
		List<TransactionInterface> transactions = Collections.emptyList();
		
		List<User> users = userDAO.getUserByUsername(filter);
		if(!users.isEmpty()) {
			transactions = adminDAO.getBetbyCustomerId(users.get(FIRST_INDEX).getId());
		}
		
		List<EventInterface> events = userDAO.getEventByEventCode(filter);
		if(!events.isEmpty()) {
			transactions = adminDAO.getBetbyEventId(events.get(FIRST_INDEX).getId());
		}
		
		if(transactions.isEmpty()) {
			return Response.status(200).entity(Collections.EMPTY_LIST).build();
		}
		
		return Response.status(200).entity(transactions).build();
	}
	
	@Path("outcome/result")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response setResult(@FormParam("outcome") String outcome,	@FormParam("eventcode") String eventCode) {
		
		try {
			SessionManager.hasPermission(request, PERMISSION);
			InputValidator.isNotNull(outcome);
			InputValidator.isNotNull(eventCode);
			InputValidator.doesEventCodeExists(eventCode);
			if(!outcome.equalsIgnoreCase("draw")) {
				InputValidator.isValidPredicted(outcome, eventCode);
			}
		} catch (AccessDeniedException e) {
			return Response.status(400).entity(FORBIDDEN).build();
		} catch (InvalidInputException e) {
			return Response.status(400).entity(FAIL + e.getMessage()).build();
		}
		
		List<EventInterface> events = userDAO.getEventByEventCode(eventCode);

		if(events.isEmpty()) {
			return Response.status(400).entity(FAIL).build();
		}
		
		EventInterface event = events.get(FIRST_INDEX);
		if(!(event.getWinner() == null)) {
			return Response.status(400).entity(FAIL + "can set result only once").build();
		}
		
		if(adminDAO.updateResult(outcome, eventCode) != SUCCESS) {
			return Response.status(400).entity(FAIL).build();
		}
		
		return Response.status(200).entity(OK).build();
	}
	
	@Path("outcome/result/{eventcode}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResult(@PathParam("eventcode") String eventCode) {
		
		try {
			SessionManager.hasPermission(request, PERMISSION);
			InputValidator.isNotNull(eventCode);
			InputValidator.doesEventCodeExists(eventCode);
		} catch (AccessDeniedException e) {
			return Response.status(400).entity(FORBIDDEN).build();
		} catch (InvalidInputException e) {
			return Response.status(400).entity(FAIL + e.getMessage()).build();
		}
		
		List<EventInterface> events = userDAO.getEventByEventCode(eventCode);

		if(events.isEmpty()) {
			return Response.status(400).entity(FAIL).build();
		}
		
		EventInterface event = events.get(FIRST_INDEX);
		if(event.getWinner() == null) {
			return Response.status(200).entity("No result yet").build();
		}
		return Response.status(200).entity(event.getWinner()).build();
	}
	
	@Path("user/addBalance")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response setResult(@FormParam("username") String username, @FormParam("amount") BigDecimal amount) {
		
		amount = amount.setScale(2, RoundingMode.DOWN);
		
		try {
			SessionManager.hasPermission(request, PERMISSION);
			InputValidator.isValidAmount(amount, username);
		} catch (AccessDeniedException e) {
			return Response.status(400).entity(FORBIDDEN).build();
		} catch (InvalidInputException e) {
			return Response.status(400).entity(FAIL + e.getMessage()).build();
		}
		
		List<User> users = userDAO.getUserByUsername(username);
		CustomerInterface customer = (CustomerInterface) users.get(FIRST_INDEX);
		
		BigDecimal newBalance = customer.getBalance().add(amount);
		
		if(adminDAO.updateBalance(newBalance, username) != SUCCESS) {
			return Response.status(400).entity(FAIL).build();
		}
		
		return Response.status(200).entity(OK).build();
	}
}
