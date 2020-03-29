package com.bank.transactions.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.bank.framework.domain.AbstractModelBean;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = TransactionWebRequest.TransactionWebRequestBuilder.class)
public class TransactionWebRequest extends AbstractModelBean {

	private final String reference;
	@NotEmpty
	private final String account_iban;
	private final LocalDateTime date;
	@NotNull
	private final BigDecimal amount;
	private final BigDecimal fee;
	private final String description;

	public TransactionWebRequest(TransactionWebRequestBuilder builder) {
		this.reference = builder.reference;
		this.account_iban = builder.account_iban;
		this.date = builder.date;
		this.amount = builder.amount;
		this.fee = builder.fee;
		this.description = builder.description;
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

	@Override
	public int hashCode() {
		return Objects.hash(account_iban, amount, date, description, fee, reference);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof TransactionWebRequest)) {
			return false;
		}
		TransactionWebRequest other = (TransactionWebRequest) obj;
		return Objects.equals(account_iban, other.account_iban) && Objects.equals(amount, other.amount)
				&& Objects.equals(date, other.date) && Objects.equals(description, other.description)
				&& Objects.equals(fee, other.fee) && Objects.equals(reference, other.reference);
	}
	
	public TransactionWebRequestBuilder cloneBuilder() {
		return new TransactionWebRequestBuilder(reference, account_iban, date, amount, fee, description);
	}

	public static TransactionWebRequestBuilder builder() {
		return new TransactionWebRequestBuilder();
	}

	public static final class TransactionWebRequestBuilder {
		private String reference;
		private String account_iban;
		private LocalDateTime date;
		private BigDecimal amount;
		private BigDecimal fee;
		private String description;

		private TransactionWebRequestBuilder() {
		}
		
		private TransactionWebRequestBuilder(String reference, String account_iban, LocalDateTime date, BigDecimal amount, BigDecimal fee,
				String description) {
			this.reference = reference;
			this.account_iban = account_iban;
			this.date = date;
			this.amount = amount;
			this.fee = fee;
			this.description = description;
		}

		public TransactionWebRequestBuilder withReference(String reference) {
			this.reference = reference;
			return this;
		}

		public TransactionWebRequestBuilder withAccount_iban(String account_iban) {
			this.account_iban = account_iban;
			return this;
		}

		public TransactionWebRequestBuilder withDate(LocalDateTime date) {
			this.date = date;
			return this;
		}

		public TransactionWebRequestBuilder withAmount(BigDecimal amount) {
			this.amount = amount;
			return this;
		}

		public TransactionWebRequestBuilder withFee(BigDecimal fee) {
			this.fee = fee;
			return this;
		}

		public TransactionWebRequestBuilder withDescription(String description) {
			this.description = description;
			return this;
		}

		public TransactionWebRequest build() {
			return new TransactionWebRequest(this);
		}
	}

}
