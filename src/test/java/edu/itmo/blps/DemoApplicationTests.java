package edu.itmo.blps;

import edu.itmo.blps.model.company.Company;
import edu.itmo.blps.model.company.CompanyRepository;
import edu.itmo.blps.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = DemoApplication.class)
@DataJpaTest
@RequiredArgsConstructor
public class DemoApplicationTests {

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	private UserRepository userRepository;



	/* @Test
	@Transactional(value = "bitronixTransactionManager")
	@Rollback
	void addDeviceInCartTest() {
		Account account = new Account("customer", "user1000", "kokl");
		accountService.signup(account);
		Company company = new Company("company", "aaaaaa");
		company = companyRepository.save(company);
		Device device = new Device(null, "transistor", "sslkhfsd", "dsfsjkldhlf", company, 100);
		device = deviceService.saveDevice(device);
		Cart cartFromDb = cartService.putDeviceInCart(device, account.getName());
		Assertions.assertEquals(cartFromDb.getDevices().toArray()[0], device);
	}

	@Test
	@Transactional(value = "bitronixTransactionManager")
	@Rollback
	void removeDeviceFromCartTest() {
		Account account = new Account("customer", "user1000", "kokl");
		accountService.signup(account);

		Company company = new Company("company", "aaaaaa");
		company = companyRepository.save(company);

		Device device1 = new Device(null, "transistor", "sslkhfsd", "dsfsjkldhlf", company, 100);
		device1 = deviceService.saveDevice(device1);

		Device device2 = new Device(null, "resistor", "joly", "jjjjjjjj", company, 99);
		device2 = deviceService.saveDevice(device2);

		Cart cartFromDb = cartService.putDeviceInCart(device1, account.getName());
		cartFromDb.addDevice(device2);
		cartFromDb = cartService.saveCart(cartFromDb);

		cartFromDb = cartService.removeDeviceFromCart(device2, account.getName());

		System.out.println(cartFromDb);
		Assertions.assertEquals(cartFromDb.getDevices().size(), 1);
	}*/

	@Test
	@Transactional(transactionManager = "bitronixTransactionManager", rollbackFor = {IllegalStateException.class, Exception.class})
//	@Rollback
	public void addDeviceInCartTest() {
		Company company = new Company("sony", "pass", "sony");
		userRepository.save(company);
		throw new IllegalStateException();
//		Assertions.assertTrue(companyRepository.existsByName(company.getName()));

	}

	@Test
	public void encoderTest(){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("c1"));
		System.out.println(encoder.encode("u2"));
		System.out.println(encoder.encode("u3"));
		System.out.println(encoder.matches("123","$2a$10$9ZQXIrtI9q6gw.G9stoDI.YF/U/D97TAt0BC2nzo6Wz5LGfDMAQJe"));
	}

//	@Test
//	public void valueTest(@Value("#{${app.jwt.expiration:5} * 10}") Integer tenNums,
//						  @Value("${app.jwt.expiration:5}") Integer num){
//		Assertions.assertEquals(num, tenNums / 10);
//	}
//
//	@Test
//	public void valueStringTest(@Value("${app.jwt.expiration:lola}") String name){
//		System.out.println(name);
//	}
}
