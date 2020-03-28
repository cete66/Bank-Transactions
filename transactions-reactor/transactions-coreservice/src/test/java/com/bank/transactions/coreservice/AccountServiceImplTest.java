package com.bank.transactions.coreservice;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.bank.transactions.coreservice.repository.AccountRepository;

public class AccountServiceImplTest {

	private static final String IBAN = "ES999999999999999999";
	private static final BigDecimal VALID_AMOUNT = BigDecimal.ONE;
	@Mock
	private AccountRepository accountRepository;
	@InjectMocks
	private AccountServiceImpl service;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void whenRepositoryReturns1ShouldReturnTrue() {
		Mockito.doReturn(Integer.valueOf(1)).when(accountRepository)
		.confirmTransaction(ArgumentMatchers.eq(IBAN), ArgumentMatchers.eq(VALID_AMOUNT));
		
		Assertions.assertTrue(this.service.confirmTransaction(IBAN, VALID_AMOUNT));
	}
	
	@Test
	public void whenRepositoryNotReturns1ShouldReturnFalse() {
		Mockito.doReturn(Integer.valueOf(0)).when(accountRepository)
		.confirmTransaction(ArgumentMatchers.eq(IBAN), ArgumentMatchers.eq(VALID_AMOUNT));
		
		Assertions.assertFalse(this.service.confirmTransaction(IBAN, VALID_AMOUNT));
	}
}
