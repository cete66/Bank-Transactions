package com.bank.transactions.coreservice.repository.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import com.bank.framework.domain.AbstractModelBean;

@Entity(name = "Transaction")
public class TransactionEntity extends AbstractModelBean{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String reference;
	private String account_iban;
	private LocalDateTime date;
	private BigDecimal amount;
	private BigDecimal fee;
	private String description;
	private String status;
	private String channel;
	
	public TransactionEntity() {
		
	}
	
	public TransactionEntity(Integer id, String reference, String account_iban, LocalDateTime date, BigDecimal amount,
			BigDecimal fee, String description, String status, String channel) {
		this.id = id;
		this.reference = reference;
		this.account_iban = account_iban;
		this.date = date;
		this.amount = amount;
		this.fee = fee;
		this.description = description;
		this.status = status;
		this.channel = channel;
	}

	private TransactionEntity(Builder builder) {
		this.reference = builder.reference;
		this.account_iban = builder.account_iban;
		this.date = builder.date;
		this.amount = builder.amount;
		this.fee = builder.fee;
		this.description = builder.description;
		this.status = builder.status;
		this.channel = builder.channel;
		this.id = builder.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public void setAccount_iban(String account_iban) {
		this.account_iban = account_iban;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getReference() {
		return reference;
	}

	public String getAccount_iban() {
		return account_iban;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public String getDescription() {
		return description;
	}

	public String getStatus() {
		return status;
	}
	
	public String getChannel() {
		return channel;
	}
	
	public Integer getId() {
		return id;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(account_iban, amount, channel, date, description, fee, id, reference, status);
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
				&& Objects.equals(channel, other.channel) && Objects.equals(date, other.date)
				&& Objects.equals(description, other.description) && Objects.equals(fee, other.fee)
				&& Objects.equals(id, other.id) && Objects.equals(reference, other.reference)
				&& Objects.equals(status, other.status);
	}

	public Builder cloneBuilder() {
		return new Builder(reference, account_iban, date, amount, fee, description, status, channel, id);
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
		private String status;
		private String channel;
		private Integer id;

		private Builder() {
		}
		
		public Builder(String reference, String account_iban, LocalDateTime date, BigDecimal amount, BigDecimal fee,
				String description, String status, String channel, Integer id) {
			super();
			this.reference = reference;
			this.account_iban = account_iban;
			this.date = date;
			this.amount = amount;
			this.fee = fee;
			this.description = description;
			this.status = status;
			this.channel = channel;
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

		public Builder withStatus(String status) {
			this.status = status;
			return this;
		}
		
		public Builder withChannel(String channel) {
			this.channel = channel;
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
