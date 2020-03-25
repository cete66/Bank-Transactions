package com.bank.transactions.response;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;

import com.bank.framework.domain.Status;
import com.bank.framework.domain.test.utils.AbstractModelBeanTest;

public class TransactionWebResponseTest extends AbstractModelBeanTest<TransactionWebResponse>{

	private static final String REF = "ref";
	private final TransactionWebResponse.TransactionWebResponseBuilder builder = TransactionWebResponse.builder()
															.withAmount(BigDecimal.ONE)
															.withFee(BigDecimal.ZERO)
															.withReference(REF)
															.withStatus(Status.FUTURE);
	
    @Override
    @BeforeEach
    public void initEntities(){
        this.entityA1 = builder.build();
        this.entityA2 = entityA1.cloneBuilder().build();
        this.entityB = entityA1.cloneBuilder().withAmount(BigDecimal.TEN).build();
    }

}
