package com.bank.transactions.coreservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {

	@Query(" SELECT t FROM Transaction t WHERE t.account_iban = :iban ORDER BY t.amount ASC ")
	List<TransactionEntity> searchTransactionsFilterByIbanSortASCByAmount(@Param("iban") final String account);
	
	@Query(" SELECT t FROM Transaction t WHERE t.account_iban = :iban ORDER BY t.amount DESC ")
	List<TransactionEntity> searchTransactionsFilterByIbanSortDESCByAmount(@Param("iban") final String account);
	
	@Query(" SELECT t FROM Transaction t WHERE t.reference = :reference ")
	TransactionEntity checkDateForStatusRule(@Param("reference") final String reference);
	
}

