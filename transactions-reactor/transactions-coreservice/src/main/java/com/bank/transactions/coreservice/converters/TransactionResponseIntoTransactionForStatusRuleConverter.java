package com.bank.transactions.coreservice.converters;

import org.springframework.stereotype.Component;

import com.bank.framework.converter.Converter;
import com.bank.transactions.coreservice.domain.TransactionForStatusRule;
import com.bank.transactions.coreservice.domain.TransactionResponse;

@Component("transactionResponseIntoTransactionForStatusRuleConverter")
public class TransactionResponseIntoTransactionForStatusRuleConverter implements Converter<TransactionResponse, TransactionForStatusRule> {

	@Override
	public TransactionForStatusRule convert(TransactionResponse entity) {
		
		if (entity == null) {
			return null;
		}
		
		return TransactionForStatusRule.builder()
				.withAccount_iban(entity.getAccount_iban())
				.withAmount(entity.getAmount())
				.withChannel(entity.getChannel())
				.withDate(entity.getDate())
				.withDescription(entity.getDescription())
				.withFee(entity.getFee())
				.withReference(entity.getReference())
				.withStatus(entity.getStatus())
				.build();
	}

}
