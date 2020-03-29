package com.bank.framework.persistence.exceptions;

import org.springframework.beans.factory.annotation.Value;

public class TransactionNotAllowedException extends RuntimeException{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TransactionNotAllowedException(
			@Value("${com.bank.transactions.coreservice.default.transaction.invalid}") 
			final String message) {
		super(message);
	}
}
