package com.bank.transactions.coreservice.repository;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.bank.transactions.coreservice.repository.entities.TransactionEntity;

@DataJpaTest
public class TransactionRepositoryIT{

	private static final String IBAN = "ES999999999999999999";
	private static final String IBAN_B = "ES888888888888888888";
	private static final String IBAN_C = "ES777777777777777777";
	private static final BigDecimal AMOUNT = BigDecimal.ONE;
	private static final String REFERENCE = "asoiru983";
	private static final String IBAN_D = "ES666666666666666666";
	@Autowired
	private TransactionRepository transactionRepository;
	private TransactionEntity entity = TransactionEntity.builder()
			.withAccount_iban(IBAN)
			.withAmount(AMOUNT)
			.withReference(UUID.randomUUID().toString())
			.build();
	
	@Test
	public void givenValidEntityShouldPersistCorrectly() {
		entity = transactionRepository.saveAndFlush(entity);
		assertNotNull(entity.getId());
	}
	
	@Test
	public void givenNullEntityIbanShouldThrowDataIntegrityViolationException() {
		final TransactionEntity entity = TransactionEntity.builder().withAmount(AMOUNT).build();
		Assertions.assertThrows(DataIntegrityViolationException.class, () -> 
		{transactionRepository.saveAndFlush(entity);});
	}
	
	@Test
	public void givenNullEntityAmountShouldThrowDataIntegrityViolationException() {
		final TransactionEntity entity = TransactionEntity.builder().withAccount_iban(IBAN).build();
		Assertions.assertThrows(DataIntegrityViolationException.class, () -> 
		{transactionRepository.saveAndFlush(entity);});
	}
	
	@Test
	public void givenValidReferenceShouldReturnEntity() {
		TransactionEntity entity = TransactionEntity.builder()
				.withAccount_iban(IBAN_D)
				.withAmount(AMOUNT)
				.withReference(UUID.randomUUID().toString())
				.build();
		transactionRepository.saveAndFlush(entity);
		
		Assertions.assertNotNull(transactionRepository.checkDateForStatusRule(entity.getReference()));
	}
	
	@Test
	public void givenInexistentReferenceShouldReturnNull() {
		Assertions.assertNull(transactionRepository.checkDateForStatusRule(REFERENCE));
	}
	
	@Test
	public void givenValidIbanShouldSearchFilterByIbanSortASCByAmount() {
		
		TransactionEntity entity = TransactionEntity.builder()
				.withAccount_iban(IBAN_B)
				.withAmount(AMOUNT.add(BigDecimal.TEN))
				.withReference(UUID.randomUUID().toString())
				.build();
		TransactionEntity entityB = entity.cloneBuilder()
				.withAmount(entity.getAmount().add(BigDecimal.TEN))
				.withReference(UUID.randomUUID().toString()).build();
		transactionRepository.saveAndFlush(entity);
		transactionRepository.saveAndFlush(entityB);

		List<TransactionEntity> actual = transactionRepository
				.searchTransactionsFilterByIbanSortASCByAmount(entity.getAccount_iban());
		MatcherAssert.assertThat(actual, Matchers.containsInRelativeOrder(entity, entityB));
	}
	
	@Test
	public void givenValidIbanShouldSearchFilterByIbanSortDESCByAmount() {
		
		TransactionEntity entity = TransactionEntity.builder()
				.withAccount_iban(IBAN_C)
				.withAmount(AMOUNT.add(BigDecimal.TEN))
				.withReference(UUID.randomUUID().toString())
				.build();
		TransactionEntity entityC = entity.cloneBuilder()
				.withAmount(entity.getAmount().add(BigDecimal.TEN))
				.withReference(UUID.randomUUID().toString()).build();
		transactionRepository.saveAndFlush(entity);
		transactionRepository.saveAndFlush(entityC);

		List<TransactionEntity> actual = transactionRepository
				.searchTransactionsFilterByIbanSortDESCByAmount(entity.getAccount_iban());
		MatcherAssert.assertThat(actual, Matchers.containsInRelativeOrder(entityC, entity));
	}
	
	@Test
	public void givenInexistentIbanSearchFilterByIbanSortDESCByAmountShouldReturnNull() {
		
		TransactionEntity entity = TransactionEntity.builder()
				.withAccount_iban(IBAN_C)
				.withAmount(AMOUNT.add(BigDecimal.TEN))
				.withReference(UUID.randomUUID().toString())
				.build();

		List<TransactionEntity> actual = transactionRepository
				.searchTransactionsFilterByIbanSortDESCByAmount(entity.getAccount_iban());
		Assertions.assertTrue(actual==null || actual.isEmpty());
	}
	
	@Test
	public void givenInexistentIbanSearchFilterByIbanSortASCByAmountShouldReturnNull() {
		
		TransactionEntity entity = TransactionEntity.builder()
				.withAccount_iban(IBAN_B)
				.withAmount(AMOUNT.add(BigDecimal.TEN))
				.withReference(UUID.randomUUID().toString())
				.build();

		List<TransactionEntity> actual = transactionRepository
				.searchTransactionsFilterByIbanSortDESCByAmount(entity.getAccount_iban());
		Assertions.assertTrue(actual==null || actual.isEmpty());
	}
}
