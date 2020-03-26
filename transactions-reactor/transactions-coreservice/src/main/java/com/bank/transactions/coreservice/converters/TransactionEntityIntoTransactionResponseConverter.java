package com.bank.transactions.coreservice.converters;

import org.springframework.stereotype.Component;

import com.bank.framework.converter.Converter;
import com.bank.framework.domain.Status;
import com.bank.transactions.coreservice.domain.TransactionResponse;
import com.bank.transactions.coreservice.repository.entities.TransactionEntity;

@Component("transactionEntityIntoTransactionResponseConverter")
public class TransactionEntityIntoTransactionResponseConverter implements Converter<TransactionEntity, TransactionResponse>{

	
	@Override
	public TransactionResponse convert(TransactionEntity entity) {

		if (entity == null) {
			return null;
		}
		
		return TransactionResponse.builder()
				.withAccount_iban(entity.getAccount_iban())
				.withAmount(entity.getAmount())
				.withDate(entity.getDate())
				.withDescription(entity.getDescription())
				.withFee(entity.getFee())
				.withReference(entity.getReference())
				.withStatus(entity.getStatus()!=null ? Status.fromString(entity.getStatus()) : null)
				.build();
	}

}
