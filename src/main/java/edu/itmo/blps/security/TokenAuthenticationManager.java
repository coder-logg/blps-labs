//package edu.itmo.blps.security;
//
//import edu.itmo.blps.dao.user.User;
//import edu.itmo.blps.service.UserDetailsServiceImpl;
//import io.jsonwebtoken.Claims;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationServiceException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.stereotype.Service;
//import utils.JwtUtils;
//
//import java.util.Date;
//
////@Service
//public class TokenAuthenticationManager implements AuthenticationManager {
//
////	@Autowired
//	private UserDetailsServiceImpl userDetailsService;
//
//	@Override
//	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//		try {
//			if (authentication instanceof TokenAuthentication)
//				return processAuthentication((TokenAuthentication) authentication);
//			authentication.setAuthenticated(false);
//			return authentication;
//		} catch (Exception ex) {
//			if(ex instanceof AuthenticationServiceException)
//				throw ex;
//			ex.printStackTrace();
//		}
//		return null;
//	}
//
//	private TokenAuthentication processAuthentication(TokenAuthentication authentication) throws AuthenticationException {
//		String token = authentication.getToken();
//		Claims claims;
//		try {
//			claims = JwtUtils.parseToken(token);
//		} catch (Exception ex) {
//			throw new AuthenticationServiceException("Token corrupted");
//		}
//		if (claims.getExpiration() == null)
//			throw new AuthenticationServiceException("Invalid token");
//		if (claims.getExpiration().after(new Date()))
//			return buildFullTokenAuthentication(authentication, claims);
//		else
//			throw new AuthenticationServiceException("Token expired date error");
//	}
//
//	private TokenAuthentication buildFullTokenAuthentication(TokenAuthentication authentication, Claims claims) {
//		User user = (User) userDetailsService.loadUserByUsername(claims.getSubject());
//		if (user.isEnabled())
//			return new TokenAuthentication(authentication.getToken(), user.getAuthorities(), true, user);
//		else
//			throw new AuthenticationServiceException("User disabled");
//	}
//}
