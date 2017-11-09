package com.lotus.mp2.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lotus.mp2.bet.TransactionInterface;
import com.lotus.mp2.event.EventInterface;
import com.lotus.mp2.user.User;

public interface AdminDAOInterface {
		
		public List<User> getUsersList();
		
		public List<TransactionInterface> getBetList();
		
		public List<TransactionInterface> getBetbyEventId(long eventId);
		
		public List<TransactionInterface> getBetbyCustomerId(long customerId);
		
		public boolean addUser(User user);
		
		public boolean addEvent(EventInterface event);
		
		public boolean updateResult(String outcome, String eventCode);
		
		public boolean updateBalance(BigDecimal balance, String username);

}
