package com.bank.transactions.rs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.framework.domain.SortOrder;
import com.bank.transactions.coreservice.TransactionManager;
import com.bank.transactions.request.TransactionStatusWebRequest;
import com.bank.transactions.request.TransactionWebRequest;
import com.bank.transactions.response.TransactionWebResponse;

@RestController("transactionsController")
@RequestMapping(path = "/transactions")
@SuppressWarnings("deprecation")
public class TransactionsController {

	private final TransactionManager transactionManager;
	
	
	@Autowired
	public TransactionsController(final TransactionManager transacionmManager) {
		this.transactionManager = transacionmManager;
	}

	@PostMapping(
			path = "/create", 
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, /** for compatibility with all browsers*/
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<TransactionWebResponse> create(@RequestBody final TransactionWebRequest webRequest) {
		return ResponseEntity.ok(transactionManager.create(webRequest));
	}
	
	@GetMapping(path = "/search",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, /** for compatibility with all browsers*/
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<TransactionWebResponse>> search(
			@RequestParam("iban") final String iban, 
			@RequestParam("sort") final String sort) {
		return ResponseEntity.ok(transactionManager.search(iban, sort));
	}
	
	@PostMapping(path = "/status",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, /** for compatibility with all browsers*/
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<TransactionWebResponse> status(@RequestBody final TransactionStatusWebRequest webRequest) {
		return ResponseEntity.ok(transactionManager.status(webRequest));
	}
}
