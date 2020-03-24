package com.bank.transactions.converters;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import com.bank.framework.domain.Status;
import com.bank.transactions.coreservice.domain.TransactionRequest;
import com.bank.transactions.response.TransactionWebResponse;

public class TransactionRequestIntoTransactionWebResponseConverterTest {
	
	private static final BigDecimal AMOUNT = BigDecimal.ONE;
	private static final BigDecimal FEE = BigDecimal.ZERO;
	private static final String REF = "ref";
	private static final Status STATUS = Status.INVALID;
	private final TransactionRequest toConvert = initToConvert();
	private final TransactionRequestIntoTransactionWebResponseConverter converter = new TransactionRequestIntoTransactionWebResponseConverter();
	
	@Test
	public void shouldReturnNullWhenNullParameter() {
		assertNull(converter.convert(null));
	}

	@Test
	public void givenValidWebRequestShouldConvertProperly() {
		
		TransactionWebResponse actual = converter.convert(toConvert);
		
		TransactionWebResponse expected = initExpected();
		
		MatcherAssert.assertThat(actual, Matchers.is(expected));
	}
	
	private TransactionWebResponse initExpected() {
		return TransactionWebResponse.builder()
				.withAmount(AMOUNT)
				.withFee(FEE)
				.withReference(REF)
				.withStatus(STATUS)
				.build();
	}

	private TransactionRequest initToConvert() {
		return TransactionRequest.builder()
				.withAmount(AMOUNT)
				.withFee(FEE)
				.withReference(REF)
				.withStatus(STATUS)
				.build();
	}

}
