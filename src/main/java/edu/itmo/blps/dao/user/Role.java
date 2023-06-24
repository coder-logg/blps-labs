package edu.itmo.blps.dao.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
	ADMIN,
	USER,
	CUSTOMER,
	COMPANY;

	@Override
	public String getAuthority() {
		return name();
	}
}
