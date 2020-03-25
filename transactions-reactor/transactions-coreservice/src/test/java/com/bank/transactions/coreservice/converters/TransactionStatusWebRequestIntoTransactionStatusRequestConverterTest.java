package com.bank.transactions.coreservice.converters;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import com.bank.framework.domain.Channel;
import com.bank.transactions.coreservice.domain.TransactionStatusRequest;
import com.bank.transactions.request.TransactionStatusWebRequest;

public class TransactionStatusWebRequestIntoTransactionStatusRequestConverterTest {
	
	private static final Channel CHANNEL = Channel.ATM;
	private static final String REF = "ref";
	private final TransactionStatusWebRequest toConvert = initToConvert();
	private final TransactionStatusWebRequestIntoTransactionStatusRequestConverter converter = new TransactionStatusWebRequestIntoTransactionStatusRequestConverter();
	
	@Test
	public void shouldReturnNullWhenNullParameter() {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void givenValidWebRequestShouldConvertProperly() {
		
		TransactionStatusRequest actual = converter.convert(toConvert);
		
		TransactionStatusRequest expected = initExpected();
		
		MatcherAssert.assertThat(actual, Matchers.is(expected));
	}

	private TransactionStatusRequest initExpected() {
		return TransactionStatusRequest.builder()
				.withChannel(CHANNEL)
				.withReference(REF)
				.build();
	}

	private TransactionStatusWebRequest initToConvert() {
		return TransactionStatusWebRequest.builder()
				.withChannel(CHANNEL)
				.withReference(REF)
				.build();
	}

}
