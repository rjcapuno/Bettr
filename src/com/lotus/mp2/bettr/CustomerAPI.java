package com.lotus.mp2.bettr;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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

import com.lotus.mp2.bet.BetInterface;
import com.lotus.mp2.bet.Transaction;
import com.lotus.mp2.bet.TransactionHelper;
import com.lotus.mp2.bet.TransactionInterface;
import com.lotus.mp2.dao.AdminDAO;
import com.lotus.mp2.dao.AdminDAOInterface;
import com.lotus.mp2.dao.CustomerDAO;
import com.lotus.mp2.dao.CustomerDAOInterface;
import com.lotus.mp2.dao.TransactionDAO;
import com.lotus.mp2.dao.TransactionDAOInterface;
import com.lotus.mp2.dao.UserDAO;
import com.lotus.mp2.dao.UserDAOInterface;
import com.lotus.mp2.event.EventInterface;
import com.lotus.mp2.exceptions.AccessDeniedException;
import com.lotus.mp2.exceptions.InvalidInputException;
import com.lotus.mp2.sport.Sport;
import com.lotus.mp2.sport.SportInterface;
import com.lotus.mp2.user.User;
import com.lotus.mp2.user.customer.CustomerInterface;
import com.lotus.mp2.utils.InputValidator;
import com.lotus.mp2.utils.Result;
import com.lotus.mp2.utils.Sports;

import static com.lotus.mp2.utils.Constants.*;

@Path("api/customer")
public class CustomerAPI {
	private static final String PERMISSION = "CUSTOMER";
	private static TransactionDAOInterface transactionDAO = new TransactionDAO();
	private static UserDAOInterface userDAO = new UserDAO();
	private static CustomerDAOInterface customerDAO = new CustomerDAO();
	private static AdminDAOInterface adminDAO = new AdminDAO();
	
	@Context
	HttpServletRequest request;
	
	@Path("bet")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createBet(@FormParam("eventcode") String eventCode, @FormParam("predicted") String predicted, 
			@FormParam("stake") BigDecimal stake) {
		long  eventId = 0;
		long  customerId = 0;
		String username = null;
		stake = stake.setScale(2, RoundingMode.DOWN);
		
		try {
			SessionManager.hasPermission(request, PERMISSION);
			customerId = Long.valueOf(SessionManager.getSessionUserId(request));
			username = SessionManager.getSessionUsername(request);
			InputValidator.isValidBetEventCode(eventCode);
			InputValidator.isValidPredicted(predicted, eventCode);
			InputValidator.isValidBetStake(stake, username);
			InputValidator.isValidBetDate(Calendar.getInstance().getTime(), eventCode);
			eventId = getEventId(eventCode);
			checkHasBetOnEvent(eventCode, customerId);
		} catch (AccessDeniedException e) {
			return Response.status(400).entity(FORBIDDEN).build();
		} catch (InvalidInputException e) {
			return Response.status(400).entity(FAIL + e.getMessage()).build();
		}
		
		List<User> users = userDAO.getUserByUsername(username);
		CustomerInterface customer = (CustomerInterface) users.get(FIRST_INDEX);
		
		BigDecimal newBalance = customer.getBalance().subtract(stake);
		
		if(adminDAO.updateBalance(newBalance, username) != SUCCESS) {
			return Response.status(400).entity(FAIL).build();
		}
		
		Calendar calendar = Calendar.getInstance();
		TransactionInterface bet = new Transaction(DEFAULT_ID, stake, eventId, predicted, calendar.getTime(), 
				TransactionHelper.generateTransactionId(TRANSACTION_ID_LENGTH), customerId, Result.PENDING);
		
		if(transactionDAO.addTransaction(bet) != SUCCESS) {
			return Response.status(400).entity(FAIL).build();
		}
		
		return Response.status(200).entity(OK).build();
	}
	
	@Path("bet")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBetHistory() {
		long customerId = 0;
		
		try {
			SessionManager.hasPermission(request, PERMISSION);
			customerId = Long.valueOf(SessionManager.getSessionUserId(request));
		} catch (AccessDeniedException e) {
			return Response.status(403).entity(FORBIDDEN).build();
		}
		
		List<BetInterface> bets = customerDAO.getBetHistory(customerId);
		
		if(bets.isEmpty()) {
			return Response.status(200).entity(Collections.EMPTY_LIST).build();
		}
		
		return Response.status(200).entity(bets).build();
	}
	
	//TO DO put sports in db
	@Path("sport")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAvailableSports() {
		
		try {
			SessionManager.hasPermission(request, PERMISSION);
		} catch (AccessDeniedException e) {
			return Response.status(403).entity(FORBIDDEN).build();
		}
		
		List<SportInterface> sportsList = new ArrayList<>();
		for(Sports s : Sports.values()) {
			sportsList.add(new Sport(s.toString(), s.getCode().toString()));
		}
		
		return Response.status(200).entity(sportsList).build();
	}
	
	@Path("sport/{sportcode}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEventsBySport(@PathParam("sportcode") String sportCode) {
		
		try {
			SessionManager.hasPermission(request, PERMISSION);
			InputValidator.isValidSportCode(sportCode);
		} catch (AccessDeniedException e) {
			return Response.status(403).entity(FORBIDDEN).build();
		} catch (InvalidInputException e) {
			return Response.status(400).entity(FAIL + e.getMessage()).build();
		}
		
		String sport = getSportValue(sportCode);
		List<EventInterface> events = customerDAO.getEventBySport(sport);
		
		if(events.isEmpty()) {
			return Response.status(200).entity(Collections.EMPTY_LIST).build();
		}
		
		return Response.status(200).entity(events).build();
	}
	
	@Path("balance")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBalance() {
		long customerId = 0;
		
		try {
			SessionManager.hasPermission(request, PERMISSION);
			customerId = Long.valueOf(SessionManager.getSessionUserId(request));
		} catch (AccessDeniedException e) {
			return Response.status(403).entity(FORBIDDEN).build();
		}
		
		List<User> users = userDAO.getUserByCustomerId(customerId);
		if(users.isEmpty()) {
			return Response.status(400).entity(FAIL + " user does not exist").build();
		}
		
		CustomerInterface customer = (CustomerInterface) users.get(FIRST_INDEX);
		return Response.status(200).entity(customer.getBalance()).build();
	}
	
	private long getEventId(String eventCode) throws InvalidInputException {
		List <EventInterface> events = userDAO.getEventByEventCode(eventCode);
		if(events.isEmpty()) {
			throw new InvalidInputException("Event not found");
		}
		
		return events.get(FIRST_INDEX).getId();
	}
	
	private boolean checkHasBetOnEvent(String eventCode, long customerId) throws InvalidInputException {
		List<BetInterface> bets = customerDAO.getBetHistory(customerId);
		
		if(bets.isEmpty()) {
			return false;
		}
		
		for(BetInterface bet : bets) {
			if(bet.getEventCode().equalsIgnoreCase(eventCode)) {
				throw new InvalidInputException("Cannot bet on event twice");
			}
		}
		
		return false;
	}
	
	private String getSportValue(String sportCode) {
		String sport = null;
		
		for(Sports s : Sports.values()) {
			if(sportCode.equalsIgnoreCase(s.getCode())) {
				sport = s.name().toString();
			}
		}
		
		return sport;
	}
}
