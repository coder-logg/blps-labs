package edu.itmo.blps.messaging;

import edu.itmo.blps.messaging.dto.EmailMessage;
import edu.itmo.blps.messaging.dto.UserDto;
import lombok.Getter;

@Getter
public enum StandardMessages {
	WELCOME("Welcome to ChipDip", "Welcome to ChipDip. We're exited to have you onboard.\n" +
			" Cheers, ChipDip command");

	private final String messageText;
	private final String messageSubject;
	private UserDto user;

	StandardMessages( String messageSubject, String messageText) {
		this.messageText = messageText;
		this.messageSubject = messageSubject;
	}

	public StandardMessages setUser(UserDto user) {
		this.user = user;
		return this;
	}
}
