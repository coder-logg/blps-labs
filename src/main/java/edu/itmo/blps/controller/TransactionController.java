package edu.itmo.blps.controller;

import edu.itmo.blps.model.customer.Customer;
import edu.itmo.blps.model.transaction.Transaction;
import edu.itmo.blps.model.user.User;
import edu.itmo.blps.service.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequestMapping("/")
@RestController
public class TransactionController {
	@Autowired
	private TransactionServiceImpl transactionServiceImpl;

	@PostMapping("/add-transaction")
	public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction, Principal principal){
		User user = new Customer();
		user.setUsername(principal.getName());
		transaction.setCustomer(user);
		return ResponseEntity.ok(transactionServiceImpl.addTransaction(transaction));
	}

	@GetMapping("/get-transaction/{userId}")
	public List<Transaction> getTransaction(@PathVariable Integer userId){
		return transactionServiceImpl.getTransactions(userId);
	}

	@GetMapping("/test")
	public ResponseEntity<?> getTest(){
		transactionServiceImpl.test();
		return ResponseEntity.ok().build();
	}
}
