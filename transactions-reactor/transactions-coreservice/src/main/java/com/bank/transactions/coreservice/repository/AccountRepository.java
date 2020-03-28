package com.bank.transactions.coreservice.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bank.transactions.coreservice.repository.entities.TransactionEntity;

/**
 * This would be a provider of another microservice in a real situation. Instead of a repository.
 * @author Robin
 *
 */
@Repository
public interface AccountRepository extends JpaRepository<TransactionEntity, Integer> {

	@Modifying
	@Query(" UPDATE Account a SET a.balance = a.balance + :amount WHERE a.iban = :iban AND a.balance + :amount > -1 ")
	Integer confirmTransaction(@Param("iban") final String account_iban, @Param("amount") final BigDecimal amount);
}
