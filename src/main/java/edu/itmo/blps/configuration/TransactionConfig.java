package edu.itmo.blps.configuration;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "edu.itmo.blps.model", transactionManagerRef = "bitronixTransactionManager")
public class TransactionConfig {

	@Bean(name = "bitronixTransactionManager")
	public PlatformTransactionManager bitronixTransactionManager(){
		JtaTransactionManager transactionManager = new JtaTransactionManager();
		BitronixTransactionManager bitronixTransactionManager = TransactionManagerServices.getTransactionManager();
		transactionManager.setTransactionManager(bitronixTransactionManager);
		transactionManager.setUserTransaction(bitronixTransactionManager);
		return transactionManager;
	}
}