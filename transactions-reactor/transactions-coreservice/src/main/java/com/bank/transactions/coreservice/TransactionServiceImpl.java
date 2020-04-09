package com.bank.transactions.coreservice;

import com.bank.framework.converter.Converter;
import com.bank.framework.domain.Status;
import com.bank.transactions.coreservice.domain.TransactionForStatusRule;
import com.bank.transactions.coreservice.domain.TransactionRequest;
import com.bank.transactions.coreservice.domain.TransactionResponse;
import com.bank.transactions.coreservice.domain.TransactionStatusRequest;
import com.bank.transactions.coreservice.repository.TransactionRepository;
import com.bank.transactions.coreservice.repository.entities.TransactionEntity;
import com.bank.transactions.coreservice.rule.TransactionCheckStatusRule;
import com.deliveredtechnologies.rulebook.Fact;
import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.model.RuleBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("transactionServiceImpl")
@Transactional
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepository;
	private final Converter<TransactionForStatusRule, TransactionResponse> transactionForStatusRuleConverter;
	private final Converter<TransactionRequest, TransactionEntity> transactionRequestConverter;
	private final Converter<TransactionEntity, TransactionResponse> transactionEntityConverter;
	private final Converter<TransactionResponse, TransactionForStatusRule> transactionResponseForStatusRuleConverter;
	private final String sortASC;
	private final String sortDESC;
	private final RuleBook<TransactionForStatusRule> statusRule;
	private final String sortOrderInvalidErrorMessage;

	@Autowired
	public TransactionServiceImpl(final TransactionRepository transactionRepository,
			@Value("${com.bank.transactions.coreservice.default.sort.ASC}") final String sortASC,
			@Value("${com.bank.transactions.coreservice.default.sort.DESC}") final String sortDESC,
			final Converter<TransactionForStatusRule, TransactionResponse> transactionForStatusRuleConverter,
			final Converter<TransactionRequest, TransactionEntity> transactionRequestConverter,
			final TransactionCheckStatusRule statusRule,
			final Converter<TransactionResponse, TransactionForStatusRule> transactionResponseForStatusRuleConverter,
			final Converter<TransactionEntity, TransactionResponse> transactionEntityConverter,
			@Value("${com.bank.transactions.coreservice.default.sortOrder.invalid}") final String sortOrderInvalidErrorMessage) {
		this.transactionRepository = transactionRepository;
		this.sortASC = sortASC;
		this.sortDESC = sortDESC;
		this.transactionForStatusRuleConverter = transactionForStatusRuleConverter;
		this.transactionRequestConverter = transactionRequestConverter;
		this.statusRule = statusRule.defineRules();
		this.transactionResponseForStatusRuleConverter = transactionResponseForStatusRuleConverter;
		this.transactionEntityConverter = transactionEntityConverter;
		this.sortOrderInvalidErrorMessage = sortOrderInvalidErrorMessage;
	}

	@Override
	public TransactionResponse create(final TransactionRequest request) {
		return transactionEntityConverter
				.convert(transactionRepository.save(transactionRequestConverter.convert(request)))
				.cloneBuilder()
				.withStatus(Status.PENDING)
				.build();
	}

	@Override
	public TransactionResponse status(final TransactionStatusRequest statusRequest) {

		final TransactionEntity entity = transactionRepository.checkDateForStatusRule(statusRequest.getReference());

		TransactionForStatusRule transactionStatusForRule;

		if (entity != null) {
			transactionStatusForRule = transactionResponseForStatusRuleConverter.convert(transactionEntityConverter
					.convert(entity).cloneBuilder().withChannel(statusRequest.getChannel()).build());
		} else {
			transactionStatusForRule = TransactionForStatusRule.builder().withChannel(statusRequest.getChannel())
					.build();
		}

		FactMap<TransactionForStatusRule> factMap = new FactMap<>();
		factMap.put(new Fact<TransactionForStatusRule>("transaction", transactionStatusForRule));
		this.statusRule.run(factMap);
		transactionStatusForRule = this.statusRule.getResult().orElseThrow(InvalidParameterException::new).getValue();

		return transactionForStatusRuleConverter.convert(transactionStatusForRule);
	}

	@Override
	public List<TransactionResponse> search(final String iban, final String sortOrder) {

		String sort = Optional.ofNullable(sortOrder).orElse("");

		List<TransactionEntity> result;
		List<TransactionResponse> convertedResult = null;
		
		if (sortASC.equals(sort.toUpperCase().trim())) {
			result = transactionRepository.searchTransactionsFilterByIbanSortASCByAmount(iban);
		} else if (sortDESC.equals(sort.toUpperCase().trim())) {
			result = transactionRepository.searchTransactionsFilterByIbanSortDESCByAmount(iban);
		} else {
			throw new InvalidParameterException(sortOrderInvalidErrorMessage);
		}
		if (result!=null && !result.isEmpty()) {
			convertedResult = result.stream().map(transactionEntityConverter::convert).collect(Collectors.toList());
		}
		return convertedResult;
	}

}
