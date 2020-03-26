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

public class TransactionCheckStatusRuleCTest {

	private String reference = "ref";
	private RuleBook<TransactionForStatusRule> rule;
	private TransactionForStatusRule transactionForStatusRule;
	private FactMap<TransactionForStatusRule> factMap = new FactMap<>();
	private TransactionForStatusRule result;
	
	
	@Given("^A transaction that is stored in our system$")
	public void a_transaction_that_is_not_stored_in_our_system_C() throws Throwable {
		reference = null;
		transactionForStatusRule = TransactionForStatusRule.builder()
				.withReference(reference)
				.withChannel(Channel.INTERNAL)
				.withAmount(BigDecimal.TEN)
				.withFee(BigDecimal.ONE)
				.withDate(LocalDateTime.now().minusDays(1))
				.build();
		factMap.put(new Fact<TransactionForStatusRule>("transaction",transactionForStatusRule));
	}

	@When("I check the status from {string} channel")
	public void i_check_the_status_from_any_channel_C(String string) throws Throwable {
		rule = new TransactionCheckStatusRule().defineRules();
		rule.run(factMap);
		result = (TransactionForStatusRule) rule.getResult().get().getValue();
	}
	
	@And("the transaction date is before today")
	public void date_is_before_today_C() {
		Assertions.assertTrue(result.getDate().toLocalDate().isBefore(LocalDate.now()));
	}

	@Then("The system returns the status {string}")
	public void the_system_returns_the_status_C(String string) throws Throwable {
		MatcherAssert.assertThat(result.getStatus(), Matchers.is(Status.fromString(string)));
	}
	
	@And("the amount")
	public void amount_C() {
		Assertions.assertNotNull(result.getAmount());
	}
	
	@And("the fee")
	public void fee_C() {
		Assertions.assertNotNull(result.getFee());
	}
}
