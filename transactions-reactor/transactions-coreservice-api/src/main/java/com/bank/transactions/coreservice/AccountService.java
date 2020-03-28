package com.bank.transactions.coreservice;

import java.math.BigDecimal;


public interface AccountService {

	boolean confirmTransaction(final String account_iban, final BigDecimal amount);
}
