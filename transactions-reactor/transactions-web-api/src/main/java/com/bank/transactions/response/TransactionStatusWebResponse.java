package com.bank.transactions.response;

import java.math.BigDecimal;
import java.util.Objects;

import com.bank.framework.domain.AbstractModelBean;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = TransactionStatusWebResponse.TransactionStatusWebResponseBuilder.class)
@JsonInclude(value = Include.NON_NULL)
public class TransactionStatusWebResponse extends AbstractModelBean {

	private final String reference;
	private final BigDecimal amount;
	private final BigDecimal fee;
	private final String status;

	private TransactionStatusWebResponse(TransactionStatusWebResponseBuilder builder) {
		this.reference = builder.reference;
		this.amount = builder.amount;
		this.fee = builder.fee;
		this.status = builder.status;
	}

	public String getReference() {
		return reference;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public String getStatus() {
		return status;
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
				&& Objects.equals(reference, other.reference) && Objects.equals(status, other.status);
	}
	
	public TransactionStatusWebResponseBuilder cloneBuilder() {
		return new TransactionStatusWebResponseBuilder(reference, amount, fee, status);
	}

	public static TransactionStatusWebResponseBuilder builder() {
		return new TransactionStatusWebResponseBuilder();
	}

	public static final class TransactionStatusWebResponseBuilder {
		private String reference;
		private BigDecimal amount;
		private BigDecimal fee;
		private String status;

		private TransactionStatusWebResponseBuilder() {
		}
		
		public TransactionStatusWebResponseBuilder(String reference, BigDecimal amount, BigDecimal fee, String status) {
			super();
			this.reference = reference;
			this.amount = amount;
			this.fee = fee;
			this.status = status;
		}

		public TransactionStatusWebResponseBuilder withReference(String reference) {
			this.reference = reference;
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

		public TransactionStatusWebResponseBuilder withStatus(String status) {
			this.status = status;
			return this;
		}

		public TransactionStatusWebResponse build() {
			return new TransactionStatusWebResponse(this);
		}
	}

}
