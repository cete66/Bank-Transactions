package com.bank.transactions.coreservice.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;

import com.bank.framework.domain.AbstractModelBean;
import com.bank.framework.domain.Channel;
import com.bank.framework.domain.Status;

public class TransactionForStatusRule extends AbstractModelBean {

	private final String reference;
	@NotEmpty
	private final String account_iban;
	private final LocalDateTime date;
	@NotEmpty
	private final BigDecimal amount;
	private final BigDecimal fee;
	private final String description;
	private final Status status;
	private final Channel channel;

	private TransactionForStatusRule(TransactionForStatusRuleBuilder builder) {
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
		if (!(obj instanceof TransactionForStatusRule)) {
			return false;
		}
		TransactionForStatusRule other = (TransactionForStatusRule) obj;
		return Objects.equals(account_iban, other.account_iban) && Objects.equals(amount, other.amount)
				&& channel == other.channel && Objects.equals(date, other.date)
				&& Objects.equals(description, other.description) && Objects.equals(fee, other.fee)
				&& Objects.equals(reference, other.reference) && status == other.status;
	}
	
	public TransactionForStatusRuleBuilder clonebuilder() {
		return new TransactionForStatusRuleBuilder(reference, account_iban, date, amount, fee, description, status, channel);
	}

	public static TransactionForStatusRuleBuilder builder() {
		return new TransactionForStatusRuleBuilder();
	}

	public static final class TransactionForStatusRuleBuilder {
		private String reference;
		private String account_iban;
		private LocalDateTime date;
		private BigDecimal amount;
		private BigDecimal fee;
		private String description;
		private Status status;
		private Channel channel;

		private TransactionForStatusRuleBuilder() {
		}
		
		public TransactionForStatusRuleBuilder(String reference, String account_iban, LocalDateTime date,
				BigDecimal amount, BigDecimal fee, String description, Status status, Channel channel) {
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

		public TransactionForStatusRuleBuilder withReference(String reference) {
			this.reference = reference;
			return this;
		}

		public TransactionForStatusRuleBuilder withAccount_iban(String account_iban) {
			this.account_iban = account_iban;
			return this;
		}

		public TransactionForStatusRuleBuilder withDate(LocalDateTime date) {
			this.date = date;
			return this;
		}

		public TransactionForStatusRuleBuilder withAmount(BigDecimal amount) {
			this.amount = amount;
			return this;
		}

		public TransactionForStatusRuleBuilder withFee(BigDecimal fee) {
			this.fee = fee;
			return this;
		}

		public TransactionForStatusRuleBuilder withDescription(String description) {
			this.description = description;
			return this;
		}

		public TransactionForStatusRuleBuilder withStatus(Status status) {
			this.status = status;
			return this;
		}

		public TransactionForStatusRuleBuilder withChannel(Channel channel) {
			this.channel = channel;
			return this;
		}

		public TransactionForStatusRule build() {
			return new TransactionForStatusRule(this);
		}
	}

}
