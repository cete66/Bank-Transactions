package com.bank.transactions.repository;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bank.transactions.repository.entities.TransactionEntity;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {H2JpaConfig.class})
@DataJpaTest
public class TransactionRepositoryIT{

	private static final String IBAN = "ES999999999999999999";
	private static final BigDecimal AMOUNT = BigDecimal.ONE;
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Test
	public void givenValidRepositoryShouldPersistValidEntity() {
		
		TransactionEntity entity = TransactionEntity.builder().withAccount_iban(IBAN).withAmount(AMOUNT).build();
		
		assertNotNull(transactionRepository.save(entity).getId());
	}
	
}
