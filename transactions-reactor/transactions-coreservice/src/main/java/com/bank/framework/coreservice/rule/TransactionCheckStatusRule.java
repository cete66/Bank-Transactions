package com.bank.framework.coreservice.rule;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.bank.framework.domain.Channel;
import com.bank.framework.domain.Status;
import com.bank.transactions.coreservice.domain.TransactionForStatusRule;
import com.deliveredtechnologies.rulebook.NameValueReferableTypeConvertibleMap;
import com.deliveredtechnologies.rulebook.Result;
import com.deliveredtechnologies.rulebook.annotation.Rule;
import com.deliveredtechnologies.rulebook.lang.RuleBookBuilder;
import com.deliveredtechnologies.rulebook.model.RuleBook;

@Rule
public class TransactionCheckStatusRule {

	
	public RuleBook<Object> defineRules() {

		return RuleBookBuilder.create()
				/**
				 * RULE A
				 *  Given: A transaction that is not stored in our system
					When: I check the status from any channel
					Then: The system returns the status 'INVALID'
				 */
				.addRule(rule -> rule.withFactType(TransactionForStatusRule.class)
						.when(f -> f.getOne().getReference() == null)
						.then((f, result) -> thenOperationForRuleA(f, result)))
				
				/**
				 * RULE B
				 * Given: A transaction that is stored in our system
					When: I check the status from CLIENT or ATM channel
					And the transaction date is before today
					Then: The system returns the status 'SETTLED'
					And the amount substracting the fee
				 */
				.addRule(rule -> rule.withFactType(TransactionForStatusRule.class)
						.when(f -> whenConditionForRuleB(f))
						.then((f, result) -> thenOperationforRuleB(f, result)))
				
				/**
				 * RULE C
				 * Given: A transaction that is stored in our system
					When: I check the status from INTERNAL channel
					And the transaction date is before today
					Then: The system returns the status 'SETTLED'
					And the amount
					And the fee
				 */
				.addRule(rule -> rule.withFactType(TransactionForStatusRule.class)
						.when(f -> whenConditionForRuleC(f))
						.then((f, result) -> thenOperationforRuleC(f, result)))
				
				/**
				 * RULE D
				 * Given: A transaction that is stored in our system
					When: I check the status from CLIENT or ATM channel
					And the transaction date is equals to today
					Then: The system returns the status 'PENDING'
					And the amount substracting the fee
				 */
				.addRule(rule -> rule.withFactType(TransactionForStatusRule.class)
						.when(f -> whenConditionForRuleD(f))
						.then((f, result) -> thenOperationforRuleD(f, result)))
				
				/**
				 * RULE E
				 * Given: A transaction that is stored in our system
					When: I check the status from INTERNAL channel
					And the transaction date is equals to today
					Then: The system returns the status 'PENDING'
					And the amount
					And the fee
				 */
				.addRule(rule -> rule.withFactType(TransactionForStatusRule.class)
						.when(f -> whenConditionForRuleE(f))
						.then((f, result) -> thenOperationforRuleE(f, result)))
				
				/**
				 * RULE F
				 * Given: A transaction that is stored in our system
					When: I check the status from CLIENT channel
					And the transaction date is greater than today
					Then: The system returns the status 'FUTURE'
					And the amount substracting the fee
				 */
				.addRule(rule -> rule.withFactType(TransactionForStatusRule.class)
						.when(f -> whenConditionForRuleF(f))
						.then((f, result) -> thenOperationforRuleF(f, result)))
				
				/**
				 * RULE G
				 * Given: A transaction that is stored in our system
					When: I check the status from ATM channel
					And the transaction date is greater than today
					Then: The system returns the status 'PENDING'
					And the amount substracting the fee
				 */
				.addRule(rule -> rule.withFactType(TransactionForStatusRule.class)
						.when(f -> whenConditionForRuleG(f))
						.then((f, result) -> thenOperationforRuleG(f, result)))
				
				/**
				 * RULE H
				 * Given: A transaction that is stored in our system
					When: I check the status from INTERNAL channel
					And the transaction date is greater than today
					Then: The system returns the status 'FUTURE'
					And the amount
					And the fee
				 */
				.addRule(rule -> rule.withFactType(TransactionForStatusRule.class)
						.when(f -> whenConditionForRuleH(f))
						.then((f, result) -> thenOperationforRuleH(f, result)))
				
				.build();
	}
	
	private void thenOperationforRuleH(NameValueReferableTypeConvertibleMap<TransactionForStatusRule> f,
			Result<Object> result) {
		result.setValue(TransactionForStatusRule.builder()
				.withStatus(Status.FUTURE)
				.withReference(f.getOne().getReference())
				.withAmount(f.getOne().getAmount())
				.withFee(f.getOne().getFee())
				.build());
	}

	private boolean whenConditionForRuleH(NameValueReferableTypeConvertibleMap<TransactionForStatusRule> f) {
		return f.getOne().getReference()!=null && f.getOne().getChannel()!=null
				&& f.getOne().getChannel().equals(Channel.INTERNAL)
				&& f.getOne().getDate()!=null && f.getOne().getDate().toLocalDate().isAfter(LocalDate.now());
	}

