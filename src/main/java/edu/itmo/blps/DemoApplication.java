package edu.itmo.blps;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "edu.itmo.blps")
//@ComponentScan(basePackages = )
public class DemoApplication {

	private static ConfigurableApplicationContext applicationContext;

	public static void main(String[] args) {
		applicationContext = SpringApplication.run(DemoApplication.class, args);
	}

	public static ConfigurableApplicationContext getApplicationContext() {
		return applicationContext;
	}
}
