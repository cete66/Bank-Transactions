package com.bank.transactions.converters;

import org.springframework.stereotype.Component;

import com.bank.framework.converter.Converter;
import com.bank.transactions.coreservice.domain.TransactionRequest;
import com.bank.transactions.response.TransactionWebResponse;

@Component("transactionRequestIntoTransactionWebResponseConverter")
public class TransactionRequestIntoTransactionWebResponseConverter implements Converter<TransactionRequest, TransactionWebResponse>{

	@Override
	public TransactionWebResponse convert(TransactionRequest request) {

		if (request == null) {
			return null;
		}
		
		return TransactionWebResponse.builder()
				.withAmount(request.getAmount())
				.withFee(request.getFee())
				.withReference(request.getReference())
				.withStatus(request.getStatus())
				.build();
	}

}
