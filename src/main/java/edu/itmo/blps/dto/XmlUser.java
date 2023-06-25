package edu.itmo.blps.dto;

import edu.itmo.blps.dao.user.Role;
import edu.itmo.blps.dao.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.xml.bind.annotation.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "user")
@XmlType(propOrder = { "id", "username", "password","roles" })
public class XmlUser {
	@XmlAttribute
	private Integer id;
	@XmlElement
	private String username;
	@XmlElement
	private String password;
	@XmlElementWrapper(name = "roles")
	@XmlElement(name = "role")
	private Set<String> roles;

	public XmlUser(User user) {
		id = user.getId();
		username = user.getUsername();
		password = user.getPassword();
		roles = new HashSet<>(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
	}


}
