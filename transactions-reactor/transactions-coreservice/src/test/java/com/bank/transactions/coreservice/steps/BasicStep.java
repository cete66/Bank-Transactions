package com.bank.transactions.coreservice.steps;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import com.bank.framework.domain.Status;
import com.bank.transactions.coreservice.domain.TransactionForStatusRule;

import io.cucumber.java.en.Then;

public class BasicStep {

	
	@Then("The system returns the status {string}")
	public void the_system_returns_the_status(String string, TransactionForStatusRule... result) throws Throwable {
		for (int i = 0; i < result.length; i++) {
			MatcherAssert.assertThat(result[i].getStatus(), Matchers.is(Status.fromString(string)));
		}
	}
}
