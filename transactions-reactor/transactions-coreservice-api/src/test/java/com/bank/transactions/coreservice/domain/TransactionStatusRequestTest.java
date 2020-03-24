package com.bank.transactions.coreservice.domain;

import com.bank.framework.domain.Channel;
import com.bank.framework.domain.test.utils.AbstractModelBeanTest;

public class TransactionStatusRequestTest extends AbstractModelBeanTest<TransactionStatusRequest>{

	private static final String REF = "REF";
	private final TransactionStatusRequest.TransactionStatusRequestBuilder builder = 
			TransactionStatusRequest.builder()
			.withChannel(Channel.ATM)
			.withReference(REF);
			
	
	@Override
	public void initEntities() {
		entityA1 = builder.build();
		entityA2 = entityA1.cloneBuilder().build();
		entityB = builder.withChannel(Channel.CLIENT).build();
	}
}
