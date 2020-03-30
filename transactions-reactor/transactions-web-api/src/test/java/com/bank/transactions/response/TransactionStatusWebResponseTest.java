package com.bank.transactions.response;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;

import com.bank.framework.domain.Status;
import com.bank.framework.domain.test.utils.AbstractModelBeanTest;

public class TransactionStatusWebResponseTest extends AbstractModelBeanTest<TransactionStatusWebResponse>{

	private static final String REF = "ref";
	private final TransactionStatusWebResponse.TransactionStatusWebResponseBuilder builder = TransactionStatusWebResponse.builder()
															.withAmount(BigDecimal.ONE)
															.withFee(BigDecimal.ZERO)
															.withReference(REF)
															.withStatus(Status.FUTURE.getCode());
	
    @Override
    @BeforeEach
    public void initEntities(){
        this.entityA1 = builder.build();
        this.entityA2 = entityA1.cloneBuilder().build();
        this.entityB = entityA1.cloneBuilder().withAmount(BigDecimal.TEN).build();
    }

}
