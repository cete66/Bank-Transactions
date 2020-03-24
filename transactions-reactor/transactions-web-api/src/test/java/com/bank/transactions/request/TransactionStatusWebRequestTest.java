package com.bank.transactions.request;

import com.bank.framework.domain.Channel;
import com.bank.framework.domain.test.utils.AbstractModelBeanTest;

public class TransactionStatusWebRequestTest extends AbstractModelBeanTest<TransactionStatusWebRequest>{

	private static final String REF = "REF";
	private final TransactionStatusWebRequest.TransactionStatusWebRequestBuilder builder = 
			TransactionStatusWebRequest.builder()
			.withChannel(Channel.ATM)
			.withReference(REF);
			
	
	@Override
	public void initEntities() {
		entityA1 = builder.build();
		entityA2 = entityA1.cloneBuilder().build();
		entityB = builder.withChannel(Channel.CLIENT).build();
	}
}
