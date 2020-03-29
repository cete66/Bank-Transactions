package com.bank.transactions.coreservice.repository.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import com.bank.framework.domain.AbstractModelBean;

@Entity(name = "Transaction")
public class TransactionEntity extends AbstractModelBean{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(name = "REFERENCE", nullable = false, unique =  true, updatable = false)
	private String reference;
	@Column
	private String account_iban;
	@Column(name = "DATE", updatable = false, nullable = false)
	private LocalDateTime date;
	@Column
	private BigDecimal amount;
	@Column
	private BigDecimal fee;
	@Column
	private String description;
	
	public TransactionEntity() {
		
	}
	
	public TransactionEntity(Integer id, String reference, String account_iban, LocalDateTime date, BigDecimal amount,
			BigDecimal fee, String description) {
		this.id = id;
		this.reference = reference;
		this.account_iban = account_iban;
		this.date = date;
		this.amount = amount;
		this.fee = fee;
		this.description = description;
	}

	private TransactionEntity(Builder builder) {
		this.reference = builder.reference;
		this.account_iban = builder.account_iban;
		this.date = builder.date;
		this.amount = builder.amount;
		this.fee = builder.fee;
		this.description = builder.description;
		this.id = builder.id;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getAccount_iban() {
		return account_iban;
	}

	public void setAccount_iban(String account_iban) {
		this.account_iban = account_iban;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(account_iban, amount, date, description, fee, id, reference);
	}
	
	@PrePersist
	private void assignDefaultValues() {
		if (this.reference == null || "".equals(this.reference.trim())){
			this.reference = UUID.randomUUID().toString();
		}
		if (this.date==null) {
			this.date = LocalDateTime.now();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof TransactionEntity)) {
			return false;
		}
		TransactionEntity other = (TransactionEntity) obj;
		return Objects.equals(account_iban, other.account_iban) && Objects.equals(amount, other.amount)
				&& Objects.equals(date, other.date) && Objects.equals(description, other.description) 
				&& Objects.equals(fee, other.fee) && Objects.equals(id, other.id) 
				&& Objects.equals(reference, other.reference);
	}

	public Builder cloneBuilder() {
		return new Builder(reference, account_iban, date, amount, fee, description, id);
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private String reference;
		private String account_iban;
		private LocalDateTime date;
		private BigDecimal amount;
		private BigDecimal fee;
		private String description;
		private Integer id;

		private Builder() {
		}
		
		public Builder(String reference, String account_iban, LocalDateTime date, BigDecimal amount, BigDecimal fee,
				String description, Integer id) {
			super();
			this.reference = reference;
			this.account_iban = account_iban;
			this.date = date;
			this.amount = amount;
			this.fee = fee;
			this.description = description;
			this.id = id;
		}

		public Builder withReference(String reference) {
			this.reference = reference;
			return this;
		}

		public Builder withAccount_iban(String account_iban) {
			this.account_iban = account_iban;
			return this;
		}

		public Builder withDate(LocalDateTime date) {
			this.date = date;
			return this;
		}

		public Builder withAmount(BigDecimal amount) {
			this.amount = amount;
			return this;
		}

		public Builder withFee(BigDecimal fee) {
			this.fee = fee;
			return this;
		}

		public Builder withDescription(String description) {
			this.description = description;
			return this;
		}
		
		public Builder withId(Integer id) {
			this.id = id;
			return this;
		}

		public TransactionEntity build() {
			return new TransactionEntity(this);
		}
	}

}
