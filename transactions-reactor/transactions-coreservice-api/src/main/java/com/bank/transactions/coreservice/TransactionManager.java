package com.bank.transactions.coreservice;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;

import com.bank.transactions.request.TransactionStatusWebRequest;
import com.bank.transactions.request.TransactionWebRequest;
import com.bank.transactions.response.TransactionWebResponse;

public interface TransactionManager {

	TransactionWebResponse create(TransactionWebRequest request);
	
	TransactionWebResponse status(TransactionStatusWebRequest statusRequest);
	
	TransactionWebResponse search(final String iban, final Sort sort);
}
