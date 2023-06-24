package edu.itmo.blps.service;

import edu.itmo.blps.dao.transaction.Transaction;

import java.util.List;

public interface TransactionService {
	Transaction addTransaction(Transaction transaction);

	List<Transaction> getTransactions(Integer userId);

	void test();
}
