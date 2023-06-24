package edu.itmo.blps.dto;

import edu.itmo.blps.dao.company.Company;
import edu.itmo.blps.dao.customer.Customer;
import edu.itmo.blps.dao.user.User;
import edu.itmo.blps.dao.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationEntity {
	private Role role;
	private String username;
	private String password;

	public Customer asCustomer(){
			return new Customer(username, password);
	}

	public Company asCompany() {
			return new Company(username, password);
	}

	public User asUser(){
		if (role == Role.CUSTOMER)
			return new Customer(username, password);
		if (role == Role.COMPANY)
			return new Company(username, password);
		return new User(username, password);
	}
}
