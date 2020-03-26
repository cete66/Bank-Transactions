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

public class TransactionCheckStatusRuleDTest {

	private String reference = "ref";
	private RuleBook<TransactionForStatusRule> ruleCLIENT;
	private RuleBook<TransactionForStatusRule> ruleATM;
	private TransactionForStatusRule transactionForStatusRuleCLIENT;
	private TransactionForStatusRule transactionForStatusRuleATM;
	private TransactionForStatusRule transactionForStatusRuleBase;
	private FactMap<TransactionForStatusRule> factMapCLIENT = new FactMap<>();
	private FactMap<TransactionForStatusRule> factMapATM = new FactMap<>();
	private TransactionForStatusRule resultCLIENT;
	private TransactionForStatusRule resultATM;
	
	
	@Given("^A transaction that is stored in our system$")
	public void a_transaction_that_is_not_stored_in_our_system_D() throws Throwable {
		transactionForStatusRuleBase = TransactionForStatusRule.builder().withReference(reference).build();
		transactionForStatusRuleCLIENT = transactionForStatusRuleBase.clonebuilder().build();
		factMapCLIENT.put(new Fact<TransactionForStatusRule>("transaction",transactionForStatusRuleCLIENT));
		transactionForStatusRuleATM = transactionForStatusRuleBase.clonebuilder().build();
		factMapATM.put(new Fact<TransactionForStatusRule>("transaction",transactionForStatusRuleATM));
	}

	@When("I check the status from \"([^\"]*)\" or \"([^\"]*)\" channel")
	public void i_check_the_status_from_any_channel_D(String client, String atm) throws Throwable {
		ruleCLIENT = new TransactionCheckStatusRule().defineRules();
		ruleATM = new TransactionCheckStatusRule().defineRules();
		transactionForStatusRuleBase = transactionForStatusRuleBase.clonebuilder()
				.withDate(LocalDateTime.now())
				.withChannel(Channel.fromString(client)).build();
		transactionForStatusRuleCLIENT = transactionForStatusRuleBase.clonebuilder().build();
		transactionForStatusRuleATM = transactionForStatusRuleBase.clonebuilder().build();
		factMapCLIENT.put(new Fact<TransactionForStatusRule>("transaction",transactionForStatusRuleCLIENT));
		ruleCLIENT.run(factMapCLIENT);
		resultCLIENT = (TransactionForStatusRule) ruleCLIENT.getResult().get().getValue();
		factMapATM.put(new Fact<TransactionForStatusRule>("transaction",transactionForStatusRuleATM));
		ruleATM.run(factMapATM);
		resultATM = (TransactionForStatusRule) ruleATM.getResult().get().getValue();
	}
	
	@And("the transaction date is equals to today")
	public void the_transaction_date_is_before_today_D() {
		Assertions.assertTrue(transactionForStatusRuleCLIENT.getDate().toLocalDate().isEqual(LocalDate.now()));
		Assertions.assertTrue(transactionForStatusRuleATM.getDate().toLocalDate().isEqual(LocalDate.now()));
	}

	@Then("The system returns the status {string}")
	public void the_system_returns_the_status_D(String string) throws Throwable {
		MatcherAssert.assertThat(resultCLIENT.getStatus(), Matchers.is(Status.fromString(string)));
		MatcherAssert.assertThat(resultATM.getStatus(), Matchers.is(Status.fromString(string)));
	}
	
	@And("the amount substracting the fee")
	public void amount_substracting_fee_D() {
		MatcherAssert.assertThat(resultCLIENT.getAmount(), 
				Matchers.is(transactionForStatusRuleBase.getAmount()
						.subtract(transactionForStatusRuleBase.getFee()!=null ? 
								transactionForStatusRuleBase.getFee() : BigDecimal.ZERO)));
		MatcherAssert.assertThat(resultATM.getAmount(), 
				Matchers.is(transactionForStatusRuleBase.getAmount()
						.subtract(transactionForStatusRuleBase.getFee()!=null ? 
								transactionForStatusRuleBase.getFee() : BigDecimal.ZERO)));
	}
}
