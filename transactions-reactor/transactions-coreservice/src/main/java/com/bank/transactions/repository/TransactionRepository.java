package com.bank.transactions.repository;

import com.bank.framework.domain.SortOrder;
import com.bank.transactions.repository.entities.TransactionEntity;

public interface TransactionRepository {

	
	String persist(TransactionEntity entity);
	
	TransactionEntity searchFilterByAccountSortByAmount(String account, SortOrder order);
	
	TransactionEntity status(TransactionEntity entity);
	
}
