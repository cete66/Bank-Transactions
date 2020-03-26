package com.bank.transactions.coreservice.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bank.framework.domain.SortOrder;
import com.bank.transactions.coreservice.repository.entities.TransactionEntity;

/**
 * 
 * CREATE TABLE TRANSACTION (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  reference VARCHAR(255) UNIQUE,
  account_iban VARCHAR(34) NOT NULL,
  date TIMESTAMP,
  amount DECIMAL NOT NULL,
  fee DECIMAL,
  description VARCHAR(255),
  status VARCHAR(255),
  channel VARCHAR(255)
);
 * 
 * @author robin
 *
 */
@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {

	@Query(" SELECT t FROM Transaction t WHERE t.account_iban = :iban ")
	TransactionEntity searchFilterByAccountSortByAmount(@Param("iban") final String account, final Sort sort);
	
	@Query(" SELECT t FROM Transaction t WHERE t.reference = :reference AND ( :channel is null OR :channel = t.channel ) ")
	TransactionEntity status(@Param("channel") final String channel, @Param("reference") final String reference);
	
}
