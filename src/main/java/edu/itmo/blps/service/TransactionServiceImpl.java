package edu.itmo.blps.service;

import edu.itmo.blps.dao.customer.Customer;
import edu.itmo.blps.dao.transaction.Transaction;
import edu.itmo.blps.dao.transaction.TransactionRepository;
import edu.itmo.blps.dao.user.User;
import edu.itmo.blps.dao.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Transactional(value = "bitronixTransactionManager")
	public Transaction addTransaction(Transaction transaction) {
		return transactionRepository.save(transaction);
	}

	public List<Transaction> getTransactions(Integer userId){
		User user = userRepository.getById(userId);
		if (user instanceof Customer)
			return transactionRepository.findByUser_id(userId);
		else
			return List.of();
	}

	@Transactional(value = "bitronixTransactionManager")
	public void test(){
		userRepository.save(new User("wrong", "wrong"));
		throw new IllegalStateException();
	}
}
