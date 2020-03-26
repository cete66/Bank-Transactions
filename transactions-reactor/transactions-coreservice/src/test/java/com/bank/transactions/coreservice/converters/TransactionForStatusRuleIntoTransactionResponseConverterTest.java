package com.bank.transactions.coreservice.converters;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import com.bank.framework.domain.Status;
import com.bank.transactions.coreservice.domain.TransactionForStatusRule;
import com.bank.transactions.coreservice.domain.TransactionResponse;

public class TransactionForStatusRuleIntoTransactionResponseConverterTest {

	private static final BigDecimal AMOUNT = BigDecimal.ONE;
	private static final BigDecimal FEE = BigDecimal.ZERO;
	private static final String REF = "ref";
	private static final Status STATUS = Status.INVALID;
	private static final String IBAN = "iban";
	private static final LocalDateTime DATE = LocalDateTime.now();
	private static final String DESC = "desc";
	private final TransactionForStatusRule toConvert = initToConvert();
	private final TransactionForStatusRuleIntoTransactionResponseConverter converter = new TransactionForStatusRuleIntoTransactionResponseConverter();
	
	@Test
	public void shouldReturnNullWhenNullParameter() {
		assertNull(converter.convert(null));
	}

	@Test
	public void givenValidWebRequestShouldConvertProperly() {
		
		TransactionResponse actual = converter.convert(toConvert);
		
		TransactionResponse expected = initExpected();
		
		MatcherAssert.assertThat(actual, Matchers.is(expected));
	}
	
	private TransactionResponse initExpected() {
		return TransactionResponse.builder()
				.withAmount(AMOUNT)
				.withFee(FEE)
				.withReference(REF)
				.withStatus(STATUS)
				.withAccount_iban(IBAN)
				.withDate(DATE)
				.withDescription(DESC)
				.build();
	}

	private TransactionForStatusRule initToConvert() {
		return TransactionForStatusRule.builder()
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
