package edu.itmo.blps.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.itmo.blps.messaging.MessageSender;
import edu.itmo.blps.messaging.dto.EmailMessage;
import edu.itmo.blps.model.customer.Customer;
import edu.itmo.blps.model.device.Device;
import edu.itmo.blps.model.transaction.Transaction;
import edu.itmo.blps.model.transaction.TransactionRepository;
import edu.itmo.blps.model.user.User;
import edu.itmo.blps.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private MessageSender sender;

	@Transactional(value = "bitronixTransactionManager")
	public Transaction addTransaction(Transaction transaction) {
		Objects.requireNonNull(transaction.getCustomer());
		User customer = transaction.getCustomer();
		if (customer.getId() != null)
			customer = userRepository.getById(transaction.getCustomerId());
		else if (customer.getUsername() != null)
			customer = userRepository.getByUsername(customer.getUsername());
		Device device = deviceService.getDevice(transaction.getDeviceId());
		transaction.setSeller(device.getCompany());
		transaction.setCustomer(customer);
		transaction.setDevice(device);
		Transaction transactionFromDb = transactionRepository.save(transaction);
		return transactionFromDb;
	}

	public List<Transaction> getTransactions(Integer userId){
		User user = userRepository.getById(userId);
		if (user instanceof Customer)
			return transactionRepository.findByCustomer_id(userId);
		else
			return List.of();
	}

	public void test(){
//		gateway.sendMessage(new MqttMessage("mqtt message".getBytes()));
//			sender.publish(new MqttMessage("mqtt message fffff".getBytes()));
//			sender.publish("hahahaha".getBytes());
//			sender.publishPaho(new MqttMessage("hahaha".getBytes()));
//		gateway.publish("hahahaha".getBytes());
		try {
			sender.publish(new EmailMessage("hahaha", "olige", "haahahahaha"));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
//		throw new IllegalStateException();
	}
}
