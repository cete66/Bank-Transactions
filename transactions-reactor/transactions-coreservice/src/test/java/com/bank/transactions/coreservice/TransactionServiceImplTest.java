package com.bank.transactions.coreservice;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang.NullArgumentException;
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
import org.springframework.dao.DataIntegrityViolationException;

import com.bank.framework.converter.Converter;
import com.bank.framework.domain.Channel;
import com.bank.framework.domain.Status;
import com.bank.transactions.coreservice.converters.TransactionEntityIntoTransactionResponseConverter;
import com.bank.transactions.coreservice.converters.TransactionForStatusRuleIntoTransactionResponseConverter;
import com.bank.transactions.coreservice.converters.TransactionRequestIntoTransactionEntityConverter;
import com.bank.transactions.coreservice.converters.TransactionResponseIntoTransactionForStatusRuleConverter;
import com.bank.transactions.coreservice.domain.TransactionForStatusRule;
import com.bank.transactions.coreservice.domain.TransactionRequest;
import com.bank.transactions.coreservice.domain.TransactionRequest.TransactionRequestBuilder;
import com.bank.transactions.coreservice.domain.TransactionResponse;
import com.bank.transactions.coreservice.domain.TransactionStatusRequest;
import com.bank.transactions.coreservice.repository.TransactionRepository;
import com.bank.transactions.coreservice.repository.entities.TransactionEntity;
import com.bank.transactions.coreservice.rule.TransactionCheckStatusRule;

public class TransactionServiceImplTest {

	
	private static final String VALID_IBAN = "validIban";
	private static final BigDecimal VALID_AMOUNT = BigDecimal.TEN;
	private static final String DESC = "desc";
	private static final String INVALID_SORT = "invalidSort";
	@Mock
	private TransactionRepository transactionRepository;
	private TransactionServiceImpl service;
	private final Converter<TransactionRequest, TransactionEntity> transactionRequestConverter 
										= new TransactionRequestIntoTransactionEntityConverter();
	private final Converter<TransactionEntity, TransactionResponse> transactionEntityConverter
										= new TransactionEntityIntoTransactionResponseConverter();
	private final Converter<TransactionForStatusRule, TransactionResponse> transactionForStatusRuleConverter
										= new TransactionForStatusRuleIntoTransactionResponseConverter();
	private final Converter<TransactionResponse, TransactionForStatusRule> transactionResponseForStatusRuleConverter
										= new TransactionResponseIntoTransactionForStatusRuleConverter();
	
