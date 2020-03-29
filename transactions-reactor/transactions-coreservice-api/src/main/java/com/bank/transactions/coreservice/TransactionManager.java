package com.bank.transactions.coreservice;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;

import com.bank.framework.domain.SortOrder;
import com.bank.framework.domain.Status;
import com.bank.transactions.request.TransactionStatusWebRequest;
import com.bank.transactions.request.TransactionWebRequest;
import com.bank.transactions.response.TransactionWebResponse;

public interface TransactionManager {

	/**
	 * This method only inserts a register in the TRANSACTION table. 
	 * It does not actually updates account's balance, as that operation should be performed by the account's microservice.
	 * @param request
	 * @return a {@link TransactionWebResponse} with <code>status</code> {@link Status#PENDING}
	 */
	TransactionWebResponse create(TransactionWebRequest request);
	
	TransactionWebResponse status(TransactionStatusWebRequest statusRequest);
	
	List<TransactionWebResponse> search(final String iban, final String sortOrder);
}
