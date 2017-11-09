package com.lotus.mp2.bettr;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.lotus.mp2.bet.TransactionInterface;
import com.lotus.mp2.dao.AdminDAO;
import com.lotus.mp2.dao.AdminDAOInterface;
import com.lotus.mp2.dao.UserDAO;
import com.lotus.mp2.dao.UserDAOInterface;
import com.lotus.mp2.event.EventInterface;
import com.lotus.mp2.user.User;
import com.lotus.mp2.user.customer.CustomerInterface;
import com.lotus.mp2.utils.Result;

import static com.lotus.mp2.utils.Constants.*;

public class SettlementRunner extends Thread implements ServletContextListener{
	private static UserDAOInterface userDAO = new UserDAO();
	private static AdminDAOInterface adminDAO = new AdminDAO();
	private static SettlementRunner settler = new SettlementRunner();
	public void run() {
		while(true) {
			try {
				List<EventInterface> events = userDAO.getAllEvents();
				updateResults(events);
				
				Thread.sleep(SYSTEM_SLEEP_TIME);
			} catch (InterruptedException | NullPointerException e) {
				e.printStackTrace();
			}
		}
		
	}
	private synchronized void updateResults(List<EventInterface> events) {
		for(EventInterface event : events) {
			 
			 if(hasStarted(event.getEventDate()) && !event.isSettled()) {
				 List<TransactionInterface> transactions = adminDAO.getBetbyEventId(event.getId());
				 settleBetsAndTransactions(transactions, event.getWinner(), event.getId());
				 
			 }
			 
		}
	}
	private void settleBetsAndTransactions(List<TransactionInterface> transactions, String winner, long eventId) {
		for(TransactionInterface transaction : transactions) {
			 adminDAO.updateTransactionResult(transaction.getId(), generateResult(winner, transaction.getpredicted()));
			 settleBets(transaction, eventId);
		 }
	}
	
	private Result generateResult(String winner, String predicted) {
		if(winner.equalsIgnoreCase(predicted)) {
			return Result.WIN;
		}
		
		if(winner.equalsIgnoreCase("draw")) {
			return Result.DRAW;
		}
		
		return Result.LOSE;
	}
	
	private void settleBets(TransactionInterface transaction, long eventId) {
		 if(!transaction.getResult().equals(Result.PENDING)) {
			 
				List<User> users = userDAO.getUserByCustomerId(transaction.getCustomerId());
				CustomerInterface customer = (CustomerInterface) users.get(0);
				
				
				BigDecimal winnings = getWinnings(transaction.getResult(), transaction.getStake());
				BigDecimal newBalance = customer.getBalance().add(winnings);
				adminDAO.updateBalance(newBalance, customer.getUsername());
				adminDAO.updateEventIsSettled(eventId);
				 
			 }
	}
	
	private boolean hasStarted(Date eventDate) {
		if(Calendar.getInstance().getTime().after(eventDate)) {
			return true;
		} else {
			return false;
		}
	}
	
	private BigDecimal getWinnings(Result result, BigDecimal stake) {
		switch(result) {
			case WIN:	return stake.multiply(WIN_MULTIPLIER);
			case LOSE:	return ZERO;
			case DRAW:	return stake;
			case PENDING: return ZERO;
		}
		return ZERO;
	}
	@Override
	public void contextDestroyed(ServletContextEvent e) {
		settler.interrupt();
		
	}
	@Override
	public void contextInitialized(ServletContextEvent e) {
		 settler.start();
		
	}
	
}
