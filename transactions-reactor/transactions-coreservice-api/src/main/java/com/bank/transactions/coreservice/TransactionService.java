package com.bank.transactions.coreservice;

import java.util.List;

import com.bank.transactions.coreservice.domain.TransactionRequest;
import com.bank.transactions.coreservice.domain.TransactionResponse;
import com.bank.transactions.coreservice.domain.TransactionStatusRequest;

public interface TransactionService {

	TransactionResponse create(TransactionRequest request);
	
	TransactionResponse status(TransactionStatusRequest statusRequest);
	
	List<TransactionResponse> search(final String iban, final String sortOrder);
}
