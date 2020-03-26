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

public class TransactionCheckStatusRuleTest {

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
		transactionForStatusRule = transactionForStatusRule.clonebuilder().withChannel(Channel.ATM).build();
		rule = new TransactionCheckStatusRule().defineRules();
		rule.run(factMap);
		result = (TransactionForStatusRule) rule.getResult().get().getValue();
	}
	
	@Given("^A transaction that is stored in our system$")
	public void a_transaction_that_is_not_stored_in_our_system() throws Throwable {
		reference = "ref";
		transactionForStatusRule = TransactionForStatusRule.builder().withReference(reference).build();
	}

	@When("I check the status from {string} channel")
	public void i_check_the_status_from_string_channel(String string) throws Throwable {
		transactionForStatusRule = TransactionForStatusRule.builder()
				.withReference(reference)
				.withChannel(Channel.valueOf(string))
				.withAmount(BigDecimal.TEN)
				.withFee(BigDecimal.ONE)
				.withDate(LocalDateTime.now())
				.build();
	}
	
	@And("the transaction date is before today")
	public void date_is_before_today() {
		transactionForStatusRule = transactionForStatusRule.clonebuilder().withDate(LocalDateTime.now().minusDays(1)).build();
		Assertions.assertTrue(transactionForStatusRule.getDate().toLocalDate().isBefore(LocalDate.now()));
	}
	
	@And("the transaction date is equals to today")
	public void the_transaction_date_is_before_today_D() {
		transactionForStatusRule = transactionForStatusRule.clonebuilder().withDate(LocalDateTime.now()).build();
		Assertions.assertTrue(transactionForStatusRule.getDate().toLocalDate().isEqual(LocalDate.now()));
	}
	
	@And("the transaction date is greater than today")
	public void transaction_date_greater_than_today_F() {
		transactionForStatusRule = transactionForStatusRule.clonebuilder().withDate(LocalDateTime.now().plusDays(1)).build();
		Assertions.assertTrue(transactionForStatusRule.getDate().toLocalDate().isAfter(LocalDate.now()));
	}

	@Then("The system returns the status {string}")
	public void the_system_returns_the_status(String string) throws Throwable {
		factMap.put(new Fact<TransactionForStatusRule>("transaction",transactionForStatusRule));
		rule = new TransactionCheckStatusRule().defineRules();
		rule.run(factMap);
		result = (TransactionForStatusRule) rule.getResult().get().getValue();
		MatcherAssert.assertThat(result.getStatus(), Matchers.is(Status.valueOf(string)));
	}
	
	@And("the amount")
	public void amount() {
		Assertions.assertNotNull(result.getAmount());
	}
	
	@And("the amount substracting the fee")
	public void amount_substracting_fee_D() {
		MatcherAssert.assertThat(result.getAmount(), 
				Matchers.is(transactionForStatusRule.getAmount()
						.subtract(transactionForStatusRule.getFee()!=null ? 
								transactionForStatusRule.getFee() : BigDecimal.ZERO)));
	}
	
	@And("the fee")
	public void fee_C() {
		Assertions.assertNotNull(result.getFee());
	}
}
