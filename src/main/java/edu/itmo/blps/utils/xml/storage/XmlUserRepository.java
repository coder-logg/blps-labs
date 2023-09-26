package edu.itmo.blps.utils.xml.storage;

import edu.itmo.blps.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class XmlUserRepository {
	private final File file;
	private final Marshaller marshaller;
	private final Unmarshaller unmarshaller;
	private final JAXBContext jaxbContext;
	private final String fileName ="users.xml";

	private final Set<XmlUser> users;

	public XmlUserRepository(ApplicationContext appContext) throws IOException, JAXBException {
		Set<XmlUser> tmp;
		this.file = new File(appContext.getEnvironment().getProperty("users-xml-path", fileName));
		log.info("XML users file: File = {}, {}", file, file.getParentFile());
		boolean isFileAlreadyExists = file.createNewFile();
		this.jaxbContext = JAXBContext.newInstance(XmlUsers.class);
		this.marshaller = jaxbContext.createMarshaller();
		this.marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		this.unmarshaller = jaxbContext.createUnmarshaller();
		if (!isFileAlreadyExists) {
			try {
				tmp = load();
			} catch (JAXBException e) {
				log.warn("Unmarshalling failed. It seems like file {} is empty.", file.getName());
				tmp = new HashSet<>();
			}
		}
		else
			tmp = new HashSet<>();
		this.users = tmp;
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
