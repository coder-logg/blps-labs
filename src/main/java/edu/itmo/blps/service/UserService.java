package edu.itmo.blps.service;

import edu.itmo.blps.dao.user.User;
import edu.itmo.blps.dao.user.UserRepository;
import edu.itmo.blps.dto.XmlUser;
import edu.itmo.blps.utils.XmlUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.xml.bind.JAXBException;

@Service
@Slf4j
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private XmlUserRepository xmlUserRepository;

	@Autowired
	@Lazy
	private PasswordEncoder encoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("'" + username + "' doesn't exist"));
	}

	public User getById(Integer userId) {
		return userRepository.getById(userId);
	}

	public User getByUsername(String username) {
		return userRepository.getByUsername(username);
	}

	@Transactional(transactionManager = "bitronixTransactionManager")
	public User save(User user) {
		User userFromDb = userRepository.save(user);
		try {
			xmlUserRepository.addUser(user);
			return userFromDb;
		} catch (JAXBException e) {
			log.error("Error saving user in xml: {}", e.getMessage());
			e.printStackTrace();
			return userFromDb;
		}
	}

	@Transactional(transactionManager = "bitronixTransactionManager")
	public User updateProfile(User user, String username) {
		// todo try-catch for username sql-constraint
		User old = getByUsername(username);
		if (old != null) {
			user.setId(old.getId());
			user.setPassword(encoder.encode(user.getPassword()));
			User changed = userRepository.save(user);
			try {
				xmlUserRepository.updateUser(new XmlUser(changed));
			} catch (JAXBException e) {
				log.error("Error saving user in xml: {}", e.getMessage());
				e.printStackTrace();
			}
			return changed;
		}
		else throw new UsernameNotFoundException("Username '" + username + "' was not found");
	}

	@Transactional(transactionManager = "bitronixTransactionManager")
	public User saveIfNotExists(User user) {
		if (userRepository.existsByUsername(user.getUsername()))
			throw new EntityExistsException("User with username '" + user.getUsername() + "' already exists");
		return save(user);

	}
}
