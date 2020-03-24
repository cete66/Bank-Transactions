package com.bank.transactions.coreservice.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;

import com.bank.framework.domain.AbstractModelBean;
import com.bank.framework.domain.Status;

public class TransactionRequest extends AbstractModelBean {

	private final String reference;
	@NotEmpty
	private final String account_iban;
	private final LocalDateTime date;
	@NotEmpty
	private final BigDecimal amount;
	private final BigDecimal fee;
	private final String description;
	private final Status status;

	public TransactionRequest(TransactionRequestBuilder builder) {
		this.reference = builder.reference;
		this.account_iban = builder.account_iban;
		this.date = builder.date;
		this.amount = builder.amount;
		this.fee = builder.fee;
		this.description = builder.description;
		this.status = builder.status;
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
	
	@Override
	public int hashCode() {
		return Objects.hash(account_iban, amount, date, description, fee, reference, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof TransactionRequest)) {
			return false;
		}
		TransactionRequest other = (TransactionRequest) obj;
		return Objects.equals(account_iban, other.account_iban) && Objects.equals(amount, other.amount)
				&& Objects.equals(date, other.date) && Objects.equals(description, other.description)
				&& Objects.equals(fee, other.fee) && Objects.equals(reference, other.reference)
				&& status == other.status;
	}

	public TransactionRequestBuilder cloneBuilder() {
		return new TransactionRequestBuilder(reference, account_iban, date, amount, fee, description);
	}

	public static TransactionRequestBuilder builder() {
		return new TransactionRequestBuilder();
	}

	public static final class TransactionRequestBuilder {
		private String reference;
		private String account_iban;
		private LocalDateTime date;
		private BigDecimal amount;
		private BigDecimal fee;
		private String description;
		private Status status;

		private TransactionRequestBuilder() {
		}
		
		private TransactionRequestBuilder(String reference, String account_iban, LocalDateTime date, BigDecimal amount, BigDecimal fee,
				String description) {
			this.reference = reference;
			this.account_iban = account_iban;
			this.date = date;
			this.amount = amount;
			this.fee = fee;
			this.description = description;
		}

		public TransactionRequestBuilder withReference(String reference) {
			this.reference = reference;
			return this;
		}

		public TransactionRequestBuilder withAccount_iban(String account_iban) {
			this.account_iban = account_iban;
			return this;
		}

		public TransactionRequestBuilder withDate(LocalDateTime date) {
			this.date = date;
			return this;
		}

		public TransactionRequestBuilder withAmount(BigDecimal amount) {
			this.amount = amount;
			return this;
		}

		public TransactionRequestBuilder withFee(BigDecimal fee) {
			this.fee = fee;
			return this;
		}

		public TransactionRequestBuilder withDescription(String description) {
			this.description = description;
			return this;
		}
		
		public TransactionRequestBuilder withStatus(Status status) {
			this.status = status;
			return this;
		}

		public TransactionRequest build() {
			return new TransactionRequest(this);
		}
	}

}
