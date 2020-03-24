package com.bank.transactions.converters;

import org.springframework.stereotype.Component;

import com.bank.framework.converter.Converter;
import com.bank.transactions.coreservice.domain.TransactionStatusRequest;
import com.bank.transactions.request.TransactionStatusWebRequest;

@Component("transactionStatusWebRequestIntoTransactionStatusRequestConverter")
public class TransactionStatusWebRequestIntoTransactionStatusRequestConverter
		implements Converter<TransactionStatusWebRequest, TransactionStatusRequest> {

	@Override
	public TransactionStatusRequest convert(TransactionStatusWebRequest webRequest) {

		if (webRequest == null) {
			return null;
		}
		
		return TransactionStatusRequest.builder()
				.withChannel(webRequest.getChannel())
				.withReference(webRequest.getReference())
				.build();
	}

}
