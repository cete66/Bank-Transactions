package com.bank.framework.domain;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StatusTest {

	
	@Test
	public void givenValidFutureStatusShouldConvertProperly() {
		MatcherAssert.assertThat(Status.fromString("FUTURE"), Matchers.is(Status.FUTURE));
	}
	
	@Test
	public void givenValidInvalidStatusShouldConvertProperly() {
		MatcherAssert.assertThat(Status.fromString("INVALID"), Matchers.is(Status.INVALID));
	}
	
	@Test
	public void givenValidPendingStatusShouldConvertProperly() {
		MatcherAssert.assertThat(Status.fromString("PENDING"), Matchers.is(Status.PENDING));
	}
	
	@Test
	public void givenValidSettledStatusShouldConvertProperly() {
		MatcherAssert.assertThat(Status.fromString("SETTLED"), Matchers.is(Status.SETTLED));
	}
	
	@Test
	public void givenInvalidStringChannelShouldThrowException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {Channel.fromString("asd");});
	}
}
