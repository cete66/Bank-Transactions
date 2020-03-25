package com.bank.transactions.response;

import java.math.BigDecimal;
import java.util.Objects;

import com.bank.framework.domain.AbstractModelBean;
import com.bank.framework.domain.Channel;
import com.bank.framework.domain.Status;
import com.bank.transactions.response.TransactionWebResponse.TransactionWebResponseBuilder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = TransactionStatusWebResponse.TransactionStatusWebResponseBuilder.class)
public class TransactionStatusWebResponse extends AbstractModelBean {

	private final String reference;
	private final Status status;
	private final BigDecimal amount;
	private final BigDecimal fee;

	private TransactionStatusWebResponse(TransactionStatusWebResponseBuilder builder) {
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
		if (!(obj instanceof TransactionStatusWebResponse)) {
			return false;
		}
		TransactionStatusWebResponse other = (TransactionStatusWebResponse) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(fee, other.fee)
				&& Objects.equals(reference, other.reference) && status == other.status;
	}

	public TransactionStatusWebResponseBuilder cloneBuilder() {
		return new TransactionStatusWebResponseBuilder(reference, status, amount, fee);
	}

	public static TransactionStatusWebResponseBuilder builder() {
		return new TransactionStatusWebResponseBuilder();
	}

	public static final class TransactionStatusWebResponseBuilder {
		private String reference;
		private Status status;
		private BigDecimal amount;
		private BigDecimal fee;

		private TransactionStatusWebResponseBuilder() {
		}

		public TransactionStatusWebResponseBuilder(String reference, Status status, BigDecimal amount, BigDecimal fee) {
			this.reference = reference;
			this.status = status;
			this.amount = amount;
			this.fee = fee;
		}

		public TransactionStatusWebResponseBuilder withReference(String reference) {
			this.reference = reference;
			return this;
		}

		public TransactionStatusWebResponseBuilder withStatus(Status status) {
			this.status = status;
			return this;
		}

		public TransactionStatusWebResponseBuilder withAmount(BigDecimal amount) {
			this.amount = amount;
			return this;
		}

		public TransactionStatusWebResponseBuilder withFee(BigDecimal fee) {
			this.fee = fee;
			return this;
		}
		
		public TransactionStatusWebResponse build() {
			return new TransactionStatusWebResponse(this);
		}
	}

}
