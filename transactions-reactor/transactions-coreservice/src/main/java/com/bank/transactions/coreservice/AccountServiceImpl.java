package com.bank.transactions.coreservice;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.transactions.coreservice.repository.AccountRepository;

@Service("accountServiceImpl")
public class AccountServiceImpl implements AccountService {

	private final AccountRepository accountRepository;
	
	@Autowired
	public AccountServiceImpl(final AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	
	@Override
	public Integer checkValidTransaction(String account_iban, BigDecimal amount) {
		return accountRepository.checkValidTransaction(account_iban, amount);
	}

}
