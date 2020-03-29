package com.bank.framework.domain;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SortOrderTest {

	
	@Test
	public void givenValidAscSortOrderShouldConvertProperly() {
		MatcherAssert.assertThat(SortOrder.fromString("ASC"), Matchers.is(SortOrder.ASC));
	}
	
	@Test
	public void givenValidDescSortOrderShouldConvertProperly() {
		MatcherAssert.assertThat(SortOrder.fromString("DESC"), Matchers.is(SortOrder.DESC));
	}
	
	@Test
	public void givenInvalidStringSortOrderShouldThrowException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {SortOrder.fromString("asd");});
	}
}
