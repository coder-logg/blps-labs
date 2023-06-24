package edu.itmo.blps.dao.company;

import edu.itmo.blps.dao.user.Role;
import edu.itmo.blps.dao.user.User;
import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Table(name = "company")
public class Company extends User {
	private String name;
	private String description;

	{
		addRole(Role.COMPANY);
	}

	public Company(Integer id) {
		super(id);
	}

	public Company(String name, String password) {
		super(name, password);
	}

	public Company(String username, String password, String name) {
		super(username, password);
		this.name = name;
	}

	public Company(String username, String password, String name, String description) {
		super(username, password);
		this.name = name;
		this.description = description;
	}

	@Override
	public String toString() {
		return "Company{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", authorities=" + authorities +
				'}';
	}
}
