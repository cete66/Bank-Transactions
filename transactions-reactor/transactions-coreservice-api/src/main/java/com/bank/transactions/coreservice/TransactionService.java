package com.bank.transactions.coreservice;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;

import com.bank.transactions.coreservice.domain.TransactionRequest;
import com.bank.transactions.coreservice.domain.TransactionResponse;
import com.bank.transactions.coreservice.domain.TransactionStatusRequest;

public interface TransactionService {

	TransactionResponse create(TransactionRequest request);
	
	TransactionResponse status(TransactionStatusRequest statusRequest);
	
	TransactionResponse search(final String iban, final String sortOrder);
}
