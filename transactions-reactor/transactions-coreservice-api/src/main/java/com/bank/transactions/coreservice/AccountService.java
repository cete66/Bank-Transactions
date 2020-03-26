package com.bank.transactions.coreservice;

import java.math.BigDecimal;

public interface AccountService {

	Integer checkValidTransaction(final String account_iban, final BigDecimal amount);
	Integer confirmTransaction(final String account_iban, final BigDecimal amount);
}
