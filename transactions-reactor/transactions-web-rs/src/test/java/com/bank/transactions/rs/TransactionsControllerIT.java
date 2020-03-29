package com.bank.transactions.rs;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bank.framework.domain.Channel;
import com.bank.framework.domain.Status;
import com.bank.transactions.TransactionServiceApplication;
import com.bank.transactions.request.TransactionStatusWebRequest;
import com.bank.transactions.request.TransactionWebRequest;
import com.bank.transactions.request.TransactionWebRequest.TransactionWebRequestBuilder;
import com.bank.transactions.response.TransactionWebResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = TransactionServiceApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/application.properties")
public class TransactionsControllerIT {

	private static final String ROOT = "/transactions";
	private static final String SEARCH = "/search";
	private static final String STATUS = "/status";
	private static final String CREATE = "/create";
	private static final String VALID_IBAN = "ES999999999999999999";
	private static final String IBAN_PARAM = "iban";
	private static final String SORT_PARAM = "sort";
	private static final String ASC = "ASC";
	private static final String DESC = "DESC";
	private static ObjectMapper objectMapper;
	private final TransactionWebRequestBuilder webRequestBuilder = TransactionWebRequest.builder()
			.withAccount_iban(VALID_IBAN).withAmount(BigDecimal.TEN).withFee(BigDecimal.ONE);

	MockMvc mockMvc;

	@Autowired
	protected WebApplicationContext wac;
	@Autowired
	TransactionsController transactionsController;