	@Value("${com.bank.transactions.coreservice.default.sort.ASC}")
	private final String sortASC = "ASC";
	@Value("${com.bank.transactions.coreservice.default.sort.DESC}")
	private final String sortDESC = "DESC";
	@Value("${com.bank.transactions.coreservice.default.sortOrder.null}") 
	private final String sortOrderNullErrorMessage = "The sort order string value is null";
	@Value("${com.bank.transactions.coreservice.default.sortOrder.invalid}") 
	private final String sortOrderInvalidErrorMessage = "The sort order string value is invalid (neither ASC or DESC)";
	private final TransactionCheckStatusRule statusRule = new TransactionCheckStatusRule();
	private final TransactionRequestBuilder requestbuilder = TransactionRequest.builder()
			.withAccount_iban(VALID_IBAN)
			.withAmount(VALID_AMOUNT)
			.withDate(LocalDateTime.now())
			.withDescription(DESC)
			.withReference(UUID.randomUUID().toString())
			.withFee(BigDecimal.ONE);

	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		this.service = new TransactionServiceImpl(transactionRepository, sortASC, sortDESC, 
				transactionForStatusRuleConverter, transactionRequestConverter, statusRule, 
				transactionResponseForStatusRuleConverter, transactionEntityConverter, 
				sortOrderNullErrorMessage, sortOrderInvalidErrorMessage);
	}
	
	@Test
	public void givenValidTransactionRequestCreateShouldReturnValidTransactionResponse() {
		final TransactionRequest request = requestbuilder.build();
		final TransactionEntity repoExpected = transactionRequestConverter.convert(request);
		final TransactionResponse expected = transactionEntityConverter.convert(repoExpected);
		Mockito.doReturn(repoExpected).when(transactionRepository).save(ArgumentMatchers.eq(repoExpected));
		
		final TransactionResponse actual = this.service.create(request);
		MatcherAssert.assertThat(actual, Matchers.is(expected));
	}
	
	@Test
	public void givenInvalidTransactionRequestCreateShouldThrowException() {
		final TransactionRequest request = requestbuilder.build().cloneBuilder().withAccount_iban(null).build();
		final TransactionEntity repoExpected = transactionRequestConverter.convert(request);
		Mockito.doThrow(DataIntegrityViolationException.class).when(transactionRepository).save(ArgumentMatchers.eq(repoExpected));
		
		Assertions.assertThrows(DataIntegrityViolationException.class, () -> {this.service.create(request);});
	}
	
	@Test
	public void givenValidTransactionStatusRequestStatusShouldReturnValidTransactionResponseAsRuleD() {
		final TransactionStatusRequest request = TransactionStatusRequest.builder()
												.withChannel(Channel.CLIENT)
												.withReference(UUID.randomUUID().toString())
												.build();
		final TransactionEntity expectedToConvert = TransactionEntity.builder()
															.withAccount_iban(VALID_IBAN)
															.withAmount(VALID_AMOUNT)
															.withDate(LocalDateTime.now())
															.withDescription(DESC)
															.withFee(BigDecimal.ONE)
															.withId(1)
															.withReference(request.getReference())
															.build();

		final TransactionResponse expected = TransactionResponse.builder()
											.withReference(expectedToConvert.getReference())
											.withStatus(Status.PENDING)
											.withAmount(expectedToConvert.getAmount().subtract(expectedToConvert.getFee()))
											.build();

		Mockito.doReturn(expectedToConvert).when(transactionRepository).checkDateForStatusRule(ArgumentMatchers.eq(request.getReference()));

		final TransactionResponse actual = this.service.status(request);
		MatcherAssert.assertThat(actual, Matchers.is(expected));
	}
	
	@Test
	public void givenInvalidTransactionStatusRequestStatusShouldReturnValidTransactionResponseAsRuleA() {
		final TransactionStatusRequest request = TransactionStatusRequest.builder()
												.withChannel(Channel.CLIENT)
												.build();
		final TransactionEntity expectedToConvert = TransactionEntity.builder()
															.withAccount_iban(VALID_IBAN)
															.withAmount(VALID_AMOUNT)
															.withDate(LocalDateTime.now())
															.withDescription(DESC)
															.withFee(BigDecimal.ONE)
															.withId(1)
															.withReference(request.getReference())
															.build();

		final TransactionResponse expected = TransactionResponse.builder()
											.withReference(expectedToConvert.getReference())
											.withStatus(Status.INVALID)
											.build();

		Mockito.doReturn(expectedToConvert).when(transactionRepository).checkDateForStatusRule(ArgumentMatchers.eq(request.getReference()));

		final TransactionResponse actual = this.service.status(request);
		MatcherAssert.assertThat(actual, Matchers.is(expected));
	}
	
	@Test
	public void givenValidIbanAndSortAscSearchShouldReturnValidList() {
		
		final TransactionEntity entityA = TransactionEntity.builder()
															.withAccount_iban(VALID_IBAN)
															.withAmount(VALID_AMOUNT)
															.withDate(LocalDateTime.now())
															.withDescription(DESC)
															.withFee(BigDecimal.ONE)
															.withId(1)
															.withReference(UUID.randomUUID().toString())
															.build();
		final TransactionEntity entityB = entityA.cloneBuilder()
													.withAccount_iban(VALID_IBAN)
													.withId(2)
													.withAmount(entityA.getAmount().add(BigDecimal.ONE))
													.withReference(UUID.randomUUID().toString())
													.build();
		
		List<TransactionEntity> expectedToConvert = Arrays.asList(entityA, entityB);
		
		Mockito.doReturn(expectedToConvert).when(transactionRepository).searchTransactionsFilterByIbanSortASCByAmount(ArgumentMatchers.eq(VALID_IBAN));
		
		List<TransactionResponse> expected = expectedToConvert.stream()
				.map(e -> transactionEntityConverter.convert(e)).collect(Collectors.toList());

		MatcherAssert.assertThat(this.service.search(VALID_IBAN, sortASC), Matchers.containsInRelativeOrder(expected.get(0), expected.get(1)));
	}
	
	@Test
	public void givenValidIbanAndSortDescSearchShouldReturnValidList() {
		
		final TransactionEntity entityA = TransactionEntity.builder()
															.withAccount_iban(VALID_IBAN)
															.withAmount(VALID_AMOUNT)
															.withDate(LocalDateTime.now())
															.withDescription(DESC)
															.withFee(BigDecimal.ONE)
															.withId(1)
															.withReference(UUID.randomUUID().toString())
															.build();
		final TransactionEntity entityB = entityA.cloneBuilder()
													.withAccount_iban(VALID_IBAN)
													.withId(2)
													.withAmount(entityA.getAmount().add(BigDecimal.ONE))
													.withReference(UUID.randomUUID().toString())
													.build();
		
		List<TransactionEntity> expectedToConvert = Arrays.asList(entityB, entityA);
		
		Mockito.doReturn(expectedToConvert).when(transactionRepository).searchTransactionsFilterByIbanSortDESCByAmount(ArgumentMatchers.eq(VALID_IBAN));
		
		List<TransactionResponse> expected = expectedToConvert.stream()
				.map(e -> transactionEntityConverter.convert(e)).collect(Collectors.toList());

		MatcherAssert.assertThat(this.service.search(VALID_IBAN, sortDESC), Matchers.containsInRelativeOrder(expected.get(0), expected.get(1)));
	}
	
	@Test
	public void givenNullSortSearchShouldThrowException() {
		Assertions.assertThrows(NullArgumentException.class, () -> {this.service.search(VALID_IBAN, null);});
	}
	
	@Test
	public void givenInvalidSortSearchShouldThrowException() {
		Assertions.assertThrows(InvalidParameterException.class, () -> {this.service.search(VALID_IBAN, INVALID_SORT);});
	}
	
	
}
