package com.bank.transactions.coreservice;

import java.security.InvalidParameterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.stereotype.Service;

import com.bank.framework.converter.Converter;
import com.bank.transactions.coreservice.converters.TransactionResponseIntoTransactionWebResponseConverter;
import com.bank.transactions.coreservice.converters.TransactionStatusWebRequestIntoTransactionStatusRequestConverter;
import com.bank.transactions.coreservice.converters.TransactionWebRequestIntoTransactionRequestConverter;
import com.bank.transactions.coreservice.domain.TransactionRequest;
import com.bank.transactions.coreservice.domain.TransactionResponse;
import com.bank.transactions.coreservice.domain.TransactionStatusRequest;
import com.bank.transactions.request.TransactionStatusWebRequest;
import com.bank.transactions.request.TransactionWebRequest;
import com.bank.transactions.response.TransactionWebResponse;

@Service
public class TransactionManagerImpl implements TransactionManager {

	private final TransactionService transactionService;
	private final AccountService accountService;
	private final Converter<TransactionWebRequest, TransactionRequest> transactionWebRequestconverter;
	private final Converter<TransactionStatusWebRequest, TransactionStatusRequest> transactionStatusWebRequestConverter;
	private final Converter<TransactionResponse, TransactionWebResponse> transactionResponseConverter;
	private final String transactionNotValidErrorMessage;
	
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
	public TransactionWebResponse create(TransactionWebRequest webRequest) {
		if (accountService.checkValidTransaction(webRequest.getAccount_iban(), webRequest.getAmount()) == 1) {
			return this.transactionResponseConverter.convert(transactionService.create(transactionWebRequestconverter.convert(webRequest)));
		} else {
			throw new InvalidParameterException(transactionNotValidErrorMessage);
		}
		
	}

	@Override
	public TransactionWebResponse status(TransactionStatusWebRequest statusWebRequest) {
		return this.transactionResponseConverter.convert(transactionService.status(transactionStatusWebRequestConverter.convert(statusWebRequest)));
	}

	@Override
	public TransactionWebResponse search(String iban, Sort sort) {
		return this.transactionResponseConverter.convert(transactionService.search(iban, sort));
	}

}
