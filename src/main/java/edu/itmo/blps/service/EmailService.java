package edu.itmo.blps.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.itmo.blps.messaging.MessageSender;
import edu.itmo.blps.messaging.dto.EmailMessage;
import edu.itmo.blps.model.transaction.Transaction;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Service
@Aspect
public class EmailService {
	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private MessageSender sender;

	public void sendEmail(@NotNull String mailTo, @NotNull Object payload) {
		sendEmail(new EmailMessage(null, mailTo, payload));
	}

	public void sendEmail(@NotNull @NotEmpty String mailTo, @NotNull @NotEmpty String subject, @NotNull Object payload) {
		sendEmail(new EmailMessage(subject, mailTo, payload));
	}

	public void sendEmail(@NotNull EmailMessage message) {
		try {
			String json = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(message);
			sender.publish(new MqttMessage(json.getBytes()));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
