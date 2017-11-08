package com.lotus.mp2.bettr;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lotus.mp2.bet.Transaction;
import com.lotus.mp2.bet.TransactionHelper;
import com.lotus.mp2.bet.TransactionInterface;
import com.lotus.mp2.dao.TransactionDAO;
import com.lotus.mp2.dao.TransactionDAOInterface;
import com.lotus.mp2.exceptions.AccessDeniedException;
import com.lotus.mp2.exceptions.InvalidInputException;
import com.lotus.mp2.utils.InputValidator;
import com.lotus.mp2.utils.Result;
import static com.lotus.mp2.utils.Constants.*;

@Path("api/customer")
public class CustomerAPI {
	private static final String PERMISSION = "CUSTOMER";
	private static TransactionDAOInterface transactionDAO = new TransactionDAO();
	
	@Context
	HttpServletRequest request;
	
	@Path("bet")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createBet(@FormParam("eventcode") String eventCode, @FormParam("predicted") String predicted, 
			@FormParam("stake") BigDecimal stake) {
		String username = null;
		try {
			SessionManager.hasPermission(request, PERMISSION);
			username = SessionManager.getSessionUsername(request);
			InputValidator.isValidBetEventCode(eventCode);
			InputValidator.isValidPredicted(predicted, eventCode);
			InputValidator.isValidBetStake(stake, username);
		} catch (AccessDeniedException e) {
			return Response.status(400).entity(FORBIDDEN).build();
		} catch (InvalidInputException e) {
			return Response.status(400).entity(FAIL + e.getMessage()).build();
		}
		
		Calendar calendar = Calendar.getInstance();
		TransactionInterface bet = new Transaction(stake, eventCode, predicted, calendar.getTime(), 
				TransactionHelper.generateTransactionId(TRANSACTION_ID_LENGTH), username, Result.PENDING);
		
		if(transactionDAO.addTransaction(bet) != SUCCESS) {
			return Response.status(400).entity(FAIL).build();
		}
		
		return Response.status(200).entity(OK).build();
	}
}
