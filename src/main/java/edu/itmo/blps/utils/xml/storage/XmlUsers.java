package edu.itmo.blps.utils.xml.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlUsers {
	@XmlElement(name = "user")
	private Set<XmlUser> users;
}
