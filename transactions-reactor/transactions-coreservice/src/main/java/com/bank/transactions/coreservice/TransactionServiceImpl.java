package com.bank.transactions.coreservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bank.framework.converter.Converter;
import com.bank.transactions.coreservice.domain.TransactionRequest;
import com.bank.transactions.coreservice.domain.TransactionResponse;
import com.bank.transactions.coreservice.domain.TransactionStatusRequest;
import com.bank.transactions.coreservice.repository.TransactionRepository;
import com.bank.transactions.coreservice.repository.entities.TransactionEntity;

@Service("transactionServiceImpl")
public class TransactionServiceImpl implements TransactionService{

	private final TransactionRepository transactionRepository;
	private final Converter<TransactionEntity, TransactionResponse> transactionEntityConverter;
	private final Converter<TransactionRequest, TransactionEntity> transactionRequestConverter;
	private final String sortAttribute;
	
	@Autowired
	public TransactionServiceImpl(final TransactionRepository transactionRepository,
								@Value("${com.bank.transactions.coreservice.default.sort}") final String sortAttribute,
								final Converter<TransactionEntity, TransactionResponse> transactionEntityConverter,
								final Converter<TransactionRequest, TransactionEntity> transactionRequestConverter) {
		this.transactionRepository = transactionRepository;
		this.sortAttribute = sortAttribute;
		this.transactionEntityConverter = transactionEntityConverter;
		this.transactionRequestConverter = transactionRequestConverter;
	}
	
	@Override
	public TransactionResponse create(TransactionRequest request) {
		//TODO VER SI NO TIENE REFERENCE, SI NO TIENE, GENERAR UNA - ROBIN
		return transactionEntityConverter.convert(transactionRepository.save(transactionRequestConverter.convert(request)));
	}

	@Override
	public TransactionResponse status(TransactionStatusRequest statusRequest) {
		return transactionEntityConverter.convert(transactionRepository
				.status(statusRequest.getChannel().getCode(), statusRequest.getReference()));
	}

	@Override
	public TransactionResponse search(String iban, Sort sort) {
		return transactionEntityConverter.convert(transactionRepository
				.searchFilterByAccountSortByAmount(iban, org.springframework.data.domain.Sort.by(
						Direction.fromString(sort.getSortParameter()), sortAttribute)));
	}

}
