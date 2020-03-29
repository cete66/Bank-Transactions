package com.bank.transactions;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "/application.properties")
public class TransactionServiceApplicationIT {

	@Test
	public void contextLoads() {
		
	}
}
