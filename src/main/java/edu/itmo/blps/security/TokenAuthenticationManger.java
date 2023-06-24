//package edu.itmo.blps.security;
//
//import edu.itmo.blps.dao.user.User;
//import edu.itmo.blps.service.UserDetailsServiceImpl;
//import io.jsonwebtoken.JwtException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationServiceException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//import utils.JwtUtils;
//
//@Component
//public class TokenAuthenticationManger implements AuthenticationManager {
//
//	@Autowired
//	private UserDetailsServiceImpl userService;
//
//	@Override
//	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//		TokenAuthentication tokenAuth;
//		if (authentication instanceof TokenAuthentication)
//			tokenAuth = (TokenAuthentication) authentication;
//		else {
//			authentication.setAuthenticated(false);
//			return authentication;
//		}
//		try {
//			if (!JwtUtils.validateAccessToken(tokenAuth.getToken()))
//				throw new AuthenticationServiceException("Invalid JWT access token");
//			User userDetails = (User) userService.loadUserByUsername(JwtUtils.parseToken(tokenAuth.getToken()).getSubject());
//			return new TokenAuthentication(tokenAuth.getToken(), userDetails.getAuthorities(), true, userDetails);
//		} catch (JwtException | IllegalArgumentException ex) {
//			throw new AuthenticationServiceException("Exception JWT token validation: " + ex.getMessage(), ex);
//		} catch (UsernameNotFoundException ex) {
//			throw new AuthenticationServiceException("Username not found: " + ex.getMessage(), ex);
//		}
//	}
//}
