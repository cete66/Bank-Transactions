package com.bank.transactions.rs;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import com.bank.framework.domain.Channel;
import com.bank.framework.domain.Status;
import com.bank.framework.persistence.exceptions.TransactionNotAllowedException;
import com.bank.transactions.coreservice.TransactionManager;
import com.bank.transactions.request.TransactionStatusWebRequest;
import com.bank.transactions.request.TransactionStatusWebRequest.TransactionStatusWebRequestBuilder;
import com.bank.transactions.request.TransactionWebRequest;
import com.bank.transactions.request.TransactionWebRequest.TransactionWebRequestBuilder;
import com.bank.transactions.response.TransactionStatusWebResponse;
import com.bank.transactions.response.TransactionWebResponse;
import com.bank.transactions.response.TransactionWebResponse.TransactionWebResponseBuilder;

@TestPropertySource(locations = "/application.properties")
public class TransactionControllerTest {

	private static final String VALID_IBAN = "ES999999999999999999";
	private static final String DESC = "desc";
	private static final String REFERENCE = "asoiidh398hsadjsdikn";
	@Value("${com.bank.transactions.coreservice.default.sort.ASC}") 
	private String sortASC;
	@Value("${com.bank.transactions.coreservice.default.sort.DESC}") 
	private String sortDESC;
	@InjectMocks
	private TransactionsController controller;
	@Mock
	private TransactionManager transactionManager;
	private final TransactionWebResponseBuilder responseBuilder = TransactionWebResponse.builder()
															.withAccount_iban(VALID_IBAN)
															.withDate(LocalDateTime.now())
															.withStatus(Status.PENDING.getCode());
	private final TransactionWebRequestBuilder requestBuilder = TransactionWebRequest.builder()
															.withAccount_iban(VALID_IBAN)
															.withAmount(BigDecimal.TEN)
															.withDate(LocalDateTime.now())
															.withDescription(DESC)
															.withFee(BigDecimal.ONE)
															.withReference(UUID.randomUUID().toString());
	private final TransactionStatusWebRequestBuilder statusRequestBuilder = TransactionStatusWebRequest.builder()
																		.withChannel(Channel.CLIENT)
																		.withReference(REFERENCE);
															
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void givenValidIbanAndValidSortAscShouldSearchReturnOk() {
		
		List<TransactionWebResponse> expected = Arrays.asList(
				responseBuilder.build().cloneBuilder().withAmount(BigDecimal.valueOf(500)).build(),
				responseBuilder.build().cloneBuilder().withAmount(BigDecimal.valueOf(1000)).build());
		
		Mockito.doReturn(expected).when(transactionManager).search(ArgumentMatchers.eq(VALID_IBAN), ArgumentMatchers.eq(sortASC));
		ResponseEntity<List<TransactionWebResponse>> entity = this.controller.search(VALID_IBAN, sortASC);
		List<TransactionWebResponse> actual = this.controller.search(VALID_IBAN, sortASC).getBody();
		
		MatcherAssert.assertThat(entity.getStatusCode(), Matchers.is(HttpStatus.OK));
		MatcherAssert.assertThat(actual, Matchers.contains(expected.get(0), expected.get(1)));
	}
	
	@Test
	public void givenValidRequestShouldCreateReturnOk() {
		final TransactionWebRequest request = requestBuilder.build();
		final TransactionWebResponse expected = responseBuilder
											.withAmount(request.getAmount().subtract(request.getFee()))
											.withDate(request.getDate())
											.withReference(request.getReference())
											.withDescription(request.getDescription())
											.build();
		Mockito.doReturn(expected).when(transactionManager).create(ArgumentMatchers.eq(request));
		ResponseEntity<TransactionWebResponse> entity = this.controller.create(request);
		final TransactionWebResponse actual = entity.getBody();
		MatcherAssert.assertThat(entity.getStatusCode(), Matchers.is(HttpStatus.OK));
		MatcherAssert.assertThat(actual, Matchers.is(expected));
	}
	
	@Test
	public void givenInvalidRequestShouldCreateReturnBadRequest() {
		final TransactionWebRequest request = requestBuilder.build().cloneBuilder().withAccount_iban(null).withAmount(null).build();
		Mockito.doThrow(TransactionNotAllowedException.class).when(transactionManager).create(ArgumentMatchers.eq(request));
		
		try {
			this.controller.create(request);
		} catch (TransactionNotAllowedException e) {
			MatcherAssert.assertThat(e, Matchers.any(TransactionNotAllowedException.class));
		}
	}
	
	@Test
	public void givenValidStatusWebRequestShouldStatusReturnOk() {
		final TransactionStatusWebRequest request = statusRequestBuilder.build();
		final TransactionStatusWebResponse expected = TransactionStatusWebResponse.builder()
				.withAmount(BigDecimal.TEN.subtract(BigDecimal.ONE))
				.withStatus(Status.PENDING.getCode())
				.withReference(request.getReference())
				.build();
		Mockito.doReturn(expected).when(transactionManager).status(ArgumentMatchers.eq(request));
		ResponseEntity<TransactionStatusWebResponse> entity = this.controller.status(request);
		final TransactionStatusWebResponse actual = entity.getBody();
		MatcherAssert.assertThat(actual, Matchers.is(expected));
		MatcherAssert.assertThat(entity.getStatusCode(), Matchers.is(HttpStatus.OK));
		
	}
	
	@Test
	public void givenNullReferenceStatusWebRequestShouldStatusThrowException() {
		final TransactionStatusWebRequest request = statusRequestBuilder.build().cloneBuilder().withReference(null).build();
		Mockito.doThrow(InvalidParameterException.class).when(transactionManager).status(ArgumentMatchers.eq(request));
		
		try {
			this.controller.status(request);
		} catch (InvalidParameterException e) {
			MatcherAssert.assertThat(e, Matchers.any(InvalidParameterException.class));
		}
	}
	
	@Test
	public void givenInexistentReferenceStatusWebRequestShouldStatusReturnRuleA() {
		final TransactionStatusWebRequest request = statusRequestBuilder.build().cloneBuilder().withReference("inexistent").build();
		final TransactionStatusWebResponse expected = TransactionStatusWebResponse.builder()
				.withStatus(Status.INVALID.getCode())
				.build();
		Mockito.doReturn(expected).when(transactionManager).status(ArgumentMatchers.eq(request));
		ResponseEntity<TransactionStatusWebResponse> entity = this.controller.status(request);
		final TransactionStatusWebResponse actual = entity.getBody();
		MatcherAssert.assertThat(actual, Matchers.is(expected));
		MatcherAssert.assertThat(entity.getStatusCode(), Matchers.is(HttpStatus.OK));
	}
	
	@Test
	public void givenValidIbanAndNullSortAscShouldSearchThrowException() {
		try {
			this.controller.search(VALID_IBAN, null);
		} catch (InvalidParameterException e) {
			MatcherAssert.assertThat(e, Matchers.any(InvalidParameterException.class));
		}
	}
	
	
}
