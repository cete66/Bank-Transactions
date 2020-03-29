package com.bank.transactions.coreservice;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.transactions.coreservice.repository.AccountRepository;

@Service("accountServiceImpl")
@Transactional
public class AccountServiceImpl implements AccountService {

	private final AccountRepository accountRepository;
	
	@Autowired
	public AccountServiceImpl(final AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public boolean confirmTransaction(String account_iban, BigDecimal amount) {
		return accountRepository.confirmTransaction(account_iban, amount) == 1;
	}

}
