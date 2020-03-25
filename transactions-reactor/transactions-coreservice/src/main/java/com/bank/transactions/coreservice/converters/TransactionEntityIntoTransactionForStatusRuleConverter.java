package com.bank.transactions.coreservice.converters;

import org.springframework.stereotype.Component;

import com.bank.framework.converter.Converter;
import com.bank.framework.domain.Channel;
import com.bank.framework.domain.Status;
import com.bank.transactions.coreservice.domain.TransactionForStatusRule;
import com.bank.transactions.coreservice.repository.entities.TransactionEntity;

@Component("transactionEntityIntoTransactionForStatusRuleConverter")
public class TransactionEntityIntoTransactionForStatusRuleConverter implements Converter<TransactionEntity, TransactionForStatusRule> {

	@Override
	public TransactionForStatusRule convert(TransactionEntity entity) {
		
		if (entity == null) {
			return null;
		}
		
		return TransactionForStatusRule.builder()
				.withAccount_iban(entity.getAccount_iban())
				.withAmount(entity.getAmount())
				.withChannel(Channel.fromString(entity.getChannel()))
				.withDate(entity.getDate())
				.withDescription(entity.getDescription())
				.withFee(entity.getFee())
				.withReference(entity.getReference())
				.withStatus(Status.fromString(entity.getStatus()))
				.build();
	}

}
