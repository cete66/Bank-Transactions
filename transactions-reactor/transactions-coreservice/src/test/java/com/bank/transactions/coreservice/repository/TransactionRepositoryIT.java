package com.bank.transactions.coreservice.repository;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.bank.transactions.coreservice.repository.TransactionRepository;
import com.bank.transactions.coreservice.repository.entities.TransactionEntity;

@DataJpaTest
public class TransactionRepositoryIT{

	private static final String IBAN = "ES999999999999999999";
	private static final String IBAN_B = "ES888888888888888888";
	private static final BigDecimal AMOUNT = BigDecimal.ONE;
	private static final String REFERENCE = "asoiru983";
	private static final String AMOUNT_PROPERTY_NAME = "amount";
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Test
	public void givenValidRepositoryShouldPersistValidEntity() {
		
		TransactionEntity entity = TransactionEntity.builder().withAccount_iban(IBAN).withAmount(AMOUNT).build();
		
		assertNotNull(transactionRepository.save(entity).getId());
	}
	
	@Test
	public void givenValidReferenceShouldSearchFilterByAccountSortByAmount() {
		
		TransactionEntity entity = TransactionEntity.builder().withAccount_iban(IBAN_B).withAmount(AMOUNT).withReference(REFERENCE).build();
		
		entity = transactionRepository.save(entity);
		
		TransactionEntity actual = transactionRepository.searchFilterByAccountSortByAmount(entity.getAccount_iban(), Sort.by(Direction.DESC, AMOUNT_PROPERTY_NAME));
		
		MatcherAssert.assertThat(actual, Matchers.is(entity));
	}
	
}
