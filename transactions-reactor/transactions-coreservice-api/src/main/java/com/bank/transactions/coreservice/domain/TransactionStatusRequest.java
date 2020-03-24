package com.bank.transactions.coreservice.domain;

import java.util.Objects;

import javax.validation.constraints.NotEmpty;

import com.bank.framework.domain.AbstractModelBean;
import com.bank.framework.domain.Channel;

public class TransactionStatusRequest extends AbstractModelBean{

	@NotEmpty
	private final String reference;
	private final Channel channel;

	private TransactionStatusRequest(TransactionStatusRequestBuilder builder) {
		this.reference = builder.reference;
		this.channel = builder.channel;
	}

	public String getReference() {
		return reference;
	}

	public Channel getChannel() {
		return channel;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(channel, reference);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof TransactionStatusRequest)) {
			return false;
		}
		TransactionStatusRequest other = (TransactionStatusRequest) obj;
		return channel == other.channel && Objects.equals(reference, other.reference);
	}

	public TransactionStatusRequestBuilder cloneBuilder() {
		return new TransactionStatusRequestBuilder(reference, channel);
	}

	public static TransactionStatusRequestBuilder builder() {
		return new TransactionStatusRequestBuilder();
	}

	public static final class TransactionStatusRequestBuilder {
		private String reference;
		private Channel channel;

		private TransactionStatusRequestBuilder() {
		}
		
		public TransactionStatusRequestBuilder(String reference, Channel channel) {
			super();
			this.reference = reference;
			this.channel = channel;
		}

		public TransactionStatusRequestBuilder withReference(String reference) {
			this.reference = reference;
			return this;
		}

		public TransactionStatusRequestBuilder withChannel(Channel channel) {
			this.channel = channel;
			return this;
		}

		public TransactionStatusRequest build() {
			return new TransactionStatusRequest(this);
		}
	}

}
