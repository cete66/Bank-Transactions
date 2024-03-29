package com.bank.transactions.coreservice.repository.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;

import com.bank.framework.domain.test.utils.AbstractModelBeanTest;

public class TransactionEntityTest extends AbstractModelBeanTest<TransactionEntity>{

	private static final String ACCOUNT_IBAN = "ES99999999999999";
	private static final String DESC = "desc";
	private static final String REF = "ref";
	private static final String ACCOUNT_IBAN_B = "ES88888888888888";
	private static final BigDecimal AMOUNT = BigDecimal.ONE;
	private static final LocalDateTime DATE = LocalDateTime.now();
	private static final BigDecimal FEE = BigDecimal.ZERO;
	private static final Integer id = 1;
	private static final Integer idB = 2;
	
	private final TransactionEntity.Builder builder = TransactionEntity.builder()
														.withAccount_iban(ACCOUNT_IBAN)
														.withAmount(AMOUNT)
														.withDate(DATE)
														.withDescription(DESC)
														.withFee(FEE)
														.withReference(REF)
														.withId(id);
	
	@Override
	@BeforeEach
	public void initEntities() {
		entityA1 = builder.build();
		entityA2 = entityA1.cloneBuilder().build();
		entityB = entityA1.cloneBuilder().withAccount_iban(ACCOUNT_IBAN_B).withId(idB).build();
	}

}
