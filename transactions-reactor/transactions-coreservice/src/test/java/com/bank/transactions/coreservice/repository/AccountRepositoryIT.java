package com.bank.transactions.coreservice.repository;

import java.math.BigDecimal;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class AccountRepositoryIT {

	private static final String IBAN = "ES999999999999999999";
	private static final BigDecimal VALID_AMOUNT = BigDecimal.ONE;
	@Autowired
	private AccountRepository accountRepository;
	
	
	@Test
	public void givenValidTransactionAmountShouldReturn1() {
		MatcherAssert.assertThat(this.accountRepository.checkValidTransaction(IBAN, VALID_AMOUNT), Matchers.is(Integer.valueOf(1)));
	}
	
}
