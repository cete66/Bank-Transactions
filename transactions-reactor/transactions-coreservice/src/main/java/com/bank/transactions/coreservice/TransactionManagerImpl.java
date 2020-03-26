package com.bank.transactions.coreservice;

import java.math.BigDecimal;
import java.security.InvalidParameterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.stereotype.Service;

import com.bank.framework.converter.Converter;
import com.bank.framework.domain.SortOrder;
import com.bank.transactions.coreservice.converters.TransactionResponseIntoTransactionWebResponseConverter;
import com.bank.transactions.coreservice.converters.TransactionStatusWebRequestIntoTransactionStatusRequestConverter;
import com.bank.transactions.coreservice.converters.TransactionWebRequestIntoTransactionRequestConverter;
import com.bank.transactions.coreservice.domain.TransactionRequest;
import com.bank.transactions.coreservice.domain.TransactionResponse;
import com.bank.transactions.coreservice.domain.TransactionStatusRequest;
import com.bank.transactions.request.TransactionStatusWebRequest;
import com.bank.transactions.request.TransactionWebRequest;
import com.bank.transactions.response.TransactionWebResponse;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator;

@Service
public class TransactionManagerImpl implements TransactionManager {

	private final TransactionService transactionService;
	private final AccountService accountService;
	private final Converter<TransactionWebRequest, TransactionRequest> transactionWebRequestconverter;
	private final Converter<TransactionStatusWebRequest, TransactionStatusRequest> transactionStatusWebRequestConverter;
	private final Converter<TransactionResponse, TransactionWebResponse> transactionResponseConverter;
	private final String transactionNotValidErrorMessage;
	private final UUIDGenerator uuidGenerator = new UUIDGenerator();
	
	@Autowired
	public TransactionManagerImpl(final TransactionService transacionsService,
			final Converter<TransactionWebRequest, TransactionRequest> transactionWebRequestconverter,
			final Converter<TransactionStatusWebRequest, TransactionStatusRequest> transactionStatusWebRequestConverter,
			final Converter<TransactionResponse, TransactionWebResponse> transactionResponseConverter,
			final AccountService accountService,
			@Value("${com.bank.transactions.coreservice.default.transaction.invalid}") final String transactionNotValidErrorMessage) {
		this.transactionService = transacionsService;
		this.transactionWebRequestconverter = transactionWebRequestconverter;
		this.transactionStatusWebRequestConverter = transactionStatusWebRequestConverter;
		this.transactionResponseConverter = transactionResponseConverter;
		this.accountService = accountService;
		this.transactionNotValidErrorMessage = transactionNotValidErrorMessage;
	}
	
	@Override
	public TransactionWebResponse create(final TransactionWebRequest webRequest) {
		
		final BigDecimal amount = webRequest.getFee()!=null ? 
				webRequest.getAmount().subtract(webRequest.getFee()) : webRequest.getAmount();
		
		if (accountService.checkValidTransaction(webRequest.getAccount_iban(), amount) == 1) {
			TransactionRequest request = transactionWebRequestconverter.convert(webRequest);
			request = processReference(request);
			accountService.confirmTransaction(request.getAccount_iban(), amount);
			return this.transactionResponseConverter.convert(transactionService.create(request));
		} else {
			throw new InvalidParameterException(transactionNotValidErrorMessage);
		}
		
	}

	private TransactionRequest processReference(final TransactionRequest request) {
		if (request!=null && (request.getReference() == null || request.getReference().trim().isEmpty()) ){
			return request.cloneBuilder().withReference(uuidGenerator.generateId(request).toString()).build();
		}
		return request;
	}

	@Override
	public TransactionWebResponse status(final TransactionStatusWebRequest statusWebRequest) {
		return this.transactionResponseConverter.convert(transactionService.status(transactionStatusWebRequestConverter.convert(statusWebRequest)));
	}

	@Override
	public TransactionWebResponse search(final String iban, final String sortOrder) {
		return this.transactionResponseConverter.convert(transactionService.search(iban, sortOrder));
	}

}
