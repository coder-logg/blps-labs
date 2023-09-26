package edu.itmo.blps.messaging;


import edu.itmo.blps.messaging.dto.EmailMessage;
import edu.itmo.blps.messaging.dto.TransactionDTO;
import edu.itmo.blps.model.transaction.Transaction;
import edu.itmo.blps.model.user.User;
import edu.itmo.blps.service.EmailService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MessagingAspect {
	@Autowired
	private EmailService emailService;

	@AfterReturning(
			pointcut = "execution(* edu.itmo.blps.service.TransactionServiceImpl.addTransaction(edu.itmo.blps.model.transaction.Transaction))",
			returning = "transaction")
	public void sendEmailAfterAddingTransaction(Transaction transaction) {
		emailService.sendEmail(transaction.getSeller().getEmail(), "New transaction", TransactionDTO.createFromTransaction(transaction));
	}

	@AfterReturning(
			pointcut = "execution(* edu.itmo.blps.service.AuthService.register(edu.itmo.blps.model.user.User))",
			returning = "user")
	public void sendEmailAfterRegistration(User user) {
		emailService.sendEmail(EmailMessage.createFromStandardMessage(StandardMessages.WELCOME, user.getEmail()));
	}
}
