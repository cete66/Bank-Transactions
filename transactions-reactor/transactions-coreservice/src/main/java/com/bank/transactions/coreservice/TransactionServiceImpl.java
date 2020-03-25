package com.bank.transactions.coreservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bank.framework.converter.Converter;
import com.bank.framework.coreservice.rule.TransactionCheckStatusRule;
import com.bank.transactions.coreservice.domain.TransactionForStatusRule;
import com.bank.transactions.coreservice.domain.TransactionRequest;
import com.bank.transactions.coreservice.domain.TransactionResponse;
import com.bank.transactions.coreservice.domain.TransactionStatusRequest;
import com.bank.transactions.coreservice.repository.TransactionRepository;
import com.bank.transactions.coreservice.repository.entities.TransactionEntity;
import com.deliveredtechnologies.rulebook.Fact;
import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.model.RuleBook;

@Service("transactionServiceImpl")
public class TransactionServiceImpl implements TransactionService{

	private final TransactionRepository transactionRepository;
	private final Converter<TransactionForStatusRule, TransactionResponse> transactionForStatusRuleConverter;
	private final Converter<TransactionRequest, TransactionEntity> transactionRequestConverter;
	private final Converter<TransactionEntity, TransactionResponse> transactionEntityConverter;
	private final Converter<TransactionEntity, TransactionForStatusRule> transactionEntityForStatusRuleConverter;
	private final String sortAttribute;
	private final RuleBook<Object> statusRule;
	
	@Autowired
	public TransactionServiceImpl(final TransactionRepository transactionRepository,
								@Value("${com.bank.transactions.coreservice.default.sort}") final String sortAttribute,
								final Converter<TransactionForStatusRule, TransactionResponse> transactionForStatusRuleConverter,
								final Converter<TransactionRequest, TransactionEntity> transactionRequestConverter,
								final TransactionCheckStatusRule statusRule,
								final Converter<TransactionEntity, TransactionForStatusRule> transactionEntityForStatusRuleConverter,
								final Converter<TransactionEntity, TransactionResponse> transactionEntityConverter) {
		this.transactionRepository = transactionRepository;
		this.sortAttribute = sortAttribute;
		this.transactionForStatusRuleConverter = transactionForStatusRuleConverter;
		this.transactionRequestConverter = transactionRequestConverter;
		this.statusRule = statusRule.defineRules();
		this.transactionEntityForStatusRuleConverter = transactionEntityForStatusRuleConverter;
		this.transactionEntityConverter = transactionEntityConverter;
	}
	
	@Override
	public TransactionResponse create(TransactionRequest request) {
		return transactionEntityConverter.convert(transactionRepository.save(transactionRequestConverter.convert(request)));
	}

	@Override
	public TransactionResponse status(TransactionStatusRequest statusRequest) {
		
		TransactionForStatusRule transactionStatusForRule =  transactionEntityForStatusRuleConverter.convert(transactionRepository
		.status(statusRequest.getChannel().getCode(), statusRequest.getReference()));
		
		FactMap<TransactionForStatusRule> factMap = new FactMap<>();
		factMap.put(new Fact<TransactionForStatusRule>("transaction",transactionStatusForRule));
		this.statusRule.run(factMap);
		transactionStatusForRule = (TransactionForStatusRule) this.statusRule.getResult().get().getValue();
		
		return transactionForStatusRuleConverter.convert(transactionStatusForRule);
	}

	@Override
	public TransactionResponse search(String iban, Sort sort) {
		return transactionEntityConverter.convert(transactionRepository
				.searchFilterByAccountSortByAmount(iban, org.springframework.data.domain.Sort.by(
						Direction.fromString(sort.getSortParameter()), sortAttribute)));
	}

}
