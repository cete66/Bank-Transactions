package com.bank.transactions.rs;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang.NullArgumentException;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bank.framework.persistence.exceptions.TransactionNotAllowedException;
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
	public ResponseEntity<TransactionWebResponse> create(@Valid @RequestBody final TransactionWebRequest webRequest) {
		return ResponseEntity.ok(transactionManager.create(webRequest));
	}
	
	@GetMapping(path = "/search",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, /** for compatibility with all browsers*/
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<TransactionWebResponse>> search(
			@RequestParam(name = "iban", required = true) @Valid @NotBlank @Length(min = 16, max = 34) final String iban, 
			@RequestParam(name = "sort", required = true) @Valid @NotBlank @Length(min = 3, max = 4) final String sort) {
		return ResponseEntity.ok(transactionManager.search(iban, sort));
	}
	
	@PostMapping(path = "/status",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, /** for compatibility with all browsers*/
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<TransactionWebResponse> status(@Valid @RequestBody final TransactionStatusWebRequest statusWebRequest) {
		return ResponseEntity.ok(transactionManager.status(statusWebRequest));
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler({	InvalidParameterException.class,
						NullArgumentException.class})
	public Map<String, String> handleValidationExceptions(
	  Exception ex) {
	    Map<String, String> errors = new HashMap<>();
	    errors.put("Cause", ex.getMessage());
	    return errors;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public Map<String, String> handleSpringValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
	
	@ResponseStatus(code = HttpStatus.CONFLICT)
	@ExceptionHandler({TransactionNotAllowedException.class})
	public Map<String, String> handleNotAllowedExceptions(
	  Exception ex) {
	    Map<String, String> errors = new HashMap<>();
	    errors.put("Cause", ex.getMessage());
	    return errors;
	}
}
