package com.lotus.mp2.dao;

import java.util.List;

import com.lotus.mp2.bet.TransactionInterface;

public interface TransactionDAOInterface {
	public List<TransactionInterface> getTransactionById(String transactionId);
	
	public boolean addTransaction(TransactionInterface transaction);
}
