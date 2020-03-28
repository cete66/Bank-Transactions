package com.bank.transactions.coreservice.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bank.framework.domain.Channel;
import com.bank.framework.domain.Status;
import com.bank.framework.domain.test.utils.AbstractModelBeanTest;

public class TransactionResponseTest extends AbstractModelBeanTest<TransactionResponse>{

	private static final String ACCOUNT_IBAN = "ES99999999999999";
	private static final String DESC = "desc";
	private static final String REF = "ref";
	private static final String ACCOUNT_IBAN_B = "ES88888888888888";
	private static final Status STATUS = Status.FUTURE;
	private static final Channel CHANNEL = Channel.ATM;
	private final TransactionResponse.TransactionResponseBuilder builder = TransactionResponse.builder()
															.withAccount_iban(ACCOUNT_IBAN)
															.withAmount(BigDecimal.ONE)
															.withDate(LocalDateTime.now())
															.withDescription(DESC)
															.withFee(BigDecimal.ZERO)
															.withReference(REF)
															.withStatus(STATUS)
															.withChannel(CHANNEL);
	
    @Override
    public void initEntities(){
        this.entityA1 = builder.build();
        this.entityA2 = entityA1.cloneBuilder().build();
        this.entityB = entityA1.cloneBuilder().withAccount_iban(ACCOUNT_IBAN_B).build();
    }

}
