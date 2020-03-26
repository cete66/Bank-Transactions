package com.bank.transactions.coreservice.steps;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import com.bank.framework.domain.Status;
import com.bank.transactions.coreservice.domain.TransactionForStatusRule;
import com.bank.transactions.coreservice.rule.TransactionCheckStatusRule;
import com.deliveredtechnologies.rulebook.Fact;
import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.model.RuleBook;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TransactionCheckStatusRuleATest extends BasicStep{

	private String reference = null;
	private RuleBook<TransactionForStatusRule> rule;
	private TransactionForStatusRule transactionForStatusRule;
	private FactMap<TransactionForStatusRule> factMap = new FactMap<>();
	private TransactionForStatusRule result;
	
	
	@Given("^A transaction that is not stored in our system$")
	public void a_transaction_that_is_not_stored_in_our_system_A() throws Throwable {
		reference = null;
		transactionForStatusRule = TransactionForStatusRule.builder().withReference(reference).build();
		factMap.put(new Fact<TransactionForStatusRule>("transaction",transactionForStatusRule));
	}

	@When("I check the status from any channel")
	public void i_check_the_status_from_any_channel_A() throws Throwable {
		rule = new TransactionCheckStatusRule().defineRules();
		rule.run(factMap);
		result = (TransactionForStatusRule) rule.getResult().get().getValue();
	}

	@Then("The system returns the status {string}")
	public void the_system_returns_the_status_A(String string) throws Throwable {
		MatcherAssert.assertThat(result.getStatus(), Matchers.is(Status.fromString(string)));
	}
}
