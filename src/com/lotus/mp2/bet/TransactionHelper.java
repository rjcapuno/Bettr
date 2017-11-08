package com.lotus.mp2.bet;

import java.util.List;
import java.util.Random;

import com.lotus.mp2.dao.TransactionDAO;
import com.lotus.mp2.dao.TransactionDAOInterface;

public class TransactionHelper {
	
	private static TransactionDAOInterface transactionDAO = new TransactionDAO();
	
	public static String generateTransactionId(int length) {
		String transactionId;
		
		do {
			transactionId = generateId(length);
		} while(isTransactionIdPresent(transactionId));
		
		return transactionId;
	}
	
	public static String generateId(int length) {
		String VALID_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder builder = new StringBuilder();
		Random rnd = new Random();
		
		while(builder.length() < length) {
			int index = (int) (rnd.nextFloat() * VALID_CHARS.length());
			builder.append(VALID_CHARS.charAt(index));
		}
		
		return builder.toString();
	}
	
	public static boolean isTransactionIdPresent(String transactionId) {
		List<TransactionInterface> transactions = transactionDAO.getTransactionById(transactionId);
		
		if(transactions.isEmpty()) {
			return false;
		} else {
			return true;
		}
		
	}
}
