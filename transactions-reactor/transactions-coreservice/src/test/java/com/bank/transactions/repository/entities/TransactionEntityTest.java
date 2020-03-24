package com.bank.transactions.repository.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bank.framework.domain.Channel;
import com.bank.framework.domain.Status;
import com.bank.framework.domain.test.utils.AbstractModelBeanTest;

public class TransactionEntityTest extends AbstractModelBeanTest<TransactionEntity>{

	private static final String ACCOUNT_IBAN = "ES99999999999999";
	private static final String DESC = "desc";
	private static final String REF = "ref";
	private static final String ACCOUNT_IBAN_B = "ES88888888888888";
	private static final BigDecimal AMOUNT = BigDecimal.ONE;
	private static final LocalDateTime DATE = LocalDateTime.now();
	private static final BigDecimal FEE = BigDecimal.ZERO;
	private static final Status STATUS = Status.FUTURE;
	private static final Channel channel = Channel.CLIENT;
	
	private final TransactionEntity.Builder builder = TransactionEntity.builder()
														.withAccount_iban(ACCOUNT_IBAN)
														.withAmount(AMOUNT)
														.withDate(DATE)
														.withDescription(DESC)
														.withFee(FEE)
														.withReference(REF)
														.withStatus(STATUS.getCode())
														.withChannel(channel.getCode());
	
	@Override
	public void initEntities() {
		entityA1 = builder.build();
		entityA2 = entityA1.cloneBuilder().build();
		entityB = builder.withAccount_iban(ACCOUNT_IBAN_B).build();
	}

}
