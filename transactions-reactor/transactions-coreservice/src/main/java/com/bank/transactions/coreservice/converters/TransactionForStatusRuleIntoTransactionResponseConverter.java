package com.bank.transactions.coreservice.converters;

import org.springframework.stereotype.Component;

import com.bank.framework.converter.Converter;
import com.bank.transactions.coreservice.domain.TransactionForStatusRule;
import com.bank.transactions.coreservice.domain.TransactionResponse;

@Component("transactionForStatusRuleIntoTransactionResponseConverter")
public class TransactionForStatusRuleIntoTransactionResponseConverter implements Converter<TransactionForStatusRule, TransactionResponse>{

	
	@Override
	public TransactionResponse convert(TransactionForStatusRule entity) {

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
				.withStatus(entity.getStatus())
				.build();
	}

}
