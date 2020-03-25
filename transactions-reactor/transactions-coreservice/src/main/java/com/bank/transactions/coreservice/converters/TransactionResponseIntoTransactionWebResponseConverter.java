package com.bank.transactions.coreservice.converters;

import org.springframework.stereotype.Component;

import com.bank.framework.converter.Converter;
import com.bank.transactions.coreservice.domain.TransactionResponse;
import com.bank.transactions.response.TransactionWebResponse;

@Component("transactionResponseIntoTransactionWebResponseConverter")
public class TransactionResponseIntoTransactionWebResponseConverter implements Converter<TransactionResponse, TransactionWebResponse>{

	@Override
	public TransactionWebResponse convert(TransactionResponse request) {

		if (request == null) {
			return null;
		}
		
		return TransactionWebResponse.builder()
				.withAmount(request.getAmount())
				.withFee(request.getFee())
				.withReference(request.getReference())
				.withStatus(request.getStatus())
				.withAccount_iban(request.getAccount_iban())
				.withDate(request.getDate())
				.withDescription(request.getDescription())
				.build();
	}

}
