package edu.itmo.blps.dao.user;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import edu.itmo.blps.dao.company.Company;
import edu.itmo.blps.dao.customer.Customer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@EqualsAndHashCode(of = {"id", "username"})
@NoArgsConstructor
@Table(name = "_user")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
		property = "role",
		include = JsonTypeInfo.As.EXISTING_PROPERTY,
		defaultImpl = User.class)
@JsonSubTypes({@Type(value = Company.class, name = "company"),
		@Type(value = Customer.class, name = "customer")})
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
	protected String username;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	protected String password;

	@JsonIgnore
	@Transient
	protected List<GrantedAuthority> authorities = new ArrayList<>();

	public void addRole(GrantedAuthority authority) {
		authorities.add(authority);
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User(Integer id) {
		this.id = id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}
}
