package com.bank.transactions.request;

import java.util.Objects;

import javax.validation.constraints.NotEmpty;

import com.bank.framework.domain.AbstractModelBean;
import com.bank.framework.domain.Channel;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = TransactionStatusWebRequest.TransactionStatusWebRequestBuilder.class)
public class TransactionStatusWebRequest extends AbstractModelBean {

	@NotEmpty
	private final String reference;
	private final Channel channel;

	private TransactionStatusWebRequest(TransactionStatusWebRequestBuilder builder) {
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
		if (!(obj instanceof TransactionStatusWebRequest)) {
			return false;
		}
		TransactionStatusWebRequest other = (TransactionStatusWebRequest) obj;
		return channel == other.channel && Objects.equals(reference, other.reference);
	}

	public TransactionStatusWebRequestBuilder cloneBuilder() {
		return new TransactionStatusWebRequestBuilder(reference, channel);
	}

	public static TransactionStatusWebRequestBuilder builder() {
		return new TransactionStatusWebRequestBuilder();
	}

	public static final class TransactionStatusWebRequestBuilder {
		private String reference;
		private Channel channel;

		private TransactionStatusWebRequestBuilder() {
		}
		
		public TransactionStatusWebRequestBuilder(String reference, Channel channel) {
			super();
			this.reference = reference;
			this.channel = channel;
		}

		public TransactionStatusWebRequestBuilder withReference(String reference) {
			this.reference = reference;
			return this;
		}

		public TransactionStatusWebRequestBuilder withChannel(Channel channel) {
			this.channel = channel;
			return this;
		}

		public TransactionStatusWebRequest build() {
			return new TransactionStatusWebRequest(this);
		}
	}

}
