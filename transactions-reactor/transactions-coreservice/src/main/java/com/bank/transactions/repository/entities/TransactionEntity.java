package com.bank.transactions.repository.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import com.bank.framework.domain.AbstractModelBean;

public class TransactionEntity extends AbstractModelBean{

	private final String reference;
	private final String account_iban;
	private final LocalDateTime date;
	private final BigDecimal amount;
	private final BigDecimal fee;
	private final String description;
	private final String status;
	private final String channel;

	private TransactionEntity(Builder builder) {
		this.reference = builder.reference;
		this.account_iban = builder.account_iban;
		this.date = builder.date;
		this.amount = builder.amount;
		this.fee = builder.fee;
		this.description = builder.description;
		this.status = builder.status;
		this.channel = builder.channel;
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
	
	

	@Override
	public int hashCode() {
		return Objects.hash(account_iban, amount, channel, date, description, fee, reference, status);
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
				&& Objects.equals(reference, other.reference) && Objects.equals(status, other.status);
	}

	public Builder cloneBuilder() {
		return new Builder(reference, account_iban, date, amount, fee, description, status, channel);
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

		private Builder() {
		}
		
		public Builder(String reference, String account_iban, LocalDateTime date, BigDecimal amount, BigDecimal fee,
				String description, String status, String channel) {
			super();
			this.reference = reference;
			this.account_iban = account_iban;
			this.date = date;
			this.amount = amount;
			this.fee = fee;
			this.description = description;
			this.status = status;
			this.channel = channel;
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

		public TransactionEntity build() {
			return new TransactionEntity(this);
		}
	}

}
