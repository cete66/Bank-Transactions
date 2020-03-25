package com.bank.transactions.coreservice.repository.entities;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.bank.framework.domain.AbstractModelBean;
import javax.annotation.Generated;

@Entity(name = "Account")
public class AccountEntity extends AbstractModelBean {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String iban;
	private BigDecimal balance;

	public AccountEntity() {
		
	}
	
	private AccountEntity(AccountEntityBuilder builder) {
		this.iban = builder.iban;
		this.balance = builder.balance;
	}

	public String getIban() {
		return iban;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	@Override
	public int hashCode() {
		return Objects.hash(balance, iban);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AccountEntity)) {
			return false;
		}
		AccountEntity other = (AccountEntity) obj;
		return Objects.equals(balance, other.balance) && Objects.equals(iban, other.iban);
	}
	
	public AccountEntityBuilder cloneBuilder() {
		return new AccountEntityBuilder(iban, balance);
	}

	public static AccountEntityBuilder builder() {
		return new AccountEntityBuilder();
	}

	public static final class AccountEntityBuilder {
		private String iban;
		private BigDecimal balance;

		private AccountEntityBuilder() {
		}
		
		public AccountEntityBuilder(String iban, BigDecimal balance) {
			super();
			this.iban = iban;
			this.balance = balance;
		}

		public AccountEntityBuilder withIban(String iban) {
			this.iban = iban;
			return this;
		}

		public AccountEntityBuilder withBalance(BigDecimal balance) {
			this.balance = balance;
			return this;
		}

		public AccountEntity build() {
			return new AccountEntity(this);
		}
	}

}
