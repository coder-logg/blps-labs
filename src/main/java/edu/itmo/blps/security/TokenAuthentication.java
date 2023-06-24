//package edu.itmo.blps.security;
//
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//
//@Data
//@NoArgsConstructor
//public class TokenAuthentication implements Authentication {
//	private String token;
//	private Collection<? extends GrantedAuthority> authorities;
//	private boolean isAuthenticated;
//	private UserDetails details;
//
//	public TokenAuthentication(String token) {
//		this.token = token;
//	}
//
//	public TokenAuthentication(String token, Collection<GrantedAuthority> authorities, boolean isAuthenticated,
//							   UserDetails details) {
//		this.token = token;
//		this.authorities = authorities;
//		this.isAuthenticated = isAuthenticated;
//		this.details = details;
//	}
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return authorities;
//	}
//
//	@Override
//	public Object getCredentials() {
//		return token;
//	}
//
//	@Override
//	public Object getDetails() {
//		return details;
//	}
//
//	@Override
//	public String getName() {
//		if (details != null)
//			return details.getUsername();
//		else
//			return null;
//	}
//
//	@Override
//	public Object getPrincipal() {
//		return details;
//	}
//
//	@Override
//	public boolean isAuthenticated() {
//		return isAuthenticated;
//	}
//
//	@Override
//	public void setAuthenticated(boolean b) throws IllegalArgumentException {
//		isAuthenticated = b;
//	}
//
//}
