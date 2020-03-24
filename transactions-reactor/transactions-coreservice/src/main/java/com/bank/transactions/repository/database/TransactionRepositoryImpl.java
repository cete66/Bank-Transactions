package com.bank.transactions.repository.database;

import org.springframework.stereotype.Repository;

import com.bank.framework.domain.SortOrder;
import com.bank.transactions.repository.TransactionRepository;
import com.bank.transactions.repository.entities.TransactionEntity;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository{

	//TODO ROBIN
	@Override
	public String persist(TransactionEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionEntity searchFilterByAccountSortByAmount(String account, SortOrder order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionEntity status(TransactionEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
