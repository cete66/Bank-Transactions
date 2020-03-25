package com.bank.transactions.coreservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.bank.transactions.coreservice.repository.entities.TransactionEntity;

@SpringBootConfiguration
@EnableJpaRepositories
@PropertySource("application.properties")
@EnableTransactionManagement
@EntityScan(basePackageClasses=TransactionEntity.class)
public class H2JpaConfig {

	@Autowired
    private Environment env;
}
