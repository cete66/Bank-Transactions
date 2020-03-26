package com.bank.transactions.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;

import com.bank.framework.domain.AbstractModelBean;
import com.bank.framework.domain.Status;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javax.annotation.Generated;

@JsonDeserialize(builder = TransactionWebResponse.TransactionWebResponseBuilder.class)
public class TransactionWebResponse extends AbstractModelBean {

	private final String reference;
	private final String account_iban;
	private final LocalDateTime date;
	private final BigDecimal amount;
	private final BigDecimal fee;
	private final String description;
	private final String status;

	@Generated("SparkTools")
	private TransactionWebResponse(TransactionWebResponseBuilder builder) {
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

	public String getStatus() {
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
		if (!(obj instanceof TransactionWebResponse)) {
			return false;
		}
		TransactionWebResponse other = (TransactionWebResponse) obj;
		return Objects.equals(account_iban, other.account_iban) && Objects.equals(amount, other.amount)
				&& Objects.equals(date, other.date) && Objects.equals(description, other.description)
				&& Objects.equals(fee, other.fee) && Objects.equals(reference, other.reference)
				&& status == other.status;
	}

	public TransactionWebResponseBuilder cloneBuilder() {
		return new TransactionWebResponseBuilder(reference, account_iban, date, amount, fee, description, status);
	}

	/**
	 * Creates builder to build {@link TransactionWebResponse}.
	 * 
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static TransactionWebResponseBuilder builder() {
		return new TransactionWebResponseBuilder();
	}

	/**
	 * Builder to build {@link TransactionWebResponse}.
	 */
	@Generated("SparkTools")
	public static final class TransactionWebResponseBuilder {
		private String reference;
		private String account_iban;
		private LocalDateTime date;
		private BigDecimal amount;
		private BigDecimal fee;
		private String description;
		private String status;

		private TransactionWebResponseBuilder() {
		}

		public TransactionWebResponseBuilder(String reference, String account_iban, LocalDateTime date,
				BigDecimal amount, BigDecimal fee, String description, String status) {
			super();
			this.reference = reference;
			this.account_iban = account_iban;
			this.date = date;
			this.amount = amount;
			this.fee = fee;
			this.description = description;
			this.status = status;
		}

		public TransactionWebResponseBuilder withReference(String reference) {
			this.reference = reference;
			return this;
		}

		public TransactionWebResponseBuilder withAccount_iban(String account_iban) {
			this.account_iban = account_iban;
			return this;
		}

		public TransactionWebResponseBuilder withDate(LocalDateTime date) {
			this.date = date;
			return this;
		}

		public TransactionWebResponseBuilder withAmount(BigDecimal amount) {
			this.amount = amount;
			return this;
		}

		public TransactionWebResponseBuilder withFee(BigDecimal fee) {
			this.fee = fee;
			return this;
		}

		public TransactionWebResponseBuilder withDescription(String description) {
			this.description = description;
			return this;
		}

		public TransactionWebResponseBuilder withStatus(String status) {
			this.status = status;
			return this;
		}

		public TransactionWebResponse build() {
			return new TransactionWebResponse(this);
		}
	}

}
