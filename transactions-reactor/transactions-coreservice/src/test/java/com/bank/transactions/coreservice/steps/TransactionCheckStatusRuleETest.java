package com.bank.transactions.coreservice.steps;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;

import com.bank.framework.domain.Channel;
import com.bank.framework.domain.Status;
import com.bank.transactions.coreservice.domain.TransactionForStatusRule;
import com.bank.transactions.coreservice.rule.TransactionCheckStatusRule;
import com.deliveredtechnologies.rulebook.Fact;
import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.model.RuleBook;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TransactionCheckStatusRuleETest {

	private String reference = "ref";
	private RuleBook<TransactionForStatusRule> rule;
	private TransactionForStatusRule transactionForStatusRule;
	private FactMap<TransactionForStatusRule> factMap = new FactMap<>();
	private TransactionForStatusRule result;
	
	
	@Given("^A transaction that is stored in our system$")
	public void a_transaction_that_is_not_stored_in_our_system_E() throws Throwable {
		reference = null;
		transactionForStatusRule = TransactionForStatusRule.builder()
				.withReference(reference)
				.withChannel(Channel.INTERNAL)
				.withAmount(BigDecimal.TEN)
				.withFee(BigDecimal.ONE)
				.withDate(LocalDateTime.now())
				.build();
		factMap.put(new Fact<TransactionForStatusRule>("transaction",transactionForStatusRule));
	}

	@When("I check the status from {string} channel")
	public void i_check_the_status_from_any_channel_E(String string) throws Throwable {
		rule = new TransactionCheckStatusRule().defineRules();
		rule.run(factMap);
		result = (TransactionForStatusRule) rule.getResult().get().getValue();
	}
	
	@And("the transaction date is equals to today")
	public void transaction_date_equals_today_E() {
		Assertions.assertTrue(result.getDate().toLocalDate().isEqual(LocalDate.now()));
	}

	@Then("The system returns the status {string}")
	public void the_system_returns_the_status_E(String string) throws Throwable {
		MatcherAssert.assertThat(result.getStatus(), Matchers.is(Status.fromString(string)));
	}
	
	@And("the amount")
	public void amount_E() {
		Assertions.assertNotNull(result.getAmount());
	}
	
	@And("the fee")
	public void fee_E() {
		Assertions.assertNotNull(result.getFee());
	}
}
