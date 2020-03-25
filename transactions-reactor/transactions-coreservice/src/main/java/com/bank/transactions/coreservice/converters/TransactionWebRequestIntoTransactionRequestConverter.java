package com.bank.transactions.coreservice.converters;

import org.springframework.stereotype.Component;

import com.bank.framework.converter.Converter;
import com.bank.transactions.coreservice.domain.TransactionRequest;
import com.bank.transactions.request.TransactionWebRequest;

@Component("transactionWebRequestIntoTransactionRequestConverter")
public class TransactionWebRequestIntoTransactionRequestConverter implements Converter<TransactionWebRequest, TransactionRequest>{

	@Override
	public TransactionRequest convert(TransactionWebRequest webRequest) {

		if (webRequest == null) {
			return null;
		}
		
		return TransactionRequest.builder()
				.withAccount_iban(webRequest.getAccount_iban())
				.withAmount(webRequest.getAmount())
				.withDate(webRequest.getDate())
				.withDescription(webRequest.getDescription())
				.withFee(webRequest.getFee())
				.withReference(webRequest.getReference())
				.build();
	}

}
