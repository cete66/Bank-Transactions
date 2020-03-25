package com.bank.transactions.request;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;

import com.bank.framework.domain.test.utils.AbstractModelBeanTest;
import com.bank.transactions.request.TransactionWebRequest;

public class TransactionWebRequestTest extends AbstractModelBeanTest<TransactionWebRequest>{

	private static final String ACCOUNT_IBAN = "ES99999999999999";
	private static final String DESC = "desc";
	private static final String REF = "ref";
	private static final String ACCOUNT_IBAN_B = "ES88888888888888";
	private final TransactionWebRequest.TransactionWebRequestBuilder builder = TransactionWebRequest.builder()
															.withAccount_iban(ACCOUNT_IBAN)
															.withAmount(BigDecimal.ONE)
															.withDate(LocalDateTime.now())
															.withDescription(DESC)
															.withFee(BigDecimal.ZERO)
															.withReference(REF);
	
    @Override
    @BeforeEach
    public void initEntities(){
        this.entityA1 = builder.build();
        this.entityA2 = entityA1.cloneBuilder().build();
        this.entityB = entityA1.cloneBuilder().withAccount_iban(ACCOUNT_IBAN_B).build();
    }

}
