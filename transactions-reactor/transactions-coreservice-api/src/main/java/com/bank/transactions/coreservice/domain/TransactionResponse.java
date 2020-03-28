package com.bank.transactions.coreservice.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import com.bank.framework.domain.AbstractModelBean;
import com.bank.framework.domain.Channel;
import com.bank.framework.domain.Status;

public class TransactionResponse extends AbstractModelBean{

	private final String reference;
	private final String account_iban;
	private final LocalDateTime date;
	private final BigDecimal amount;
	private final BigDecimal fee;
	private final String description;
	private final Status status;
	private final Channel channel;
	
	public TransactionResponse(TransactionResponseBuilder builder) {
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

	public Status getStatus() {
		return status;
	}

	public Channel getChannel() {
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
		if (!(obj instanceof TransactionResponse)) {
			return false;
		}
		TransactionResponse other = (TransactionResponse) obj;
		return Objects.equals(account_iban, other.account_iban) && Objects.equals(amount, other.amount)
				&& channel == other.channel && Objects.equals(date, other.date)
				&& Objects.equals(description, other.description) && Objects.equals(fee, other.fee)
				&& Objects.equals(reference, other.reference) && status == other.status;
	}

	public TransactionResponseBuilder cloneBuilder() {
		return new TransactionResponseBuilder(reference, account_iban, date, amount, fee, description, status, channel);
	}

	public static TransactionResponseBuilder builder() {
		return new TransactionResponseBuilder();
	}

	public static final class TransactionResponseBuilder {
		private String reference;
		private String account_iban;
		private LocalDateTime date;
		private BigDecimal amount;
		private BigDecimal fee;
		private String description;
		private Status status;
		private Channel channel;

		private TransactionResponseBuilder() {
		}
		
		public TransactionResponseBuilder(String reference, String account_iban, LocalDateTime date, BigDecimal amount,
				BigDecimal fee, String description, Status status, Channel channel) {
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

		public TransactionResponseBuilder withReference(String reference) {
			this.reference = reference;
			return this;
		}

		public TransactionResponseBuilder withAccount_iban(String account_iban) {
			this.account_iban = account_iban;
			return this;
		}

		public TransactionResponseBuilder withDate(LocalDateTime date) {
			this.date = date;
			return this;
		}

		public TransactionResponseBuilder withAmount(BigDecimal amount) {
			this.amount = amount;
			return this;
		}

		public TransactionResponseBuilder withFee(BigDecimal fee) {
			this.fee = fee;
			return this;
		}

		public TransactionResponseBuilder withDescription(String description) {
			this.description = description;
			return this;
		}
		
		public TransactionResponseBuilder withStatus(Status status) {
			this.status = status;
			return this;
		}
		
		public TransactionResponseBuilder withChannel(Channel channel) {
			this.channel = channel;
			return this;
		}

		public TransactionResponse build() {
			return new TransactionResponse(this);
		}
	}
	
}
