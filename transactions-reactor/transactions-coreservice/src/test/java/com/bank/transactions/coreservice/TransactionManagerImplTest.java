package com.bank.transactions.coreservice;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;

import com.bank.framework.converter.Converter;
import com.bank.framework.domain.Channel;
import com.bank.framework.domain.Status;
import com.bank.framework.persistence.exceptions.TransactionNotAllowedException;
import com.bank.transactions.coreservice.converters.TransactionResponseIntoTransactionWebResponseConverter;
import com.bank.transactions.coreservice.converters.TransactionStatusWebRequestIntoTransactionStatusRequestConverter;
import com.bank.transactions.coreservice.converters.TransactionWebRequestIntoTransactionRequestConverter;
import com.bank.transactions.coreservice.domain.TransactionRequest;
import com.bank.transactions.coreservice.domain.TransactionResponse;
import com.bank.transactions.coreservice.domain.TransactionStatusRequest;
import com.bank.transactions.request.TransactionStatusWebRequest;
import com.bank.transactions.request.TransactionStatusWebRequest.TransactionStatusWebRequestBuilder;
import com.bank.transactions.request.TransactionWebRequest;
import com.bank.transactions.request.TransactionWebRequest.TransactionWebRequestBuilder;
import com.bank.transactions.response.TransactionWebResponse;

public class TransactionManagerImplTest {

	private static final String VALID_IBAN = "validIban";
	private static final BigDecimal VALID_AMOUNT = BigDecimal.TEN;
	private static final String DESC = "desc";
	private static final BigDecimal FEE = BigDecimal.ONE;
	private static final String INVALID_SORT_ORDER = "asd";
	
	private final Converter<TransactionWebRequest, TransactionRequest> transactionWebRequestconverter 
										= new TransactionWebRequestIntoTransactionRequestConverter();
	private final Converter<TransactionStatusWebRequest, TransactionStatusRequest> transactionStatusWebRequestConverter
										= new TransactionStatusWebRequestIntoTransactionStatusRequestConverter();
	private final Converter<TransactionResponse, TransactionWebResponse> transactionResponseConverter
										= new TransactionResponseIntoTransactionWebResponseConverter();
	@Value("${com.bank.transactions.coreservice.default.transaction.invalid}")
	private final String transactionNotValidErrorMessage = "The transaction amount can't be greater than account's total balance";
	@Value("${com.bank.transactions.coreservice.default.sort.ASC}")
	private final String sortASC = "ASC";
	@Value("${com.bank.transactions.coreservice.default.sort.DESC}")
	private final String sortDESC = "DESC";
	
