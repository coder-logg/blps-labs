package edu.itmo.blps.utils;

import edu.itmo.blps.dao.user.User;
import edu.itmo.blps.dto.XmlUser;
import edu.itmo.blps.dto.XmlUsers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class XmlUserRepository {
	private final File file;
	private final Marshaller marshaller;
	private final Unmarshaller unmarshaller;
	private final JAXBContext context;

	private final Set<XmlUser> users;

	public XmlUserRepository(@Value("${users-xml.path:src/main/resources/users.xml}") String path)
			throws JAXBException, IOException {
		this.file = new File(path);
		log.warn("XML users file: Path = {}, File = {}, {}", path, file, file.getParentFile());
		boolean isFileAlreadyExists = file.createNewFile();
		this.context = JAXBContext.newInstance(XmlUsers.class);
		this.marshaller = context.createMarshaller();
		this.marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		this.unmarshaller = context.createUnmarshaller();
		if (isFileAlreadyExists)
			this.users = load();
		else
			this.users = new HashSet<>();
	}

	public void addUser(User user) throws JAXBException {
		addUser(new XmlUser(user));
	}

	public void addUser(XmlUser user) throws JAXBException {
		users.add(user);
		marshaller.marshal(new XmlUsers(users), file);
	}

	public void updateUser(XmlUser user) throws JAXBException {
		users.remove(user);
		addUser(user);
	}

	public Set<XmlUser> load() throws JAXBException {
		return ((XmlUsers) unmarshaller.unmarshal(file)).getUsers();
	}
}
