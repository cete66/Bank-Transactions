package com.bank.transactions.coreservice.converters;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import com.bank.framework.domain.Channel;
import com.bank.framework.domain.Status;
import com.bank.transactions.coreservice.domain.TransactionForStatusRule;
import com.bank.transactions.coreservice.domain.TransactionResponse;

public class TransactionResponseIntoTransactionForStatusRuleConverterTest {

	private static final BigDecimal AMOUNT = BigDecimal.ONE;
	private static final BigDecimal FEE = BigDecimal.ZERO;
	private static final String REF = "ref";
	private static final Status STATUS = Status.INVALID;
	private static final String IBAN = "iban";
	private static final LocalDateTime DATE = LocalDateTime.now();
	private static final String DESC = "desc";
	private static final Channel CHANNEL = Channel.ATM;
	private final TransactionResponse toConvert = initToConvert();
	private final TransactionResponseIntoTransactionForStatusRuleConverter converter = new TransactionResponseIntoTransactionForStatusRuleConverter();
	
	@Test
	public void shouldReturnNullWhenNullParameter() {
		assertNull(converter.convert(null));
	}

	@Test
	public void givenValidWebRequestShouldConvertProperly() {
		
		TransactionForStatusRule actual = converter.convert(toConvert);
		
		TransactionForStatusRule expected = initExpected();
		
		MatcherAssert.assertThat(actual, Matchers.is(expected));
	}
	
	private TransactionForStatusRule initExpected() {
		return TransactionForStatusRule.builder()
				.withAmount(AMOUNT)
				.withFee(FEE)
				.withReference(REF)
				.withStatus(STATUS)
				.withAccount_iban(IBAN)
				.withDate(DATE)
				.withDescription(DESC)
				.withChannel(CHANNEL)
				.build();
	}

	private TransactionResponse initToConvert() {
		return TransactionResponse.builder()
				.withAmount(AMOUNT)
				.withFee(FEE)
				.withReference(REF)
				.withStatus(STATUS)
				.withAccount_iban(IBAN)
				.withDate(DATE)
				.withDescription(DESC)
				.withChannel(CHANNEL)
				.build();
	}
}
