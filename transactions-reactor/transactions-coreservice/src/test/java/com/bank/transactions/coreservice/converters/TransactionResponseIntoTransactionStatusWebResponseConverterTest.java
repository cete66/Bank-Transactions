package com.bank.transactions.coreservice.converters;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import com.bank.framework.domain.Status;
import com.bank.transactions.coreservice.domain.TransactionResponse;
import com.bank.transactions.response.TransactionStatusWebResponse;

public class TransactionResponseIntoTransactionStatusWebResponseConverterTest {

	private static final BigDecimal AMOUNT = BigDecimal.ONE;
	private static final BigDecimal FEE = BigDecimal.ZERO;
	private static final String REF = "ref";
	private static final String STATUS = Status.INVALID.getCode();
	private static final String IBAN = "iban";
	private static final LocalDateTime DATE = LocalDateTime.now();
	private static final String DESC = "desc";
	private final TransactionResponse toConvert = initToConvert();
	private final TransactionResponseIntoTransactionStatusWebResponseConverter converter 
	= new TransactionResponseIntoTransactionStatusWebResponseConverter();
	
	@Test
	public void shouldReturnNullWhenNullParameter() {
		assertNull(converter.convert(null));
	}

	@Test
	public void givenValidWebRequestShouldConvertProperly() {
		
		TransactionStatusWebResponse actual = converter.convert(toConvert);
		
		TransactionStatusWebResponse expected = initExpected();
		
		MatcherAssert.assertThat(actual, Matchers.is(expected));
	}
	
	private TransactionStatusWebResponse initExpected() {
		return TransactionStatusWebResponse.builder()
				.withAmount(AMOUNT)
				.withFee(FEE)
				.withReference(REF)
				.withStatus(STATUS)
				.build();
	}

	private TransactionResponse initToConvert() {
		return TransactionResponse.builder()
				.withAmount(AMOUNT)
				.withFee(FEE)
				.withReference(REF)
				.withStatus(Status.valueOf(STATUS))
				.withAccount_iban(IBAN)
				.withDate(DATE)
				.withDescription(DESC)
				.build();
	}
}
