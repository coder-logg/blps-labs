package edu.itmo.blps.service;

import edu.itmo.blps.messaging.dto.EmailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class SchedulingSevice {
	@Autowired
	private EmailService emailService;

	@Autowired
	private UserService userService;

	@Scheduled(cron = "0 0 10 * * SUN")
	public void sendNews(){
		for (String userEmail : userService.getUserEmails()) {
			emailService.sendEmail(new EmailMessage("ChipDip news", userEmail, "We have great news for you...."));
		}
	}
}
