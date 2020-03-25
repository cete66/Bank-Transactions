package com.bank.transactions.rs;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.transactions.coreservice.TransactionManager;
import com.bank.transactions.request.TransactionWebRequest;
import com.bank.transactions.response.TransactionWebResponse;

@RestController
@RequestMapping(path = "/transactions")
public class TransactionsController {

	private final TransactionManager transactionManager;
	
	//TODO VER COMO APLCIAR LA VALIDACION DE ENTRADA @NOTEMPTY, ETC - Robin
	
	@Autowired
	public TransactionsController(final TransactionManager transacionmManager) {
		this.transactionManager = transacionmManager;
	}

	@PostMapping(
			path = "/create", 
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, 
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<TransactionWebResponse> create(@RequestBody final TransactionWebRequest webRequest) {
		return ResponseEntity.ok(transactionManager.create(webRequest));
	}
	
	@GetMapping("/search")
	public ResponseEntity<TransactionWebResponse> search() {
		return ResponseEntity.ok(transactionManager.search(null, null));
	}
	
	@PostMapping("/status")
	public ResponseEntity<TransactionWebResponse> status() {
		return null;
	}
}
