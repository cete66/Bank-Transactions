package com.bank.transactions.rs;

import java.net.URI;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;

import com.bank.transactions.TransactionServiceApplication;
import com.bank.transactions.response.TransactionWebResponse;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest(classes = TransactionServiceApplication.class, 
webEnvironment = WebEnvironment.RANDOM_PORT)
public class TransactionsControllerIT {

	
	MockMvc mockMvc;
	
    @Autowired
    protected WebApplicationContext wac;
    @Autowired
    TransactionsController transactionsController;
    
    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.transactionsController).build();
    }
    
    @Test
    public void testSearchSync() throws Exception {
        mockMvc.perform(get("/transactions/search")
        		.contentType(MediaType.APPLICATION_JSON_UTF8)
        		.param("iban", "ES999999999999999999")
        		.param("sort", "ASC"))
            .andExpect(status().isOk());
    }
	
}
