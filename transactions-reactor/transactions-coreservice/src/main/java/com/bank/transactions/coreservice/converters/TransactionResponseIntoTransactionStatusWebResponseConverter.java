package com.bank.transactions.coreservice.converters;

import org.springframework.stereotype.Component;

import com.bank.framework.converter.Converter;
import com.bank.transactions.coreservice.domain.TransactionResponse;
import com.bank.transactions.response.TransactionStatusWebResponse;
import com.bank.transactions.response.TransactionWebResponse;

@Component("transactionResponseIntoTransactionStatusWebResponseConverter")
public class TransactionResponseIntoTransactionStatusWebResponseConverter implements Converter<TransactionResponse, TransactionStatusWebResponse>{

	@Override
	public TransactionStatusWebResponse convert(TransactionResponse request) {

		if (request == null) {
			return null;
		}
		
		return TransactionStatusWebResponse.builder()
				.withAmount(request.getAmount())
				.withFee(request.getFee())
				.withReference(request.getReference())
				.withStatus(request.getStatus()!=null ? request.getStatus().getCode() : null)
				.build();
	}

}