	private void thenOperationforRuleG(NameValueReferableTypeConvertibleMap<TransactionForStatusRule> f,
			Result<Object> result) {
		result.setValue(TransactionForStatusRule.builder()
				.withStatus(Status.PENDING)
				.withReference(f.getOne().getReference())
				.withAmount(f.getOne().getAmount().subtract(f.getOne().getFee()!=null ? f.getOne().getFee() : BigDecimal.ZERO)).build());
	}

	private boolean whenConditionForRuleG(NameValueReferableTypeConvertibleMap<TransactionForStatusRule> f) {
		return f.getOne().getReference()!=null && f.getOne().getChannel()!=null
				&& f.getOne().getChannel().equals(Channel.ATM)
				&& f.getOne().getDate()!=null && f.getOne().getDate().toLocalDate().isAfter(LocalDate.now());
	}

	private void thenOperationforRuleF(NameValueReferableTypeConvertibleMap<TransactionForStatusRule> f,
			Result<Object> result) {
		result.setValue(TransactionForStatusRule.builder()
				.withStatus(Status.FUTURE)
				.withReference(f.getOne().getReference())
				.withAmount(f.getOne().getAmount().subtract(f.getOne().getFee()!=null ? f.getOne().getFee() : BigDecimal.ZERO)).build());
	}

	private boolean whenConditionForRuleF(NameValueReferableTypeConvertibleMap<TransactionForStatusRule> f) {
		return f.getOne().getReference()!=null && f.getOne().getChannel()!=null
				&& f.getOne().getChannel().equals(Channel.CLIENT)
				&& f.getOne().getDate()!=null && f.getOne().getDate().toLocalDate().isAfter(LocalDate.now());
	}

	private void thenOperationforRuleE(NameValueReferableTypeConvertibleMap<TransactionForStatusRule> f,
			Result<Object> result) {
		result.setValue(TransactionForStatusRule.builder()
				.withStatus(Status.PENDING)
				.withReference(f.getOne().getReference())
				.withAmount(f.getOne().getAmount())
				.withFee(f.getOne().getFee())
				.build());
	}

	private boolean whenConditionForRuleE(NameValueReferableTypeConvertibleMap<TransactionForStatusRule> f) {
		return f.getOne().getReference()!=null && f.getOne().getChannel()!=null
				&& f.getOne().getChannel().equals(Channel.INTERNAL)
				&& f.getOne().getDate()!=null && f.getOne().getDate().toLocalDate().isEqual(LocalDate.now()); 
	}

	private void thenOperationforRuleD(NameValueReferableTypeConvertibleMap<TransactionForStatusRule> f,
			Result<Object> result) {
		result.setValue(TransactionForStatusRule.builder()
				.withStatus(Status.PENDING)
				.withReference(f.getOne().getReference())
				.withAmount(f.getOne().getAmount().subtract(f.getOne().getFee()!=null ? f.getOne().getFee() : BigDecimal.ZERO)).build());
	}

	private boolean whenConditionForRuleD(NameValueReferableTypeConvertibleMap<TransactionForStatusRule> f) {
		return f.getOne().getReference()!=null && f.getOne().getChannel()!=null
				&& (f.getOne().getChannel().equals(Channel.CLIENT) 
						|| f.getOne().getChannel().equals(Channel.ATM))
				&& f.getOne().getDate()!=null && f.getOne().getDate().toLocalDate().isEqual(LocalDate.now());
	}

	private void thenOperationforRuleC(NameValueReferableTypeConvertibleMap<TransactionForStatusRule> f,
			Result<Object> result) {
		result.setValue(TransactionForStatusRule.builder()
				.withStatus(Status.SETTLED)
				.withReference(f.getOne().getReference())
				.withAmount(f.getOne().getAmount())
				.withFee(f.getOne().getFee()).build());
	}

	private boolean whenConditionForRuleC(NameValueReferableTypeConvertibleMap<TransactionForStatusRule> f) {
		return f.getOne().getReference()!=null && f.getOne().getChannel()!=null
				&& f.getOne().getChannel().equals(Channel.INTERNAL) 
				&& f.getOne().getDate()!=null && f.getOne().getDate().toLocalDate().isBefore(LocalDate.now()); 
	}

	private void thenOperationForRuleA(NameValueReferableTypeConvertibleMap<TransactionForStatusRule> f, Result<Object> result) {
		result.setValue(TransactionForStatusRule.builder()
				.withStatus(Status.INVALID)
				.withReference(f.getOne().getReference()).build());
	}
	
	private boolean whenConditionForRuleB(NameValueReferableTypeConvertibleMap<TransactionForStatusRule> f) {
		return f.getOne().getReference()!=null && f.getOne().getChannel()!=null
				&& (f.getOne().getChannel().equals(Channel.CLIENT) 
						|| f.getOne().getChannel().equals(Channel.ATM))
				&& f.getOne().getDate()!=null && f.getOne().getDate().toLocalDate().isBefore(LocalDate.now()); 
	}
	
	private void thenOperationforRuleB(NameValueReferableTypeConvertibleMap<TransactionForStatusRule> f, Result<Object> result) {
		result.setValue(f.getOne().clonebuilder()
				.withStatus(Status.SETTLED)
				.withAmount(f.getOne().getAmount().subtract(f.getOne().getFee()!=null ? f.getOne().getFee() : BigDecimal.ZERO)).build());
	}
}
