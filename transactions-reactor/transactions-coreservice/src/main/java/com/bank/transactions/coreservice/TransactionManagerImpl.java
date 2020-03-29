package com.bank.transactions.coreservice;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bank.framework.converter.Converter;
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
	public TransactionManagerImpl(final TransactionService transactionService,
			final Converter<TransactionWebRequest, TransactionRequest> transactionWebRequestconverter,
			final Converter<TransactionStatusWebRequest, TransactionStatusRequest> transactionStatusWebRequestConverter,
			final Converter<TransactionResponse, TransactionWebResponse> transactionResponseConverter,
			final AccountService accountService,
			@Value("${com.bank.transactions.coreservice.default.transaction.invalid}") final String transactionNotValidErrorMessage) {
		this.transactionService = transactionService;
		this.transactionWebRequestconverter = transactionWebRequestconverter;
		this.transactionStatusWebRequestConverter = transactionStatusWebRequestConverter;
		this.transactionResponseConverter = transactionResponseConverter;
		this.accountService = accountService;
		this.transactionNotValidErrorMessage = transactionNotValidErrorMessage;
	}
	
	@Override
	public TransactionWebResponse create(final TransactionWebRequest webRequest) {
		
		TransactionRequest request = transactionWebRequestconverter.convert(webRequest);
		
		request = request.cloneBuilder().withAmount(processAmountAndFee(request)).build();
		
		if (accountService.confirmTransaction(request.getAccount_iban(), request.getAmount())) {
			return transactionResponseConverter.convert(transactionService.create(request));
		} else {
			throw new InvalidParameterException(transactionNotValidErrorMessage);
		} 
	}

	@Override
	public TransactionWebResponse status(final TransactionStatusWebRequest statusWebRequest) {
		return this.transactionResponseConverter.convert(
				transactionService.status(
					transactionStatusWebRequestConverter.convert(statusWebRequest)));
	}

	@Override
	public List<TransactionWebResponse> search(final String iban, final String sortOrder) {
		List<TransactionResponse> result = transactionService
				.search(iban, sortOrder);
		return result!=null && !result.isEmpty() ? result.stream()
				.map(e -> transactionResponseConverter.convert(e)).collect(Collectors.toList()) : null;
		
	}
	
	protected BigDecimal processAmountAndFee(final TransactionRequest request) {
		if (request == null) {
			return null;
		}
		
		BigDecimal fee = request.getFee()!=null ? request.getFee() : BigDecimal.ZERO;
		BigDecimal amount = request.getAmount();
		
		if (amount.compareTo(BigDecimal.ZERO) < 0 && fee.compareTo(BigDecimal.ZERO) > 0) {
			fee = fee.multiply(BigDecimal.valueOf(-1));
		} else if (amount.compareTo(BigDecimal.ZERO) > 0 && fee.compareTo(BigDecimal.ZERO) < 0) {
			fee = fee.multiply(BigDecimal.valueOf(-1));
		}
		
		return amount.subtract(fee);
	}

}
