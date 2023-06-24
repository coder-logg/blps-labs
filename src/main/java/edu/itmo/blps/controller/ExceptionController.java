package edu.itmo.blps.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
public class ExceptionController {

	@ExceptionHandler(AuthenticationException.class)
	public void httpExceptionHandler(AuthenticationException exc, final HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.FORBIDDEN.value(), exc.getMessage());
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public void usernameNotFoundExceptionHandler(UsernameNotFoundException exc, final HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value(), exc.getMessage());
	}

	@ExceptionHandler({ConstraintViolationException.class})
	public void sqlConstraintsExceptionsHandler(ConstraintViolationException exc, final HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value(), exc.getMessage());
	}
}
