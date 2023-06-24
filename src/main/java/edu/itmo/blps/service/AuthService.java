package edu.itmo.blps.service;

import edu.itmo.blps.dao.customer.Customer;
import edu.itmo.blps.dao.user.User;
import edu.itmo.blps.dao.user.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.JwtUtils;

import javax.persistence.EntityExistsException;
import javax.security.auth.message.AuthException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Slf4j
public class AuthService {
	@Autowired
	private UserDetailsServiceImpl userService;

	@Autowired
	private BasketService basketService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder encoder;

	public String login(User user) throws AuthException {
		final User userFromDb = (User) userService.loadUserByUsername(user.getUsername());
		if (encoder.matches(user.getPassword(), userFromDb.getPassword())) {
			return JwtUtils.generateToken(userFromDb);
		} else {
			throw new AuthException("Incorrect password");
		}
	}


	@Transactional(value = "bitronixTransactionManager")
	public User registerUser(User newUser) {
		log.debug("new user trying to register {}", newUser);
		boolean isValidStrings = Stream.of(newUser.getUsername(), newUser.getPassword())
				.noneMatch((x) -> Objects.isNull(x) | x.isEmpty());
		if (!isValidStrings)
			throw new IllegalArgumentException("You enter illegal username or password");
		newUser.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));
		if (userRepository.existsByUsername(newUser.getUsername()))
			throw new EntityExistsException("User with username '" + newUser.getUsername() + "' already exists");
		User userFromDb = userRepository.save(newUser);
		if (!Objects.isNull(userFromDb) && userFromDb instanceof Customer)
			basketService.createBasketForCustomerId(userFromDb.getId());
		return userFromDb;
	}

	public Optional<String> getAccessToken(String refreshToken) {
		if (JwtUtils.validateAccessToken(refreshToken)) {
			final Claims claims = JwtUtils.parseToken(refreshToken);
			return Optional.of(JwtUtils.generateToken((User) userService.loadUserByUsername(claims.getSubject())));
		}
		return Optional.empty();
	}
}
