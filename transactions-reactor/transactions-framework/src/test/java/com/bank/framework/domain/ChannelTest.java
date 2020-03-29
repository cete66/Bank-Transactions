package com.bank.framework.domain;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ChannelTest {

	@Test
	public void givenValidClientChannelShouldConvertProperly() {
		MatcherAssert.assertThat(Channel.fromString("CLIENT"), Matchers.is(Channel.CLIENT));
	}
	
	@Test
	public void givenValidAtmChannelShouldConvertProperly() {
		MatcherAssert.assertThat(Channel.fromString("ATM"), Matchers.is(Channel.ATM));
	}
	
	@Test
	public void givenValidInternalChannelShouldConvertProperly() {
		MatcherAssert.assertThat(Channel.fromString("INTERNAL"), Matchers.is(Channel.INTERNAL));
	}
	
	@Test
	public void givenInvalidStringChannelShouldThrowException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {Channel.fromString("asd");});
	}
}
