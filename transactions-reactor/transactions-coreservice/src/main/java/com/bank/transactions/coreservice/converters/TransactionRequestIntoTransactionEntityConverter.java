package com.bank.transactions.coreservice.converters;

import org.springframework.stereotype.Component;

import com.bank.framework.converter.Converter;
import com.bank.transactions.coreservice.domain.TransactionRequest;
import com.bank.transactions.coreservice.repository.entities.TransactionEntity;

@Component("transactionRequestIntoTransactionEntityConverter")
public class TransactionRequestIntoTransactionEntityConverter implements Converter<TransactionRequest, TransactionEntity>{

	@Override
	public TransactionEntity convert(TransactionRequest request) {

		if (request == null) {
			return null;
		}
		
		return TransactionEntity.builder()
				.withAccount_iban(request.getAccount_iban())
				.withAmount(request.getAmount())
				.withDate(request.getDate())
				.withDescription(request.getDescription())
				.withFee(request.getFee())
				.withReference(request.getReference())
				.withStatus(request.getStatus()!=null ? request.getStatus().getCode() : null)
				.build();
		
	}

}
