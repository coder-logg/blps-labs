package edu.itmo.blps.messaging.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;
import edu.itmo.blps.messaging.StandardMessages;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RequiredArgsConstructor
@Data
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		property = "type")
public class EmailMessage {
	@Size(max = 50, message = "subject wrong size")
	private final String subject;

	@NotNull(message = "emailTo is null")
	@Email(message = "emailTo not valid email")
	private final String emailTo;

	@NotNull(message = "message content is null")
	private final Object payload;

	public static EmailMessage createFromStandardMessage(StandardMessages message, String emailTo) {
		return new EmailMessage(message.getMessageSubject(), emailTo, message.getMessageText());
	}
}
