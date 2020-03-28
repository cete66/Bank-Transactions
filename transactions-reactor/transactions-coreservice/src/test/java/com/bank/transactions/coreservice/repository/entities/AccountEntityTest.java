package com.bank.transactions.coreservice.repository.entities;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;

import com.bank.framework.domain.test.utils.AbstractModelBeanTest;
import com.bank.transactions.coreservice.repository.entities.AccountEntity.AccountEntityBuilder;

public class AccountEntityTest extends AbstractModelBeanTest<AccountEntity>{

	private static final String IBAN = "ES999999999999999999";
	private static final String IBAN_B = "ES888888888888888888";
	private static final Integer ID = Integer.valueOf(1);
	private final AccountEntityBuilder builder = AccountEntity.builder()
																.withBalance(BigDecimal.TEN)
																.withIban(IBAN)
																.withId(ID);
	
	@Override
	@BeforeEach
	public void initEntities() {
		entityA1 = builder.build();
		entityA2 = entityA1.cloneBuilder().build();
		entityB = entityA1.cloneBuilder().withIban(IBAN_B).build();
	}

}
