package com.bank.transactions.coreservice.converters;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import com.bank.framework.domain.Status;
import com.bank.transactions.coreservice.domain.TransactionRequest;
import com.bank.transactions.coreservice.repository.entities.TransactionEntity;

public class TransactionRequestIntoTransactionEntityConverterTest {

	private static final BigDecimal AMOUNT = BigDecimal.ONE;
	private static final BigDecimal FEE = BigDecimal.ZERO;
	private static final String REF = "ref";
	private static final Status STATUS = Status.INVALID;
	private static final String IBAN = "iban";
	private static final LocalDateTime DATE = LocalDateTime.now();
	private static final String DESC = "desc";
	private final TransactionRequest toConvert = initToConvert();
	private final TransactionRequestIntoTransactionEntityConverter converter = new TransactionRequestIntoTransactionEntityConverter();
	
	@Test
	public void shouldReturnNullWhenNullParameter() {
		assertNull(converter.convert(null));
	}

	@Test
	public void givenValidWebRequestShouldConvertProperly() {
		
		TransactionEntity actual = converter.convert(toConvert);
		
		TransactionEntity expected = initExpected();
		
		MatcherAssert.assertThat(actual, Matchers.is(expected));
	}
	
	private TransactionEntity initExpected() {
		return TransactionEntity.builder()
				.withAmount(AMOUNT)
				.withFee(FEE)
				.withReference(REF)
				.withStatus(STATUS.getCode())
				.withAccount_iban(IBAN)
				.withDate(DATE)
				.withDescription(DESC)
				.build();
	}

	private TransactionRequest initToConvert() {
		return TransactionRequest.builder()
				.withAmount(AMOUNT)
				.withFee(FEE)
				.withReference(REF)
				.withStatus(STATUS)
				.withAccount_iban(IBAN)
				.withDate(DATE)
				.withDescription(DESC)
				.build();
	}
}
