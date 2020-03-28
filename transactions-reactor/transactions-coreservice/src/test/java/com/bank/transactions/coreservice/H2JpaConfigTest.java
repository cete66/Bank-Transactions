package com.bank.transactions.coreservice;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootTest
@TestPropertySource(locations = "/application.properties")
public class H2JpaConfigTest {
	
	@TestConfiguration
	static class Config {
		@Bean
		public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
			LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
			em.setDataSource(dataSource());
			em.setPackagesToScan(new String[] { "com.bank.transactions.coreservice.repository.entities" });

			JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
			em.setJpaVendorAdapter(vendorAdapter);
			em.setJpaProperties(additionalProperties());

			return em;
		}
		
		@Bean
		public DataSource dataSource(){
		    DriverManagerDataSource dataSource = new DriverManagerDataSource();
		    dataSource.setDriverClassName("org.h2.Driver");
		    dataSource.setUrl("jdbc:h2:mem:testdb");
		    dataSource.setUsername("sa");
		    dataSource.setPassword("sa");
		    return dataSource;
		}
		
		@Bean
		public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		    JpaTransactionManager transactionManager = new JpaTransactionManager();
		    transactionManager.setEntityManagerFactory(emf);
		 
		    return transactionManager;
		}
		 
		@Bean
		public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
		    return new PersistenceExceptionTranslationPostProcessor();
		}
		 
		Properties additionalProperties() {
		    Properties properties = new Properties();
		    properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		    properties.setProperty("hibernate.show_sql", "true");
		    properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		    properties.setProperty("hibernate.cache.use_second_level_cache", "false");
		    properties.setProperty("hibernate.cache.use_query_cache", "false");
		        
		    return properties;
		}
	}

	@Test
	public void loadContext() {

	}
}

