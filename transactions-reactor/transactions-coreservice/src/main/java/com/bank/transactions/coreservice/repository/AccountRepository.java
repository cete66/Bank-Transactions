package com.bank.transactions.coreservice.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bank.transactions.coreservice.repository.entities.TransactionEntity;

@Repository
public interface AccountRepository extends JpaRepository<TransactionEntity, Integer> {

	@Query(" SELECT 1 FROM Account a WHERE a.iban = :iban AND a.balance - :amount > -1 ")
	Integer checkValidTransaction(@Param("iban") final String account_iban, @Param("amount") final BigDecimal amount);
	@Query(" UPDATE Account a SET a.balance = a.balance - :amount WHERE a.iban = :iban AND a.balance - :amount > -1 ")
	Integer confirmTransaction(@Param("iban") final String account_iban, @Param("amount") final BigDecimal amount);
}
