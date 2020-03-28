package com.bank.transactions.coreservice.repository.entities;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.bank.framework.domain.AbstractModelBean;

@Entity(name = "Account")
public class AccountEntity extends AbstractModelBean {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column
	private String iban;
	@Column
	private BigDecimal balance;

	public AccountEntity() {
		
	}
	
	private AccountEntity(AccountEntityBuilder builder) {
		this.iban = builder.iban;
		this.balance = builder.balance;
		this.id = builder.id;
	}

	public String getIban() {
		return iban;
	}

	public BigDecimal getBalance() {
		return balance;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(balance, iban, id);
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
		return Objects.equals(balance, other.balance) && Objects.equals(iban, other.iban)
				&& Objects.equals(id, other.id);
	}

	public AccountEntityBuilder cloneBuilder() {
		return new AccountEntityBuilder(iban, balance, id);
	}

	public static AccountEntityBuilder builder() {
		return new AccountEntityBuilder();
	}

	public static final class AccountEntityBuilder {
		private String iban;
		private BigDecimal balance;
		private Integer id;

		private AccountEntityBuilder() {
		}
		
		public AccountEntityBuilder(String iban, BigDecimal balance, Integer id) {
			super();
			this.iban = iban;
			this.balance = balance;
			this.id = id;
		}

		public AccountEntityBuilder withIban(String iban) {
			this.iban = iban;
			return this;
		}

		public AccountEntityBuilder withBalance(BigDecimal balance) {
			this.balance = balance;
			return this;
		}
		
		public AccountEntityBuilder withId(Integer id) {
			this.id = id;
			return this;
		}

		public AccountEntity build() {
			return new AccountEntity(this);
		}
	}

}
