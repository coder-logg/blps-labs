package edu.itmo.blps.controller;


import edu.itmo.blps.dao.user.User;
import edu.itmo.blps.service.AuthService;
import edu.itmo.blps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class AccountController {
	@Autowired
	private AuthService authService;

	@Autowired
	private UserService userService;

	@PostMapping("/user/login")
	public ResponseEntity<String> login(@RequestBody User user) {
		return ResponseEntity.ok(authService.login(user));
	}

	@PostMapping("/user/signup")
	public ResponseEntity<User> signup(@RequestBody User user){
		return ResponseEntity.ok(authService.register(user));
	}

	@PutMapping("/{username}/change-profile")
	@PreAuthorize("#username == authentication.name")
	public ResponseEntity<User> changeProfile(@RequestBody User user, @PathVariable String username) {
		return ResponseEntity.ok(userService.updateProfile(user, username));
	}
}
