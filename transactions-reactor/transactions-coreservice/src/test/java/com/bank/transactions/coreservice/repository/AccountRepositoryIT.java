package com.bank.transactions.coreservice.repository;

import java.math.BigDecimal;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class AccountRepositoryIT {

	private static final String VALID_IBAN = "ES999999999999999999";
	private static final String INVALID_IBAN = "ES888888888888888888";
	private static final BigDecimal VALID_AMOUNT = BigDecimal.ONE;
	private static final BigDecimal INVALID_AMOUNT = BigDecimal.valueOf(999999L*-1);
	@Autowired
	private AccountRepository accountRepository;
	
	
	@Test
	public void givenValidTransactionAmountAndValidIbanConfirmTransactionShouldReturn1() {
		MatcherAssert.assertThat(this.accountRepository.confirmTransaction(VALID_IBAN, VALID_AMOUNT), Matchers.is(Integer.valueOf(1)));
	}
	
	@Test
	public void givenUnvalidTransactionAmountAndValidIbanConfirmTransactionShouldReturn0() {
		MatcherAssert.assertThat(this.accountRepository.confirmTransaction(VALID_IBAN, INVALID_AMOUNT), Matchers.is(Integer.valueOf(0)));
	}
	
	@Test
	public void givenValidTransactionAmountAndInvalidIbanConfirmTransactionShouldReturn0() {
		MatcherAssert.assertThat(this.accountRepository.confirmTransaction(INVALID_IBAN, VALID_AMOUNT), Matchers.is(Integer.valueOf(0)));
	}
	
	@Test
	public void givenUnvalidTransactionAmountAndInvalidIbanConfirmTransactionShouldReturn0() {
		MatcherAssert.assertThat(this.accountRepository.confirmTransaction(INVALID_IBAN, INVALID_AMOUNT), Matchers.is(Integer.valueOf(0)));
	}
	
}
