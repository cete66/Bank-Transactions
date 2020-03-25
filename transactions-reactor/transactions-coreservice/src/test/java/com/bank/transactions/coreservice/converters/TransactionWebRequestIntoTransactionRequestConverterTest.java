package com.bank.transactions.coreservice.converters;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import com.bank.transactions.coreservice.domain.TransactionRequest;
import com.bank.transactions.request.TransactionWebRequest;

public class TransactionWebRequestIntoTransactionRequestConverterTest {

	private static final String IBAN = "iban";
	private static final BigDecimal AMOUNT = BigDecimal.ONE;
	private static final LocalDateTime DATE = LocalDateTime.now();
	private static final String DESC = "desc";
	private static final BigDecimal FEE = BigDecimal.ZERO;
	private static final String REF = "ref";
	private final TransactionWebRequest toConvert = initToConvert();
	private final TransactionWebRequestIntoTransactionRequestConverter converter = new TransactionWebRequestIntoTransactionRequestConverter();
	
	@Test
	public void shouldReturnNullWhenNullParameter() {
		assertNull(converter.convert(null));
	}

	@Test
	public void givenValidWebRequestShouldConvertProperly() {
		
		TransactionRequest actual = converter.convert(toConvert);
		
		TransactionRequest expected = initExpected();
		
		MatcherAssert.assertThat(actual, Matchers.is(expected));
	}
	
	private TransactionRequest initExpected() {
		return TransactionRequest.builder()
				.withAccount_iban(IBAN)
				.withAmount(AMOUNT)
				.withDate(DATE)
				.withDescription(DESC)
				.withFee(FEE)
				.withReference(REF)
				.build();
	}

	private TransactionWebRequest initToConvert() {
		return TransactionWebRequest.builder()
				.withAccount_iban(IBAN)
				.withAmount(AMOUNT)
				.withDate(DATE)
				.withDescription(DESC)
				.withFee(FEE)
				.withReference(REF)
				.build();
	}
	
}
