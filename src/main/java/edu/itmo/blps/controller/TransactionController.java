package edu.itmo.blps.controller;

import edu.itmo.blps.dao.transaction.Transaction;
import edu.itmo.blps.service.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/")
@RestController
public class TransactionController {
	@Autowired
	TransactionServiceImpl transactionServiceImpl;

//	@PostMapping("/cart/put-device")
//	public Response addCart(@RequestBody Cart request, Principal principal) {
//		return transactionService.addCart(request);
//	}

	@PostMapping("/add-transaction")
	public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction request){
		return ResponseEntity.ok(transactionServiceImpl.addTransaction(request));
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
