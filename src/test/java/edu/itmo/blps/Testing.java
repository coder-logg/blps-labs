package edu.itmo.blps;

import edu.itmo.blps.dao.user.Role;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class Testing {
	@Test
	public void getEnumName(){
		Role role = Role.ADMIN;
		Assertions.assertEquals(role.name(), "ADMIN");
	}

}
