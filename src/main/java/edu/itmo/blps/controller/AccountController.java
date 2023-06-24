package edu.itmo.blps.controller;


import edu.itmo.blps.dao.user.User;
import edu.itmo.blps.service.AuthService;
import edu.itmo.blps.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;

@RestController
@RequestMapping("/")
public class AccountController {
	@Autowired
	private AuthService authService;

	@Autowired
	private UserDetailsServiceImpl userService;

	@PostMapping("/user/login")
	public ResponseEntity<String> login(@RequestBody User user) throws AuthException {
		return ResponseEntity.ok(authService.login(user));
	}

	@PostMapping("/user/signup")
	public ResponseEntity<User> signup(@RequestBody User request){
		return ResponseEntity.ok(authService.registerUser(request));
	}
}