	@BeforeAll
	public static void setUpClass() {
		objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
	}

	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.transactionsController).build();
	}

	@Test
	public void givenNullReferenceShouldStatusReturnBadRequest() throws JsonProcessingException, Exception {
		final TransactionStatusWebRequest request = TransactionStatusWebRequest.builder()
				.withChannel(Channel.CLIENT)
				.withReference(null).build();

		mockMvc.perform(post(ROOT + STATUS).contentType(MediaType.APPLICATION_JSON_UTF8)
								.accept(MediaType.APPLICATION_JSON_UTF8)
								.content(objectMapper
								.writeValueAsString(request)))
		.andExpect(status().isBadRequest());
	}

	@Test
	public void givenValidStatusWebRequestShouldStatusReturnOkForRuleD() throws JsonProcessingException, Exception {

		final TransactionWebRequest createRequest = webRequestBuilder.build();

		final MockHttpServletResponse createResponse = mockMvc
				.perform(post(ROOT + CREATE).contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_JSON_UTF8)
						.content(objectMapper.writeValueAsString(createRequest.cloneBuilder().withFee(null).build())))
				.andReturn().getResponse();

		final TransactionWebResponse createActual = objectMapper.readValue(createResponse.getContentAsString(),
				TransactionWebResponse.class);

		final TransactionStatusWebRequest request = TransactionStatusWebRequest.builder().withChannel(Channel.CLIENT)
				.withReference(createActual.getReference()).build();

		final MockHttpServletResponse response = mockMvc
				.perform(post(ROOT + STATUS).contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_JSON_UTF8).content(objectMapper.writeValueAsString(request)))
				.andReturn().getResponse();

		final String entity = response.getContentAsString();
		final TransactionWebResponse actual = objectMapper.readValue(entity, TransactionWebResponse.class);
		final TransactionWebResponse expected = TransactionWebResponse.builder()
				.withReference(createActual.getReference()).withStatus(createActual.getStatus())
				.withAmount(createActual.getAmount()).build();
		Assertions.assertTrue(expected.getReference().equals(actual.getReference())
				&& expected.getAmount().compareTo(actual.getAmount()) == 0
				&& expected.getStatus().equals(actual.getStatus()));

	}

	@Test
	public void givenValidWebRequestCreateShouldCreateReturnOk() throws Exception {

		final TransactionWebRequest request = webRequestBuilder.build();

		MockHttpServletResponse response = mockMvc
				.perform(post(ROOT + CREATE).contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_JSON_UTF8)
						.content(objectMapper
								.writeValueAsString(webRequestBuilder.build().cloneBuilder().withFee(null).build())))
				.andReturn().getResponse();

		TransactionWebResponse actual = objectMapper.readValue(response.getContentAsString(),
				TransactionWebResponse.class);

		TransactionWebResponse expected = TransactionWebResponse.builder().withAccount_iban(VALID_IBAN)
				.withAmount(request.getAmount()).withDate(LocalDateTime.now()).withStatus(Status.PENDING.getCode())
				.build();
		Assertions.assertTrue(actual != null && actual.getDate().toLocalDate().isEqual(expected.getDate().toLocalDate())
				&& actual.getAccount_iban().equals(expected.getAccount_iban())
				&& actual.getAmount().compareTo(expected.getAmount()) == 0
				&& actual.getStatus().equals(expected.getStatus()) && response.getStatus() == HttpStatus.OK.value());
	}

	@Test
	public void givenNullIbanWebRequestCreateShouldCreateReturnBadRequest() throws Exception {

		mockMvc.perform(
				post(ROOT + CREATE).contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
						.content(objectMapper.writeValueAsString(
								webRequestBuilder.build().cloneBuilder().withAccount_iban(null).build())))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void givenNullAmountWebRequestCreateShouldCreateReturnBadRequest() throws Exception {

		mockMvc.perform(
				post(ROOT + CREATE).contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
						.content(objectMapper
								.writeValueAsString(webRequestBuilder.build().cloneBuilder().withAmount(null).build())))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void givenInvalidAmountWebRequestCreateShouldCreateReturnConflict() throws Exception {

		mockMvc.perform(post(ROOT + CREATE).contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8).content(objectMapper.writeValueAsString(webRequestBuilder
						.build().cloneBuilder().withAmount(BigDecimal.valueOf(-42398567893457D)).build())))
				.andExpect(status().isConflict());
	}

	@Test
	public void givenValidIbanAndSortAscSearchShouldReturnOk() throws Exception {

		MockHttpServletResponse response = mockMvc
				.perform(get(ROOT + SEARCH).contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_JSON_UTF8).param(IBAN_PARAM, VALID_IBAN).param(SORT_PARAM, ASC))
				.andReturn().getResponse();

		String entity = response.getContentAsString();
		List<TransactionWebResponse> transactions = objectMapper.readValue(entity,
				new TypeReference<List<TransactionWebResponse>>() {
				});

		List<BigDecimal> amounts = transactions.stream().map(e -> e.getAmount()).collect(Collectors.toList());

		List<BigDecimal> amountsSorted = new ArrayList<>(amounts);

		Collections.sort(amountsSorted);

		Assertions.assertTrue(transactions != null && !transactions.isEmpty()
				&& transactions.get(0).getAccount_iban().equals(VALID_IBAN)
				&& response.getStatus() == HttpStatus.OK.value());
		MatcherAssert.assertThat(amounts, Matchers.contains(amountsSorted.toArray()));
	}

	@Test
	public void givenValidIbanAndSortDescSearchShouldReturnOk() throws Exception {

		MockHttpServletResponse response = mockMvc
				.perform(get(ROOT + SEARCH).contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_JSON_UTF8).param(IBAN_PARAM, VALID_IBAN).param(SORT_PARAM, DESC))
				.andReturn().getResponse();

		String entity = response.getContentAsString();
		List<TransactionWebResponse> transactions = objectMapper.readValue(entity,
				new TypeReference<List<TransactionWebResponse>>() {
				});

		List<BigDecimal> amounts = transactions.stream().map(e -> e.getAmount()).collect(Collectors.toList());

		List<BigDecimal> amountsSorted = new ArrayList<>(amounts);

		Collections.sort(amountsSorted, Collections.reverseOrder());

		Assertions.assertTrue(transactions != null && !transactions.isEmpty()
				&& transactions.get(0).getAccount_iban().equals(VALID_IBAN)
				&& response.getStatus() == HttpStatus.OK.value());
		MatcherAssert.assertThat(amounts, Matchers.contains(amountsSorted.toArray()));
	}

	@Test
	public void givenInvalidIbanAndInvalidSortOrderSearchShouldReturnBadRequest() throws Exception {

		mockMvc.perform(
				get(ROOT + SEARCH).contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
						.param(IBAN_PARAM, "asdasdasdasdasdasd").param(SORT_PARAM, "dss"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void givenNullIbanAndValidSortOrderSearchShouldReturnBadRequest() throws Exception {

		mockMvc.perform(
				get(ROOT + SEARCH).contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
						// .param("iban", "")
						.param(SORT_PARAM, ASC))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void givenValidIbanAndNullSortOrderSearchShouldReturnBadRequest() throws Exception {

		mockMvc.perform(get(ROOT + SEARCH).contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8).param(IBAN_PARAM, VALID_IBAN))
				// .param("sort", "ASC"))
				.andExpect(status().isBadRequest());
	}

}