	@Mock
	private TransactionService transactionService;
	@Mock
	private AccountService accountService;
	private TransactionManagerImpl transactionManager;
	private final TransactionWebRequestBuilder webRequestBuilder = TransactionWebRequest.builder()
																						.withAccount_iban(VALID_IBAN)
																						.withAmount(VALID_AMOUNT)
																						.withDate(LocalDateTime.now())
																						.withDescription(DESC)
																						.withFee(FEE)
																						.withReference(UUID.randomUUID().toString());
	private final TransactionStatusWebRequestBuilder statusWebRequestBuilder = TransactionStatusWebRequest.builder()
																						.withChannel(Channel.CLIENT)
																						.withReference(webRequestBuilder.build().getReference());
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		transactionManager = new TransactionManagerImpl(transactionService, transactionWebRequestconverter, transactionStatusWebRequestConverter, 
				transactionResponseConverter, accountService, transactionNotValidErrorMessage);
	}
	
	@Test
	public void givenValidWebRequestCreateShouldCreateTransaction() {
		
		final TransactionWebRequest webRequest = webRequestBuilder.build();
		TransactionRequest request = transactionWebRequestconverter
				.convert(webRequest);
		final BigDecimal processedAmount = transactionManager.processAmountAndFee(request);
		request = request.cloneBuilder().withAmount(processedAmount).build();
		final TransactionWebResponse expected = TransactionWebResponse.builder()
																.withAccount_iban(VALID_IBAN)
																.withAmount(processedAmount)
																.withDate(webRequest.getDate())
																.withDescription(DESC)
																.withFee(FEE)
																.withReference(webRequest.getReference())
																.withStatus(Status.PENDING.getCode())
																.build();
		final TransactionResponse response = TransactionResponse.builder()
																.withAccount_iban(VALID_IBAN)
																.withAmount(processedAmount)
																.withDate(request.getDate())
																.withDescription(DESC)
																.withFee(FEE)
																.withReference(request.getReference())
																.withStatus(Status.fromString(expected.getStatus()))
																.build();
		
		Mockito.doReturn(Boolean.TRUE).when(accountService).confirmTransaction(ArgumentMatchers.eq(VALID_IBAN), 
									ArgumentMatchers.eq(processedAmount));
		Mockito.doReturn(response).when(transactionService).create(ArgumentMatchers.eq(request));
				
		
		MatcherAssert.assertThat(transactionManager.create(webRequest), Matchers.is(expected));
	}
	
	@Test
	public void givenInvalidAmountCreateShouldThrowException() {
		
		final TransactionWebRequest webRequest = webRequestBuilder.withAmount(BigDecimal.valueOf(-2342343434D)).build();
		TransactionRequest request = transactionWebRequestconverter
				.convert(webRequest);
		final BigDecimal processedAmount = transactionManager.processAmountAndFee(request);
		request = request.cloneBuilder().withAmount(processedAmount).build();
		final TransactionWebResponse expected = TransactionWebResponse.builder()
																.withAccount_iban(VALID_IBAN)
																.withAmount(processedAmount)
																.withDate(webRequest.getDate())
																.withDescription(DESC)
																.withFee(FEE)
																.withReference(webRequest.getReference())
																.withStatus(Status.PENDING.getCode())
																.build();
		final TransactionResponse response = TransactionResponse.builder()
																.withAccount_iban(VALID_IBAN)
																.withAmount(processedAmount)
																.withDate(request.getDate())
																.withDescription(DESC)
																.withFee(FEE)
																.withStatus(Status.PENDING)
																.withReference(request.getReference())
																.withStatus(Status.fromString(expected.getStatus()))
																.build();
		
		Mockito.doReturn(Boolean.FALSE).when(accountService).confirmTransaction(ArgumentMatchers.eq(VALID_IBAN), 
									ArgumentMatchers.eq(processedAmount));
		Mockito.doReturn(response).when(transactionService).create(ArgumentMatchers.eq(request));
				
		
		Assertions.assertThrows(TransactionNotAllowedException.class, () -> {transactionManager.create(webRequest);});
	}
	
	@Test
	public void givenValidIbanAndSortAscSearchShouldReturnListAscSorted() {
		
		final TransactionResponse response = TransactionResponse.builder()
				.withAccount_iban(VALID_IBAN)
				.withAmount(VALID_AMOUNT)
				.withDate(LocalDateTime.now())
				.withDescription(DESC)
				.withFee(FEE)
				.withReference(UUID.randomUUID().toString())
				.withStatus(Status.PENDING)
				.build();
		List<TransactionResponse> expectedToConvert = Arrays.asList(response.cloneBuilder()
				.withReference(UUID.randomUUID().toString()).withAmount(response.getAmount().add(BigDecimal.TEN)).build(), response);
		
		List<TransactionWebResponse> expected = Arrays.asList(transactionResponseConverter.convert(expectedToConvert.get(0)),
				transactionResponseConverter.convert(expectedToConvert.get(1)));
		Mockito.doReturn(expectedToConvert).when(transactionService).search(ArgumentMatchers.eq(VALID_IBAN), ArgumentMatchers.eq(sortASC));
		
		MatcherAssert.assertThat(transactionManager.search(VALID_IBAN, sortASC), 
				Matchers.containsInRelativeOrder(expected.get(0), expected.get(1)));
	}
	
	@Test
	public void givenValidIbanAndSortDescSearchShouldReturnListDescSorted() {
		
		final TransactionResponse response = TransactionResponse.builder()
				.withAccount_iban(VALID_IBAN)
				.withAmount(VALID_AMOUNT)
				.withDate(LocalDateTime.now())
				.withDescription(DESC)
				.withFee(FEE)
				.withReference(UUID.randomUUID().toString())
				.withStatus(Status.PENDING)
				.build();
		List<TransactionResponse> expectedToConvert = Arrays.asList(response, response.cloneBuilder()
				.withReference(UUID.randomUUID().toString()).withAmount(response.getAmount().add(BigDecimal.TEN)).build());
		
		List<TransactionWebResponse> expected = Arrays.asList(transactionResponseConverter.convert(expectedToConvert.get(1)),
																transactionResponseConverter.convert(expectedToConvert.get(0)));
		Mockito.doReturn(expectedToConvert).when(transactionService).search(ArgumentMatchers.eq(VALID_IBAN), ArgumentMatchers.eq(sortASC));
		
		MatcherAssert.assertThat(transactionManager.search(VALID_IBAN, sortASC), 
				Matchers.containsInRelativeOrder(expected.get(1), expected.get(0)));
	}
	
	@Test
	public void givenValidIbanAndInvalidSortSearchShouldThrowException() {
		
		Mockito.doThrow(IllegalArgumentException.class).when(transactionService)
						.search(ArgumentMatchers.eq(VALID_IBAN), ArgumentMatchers.eq(INVALID_SORT_ORDER));
		Assertions.assertThrows(IllegalArgumentException.class, () -> {transactionManager.search(VALID_IBAN, INVALID_SORT_ORDER);});
	}
	
	@Test
	public void givenNullIbanSearchShouldReturnNull() {
		Mockito.doReturn(null).when(transactionService).search(ArgumentMatchers.eq(null), ArgumentMatchers.eq(sortASC));
		
		Assertions.assertNull(transactionManager.search(null, sortASC));
	}
	
	@Test
	public void givenValidReferenceShouldReturnStatusPending() {
		final TransactionStatusWebRequest webRequest = statusWebRequestBuilder.build();
		final TransactionStatusRequest request = transactionStatusWebRequestConverter.convert(webRequest);
		final TransactionResponse expectedToConvert = TransactionResponse.builder()
																.withAmount(VALID_AMOUNT)
																.withFee(FEE)
																.withReference(webRequest.getReference())
																.withStatus(Status.PENDING)
																.build();
		final TransactionWebResponse expected = transactionResponseConverter.convert(expectedToConvert);
		
		Mockito.doReturn(expectedToConvert).when(transactionService).status(ArgumentMatchers.eq(request));
		MatcherAssert.assertThat(transactionManager.status(webRequest), Matchers.is(expected));
	}
	
	@Test
	public void givenInvalidReferenceShouldReturnStatusInvalid() {
		final TransactionStatusWebRequest webRequest = statusWebRequestBuilder.build();
		final TransactionStatusRequest request = transactionStatusWebRequestConverter.convert(webRequest);
		final TransactionResponse expectedToConvert = TransactionResponse.builder()
																.withStatus(Status.INVALID)
																.build();
		final TransactionWebResponse expected = transactionResponseConverter.convert(expectedToConvert);
		
		Mockito.doReturn(expectedToConvert).when(transactionService).status(ArgumentMatchers.eq(request));
		MatcherAssert.assertThat(transactionManager.status(webRequest), Matchers.is(expected));
	}
}
