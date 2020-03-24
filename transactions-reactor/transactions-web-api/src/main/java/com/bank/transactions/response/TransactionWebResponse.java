package com.bank.transactions.response;

import java.math.BigDecimal;
import java.util.Objects;

import com.bank.framework.domain.AbstractModelBean;
import com.bank.framework.domain.Status;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = TransactionWebResponse.TransactionWebResponseBuilder.class)
public class TransactionWebResponse extends AbstractModelBean {

	private final String reference;
	private final Status status;
	private final BigDecimal amount;
	private final BigDecimal fee;

	private TransactionWebResponse(TransactionWebResponseBuilder builder) {
		this.reference = builder.reference;
		this.status = builder.status;
		this.amount = builder.amount;
		this.fee = builder.fee;
	}

	public String getReference() {
		return reference;
	}

	public Status getStatus() {
		return status;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public BigDecimal getFee() {
		return fee;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(amount, fee, reference, status);
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
		return Objects.equals(amount, other.amount) && Objects.equals(fee, other.fee)
				&& Objects.equals(reference, other.reference) && status == other.status;
	}

	public TransactionWebResponseBuilder cloneBuilder() {
		return new TransactionWebResponseBuilder(reference, status, amount, fee);
	}

	public static TransactionWebResponseBuilder builder() {
		return new TransactionWebResponseBuilder();
	}

	public static final class TransactionWebResponseBuilder {
		private String reference;
		private Status status;
		private BigDecimal amount;
		private BigDecimal fee;

		private TransactionWebResponseBuilder() {
		}

		public TransactionWebResponseBuilder(String reference, Status status, BigDecimal amount, BigDecimal fee) {
			this.reference = reference;
			this.status = status;
			this.amount = amount;
			this.fee = fee;
		}

		public TransactionWebResponseBuilder withReference(String reference) {
			this.reference = reference;
			return this;
		}

		public TransactionWebResponseBuilder withStatus(Status status) {
			this.status = status;
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

		public TransactionWebResponse build() {
			return new TransactionWebResponse(this);
		}
	}

}
