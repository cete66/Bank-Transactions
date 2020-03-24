package com.bank.transactions.rs;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.transactions.Greeting;

@RestController()
@RequestMapping(path = "/transactions")
public class TransactionsController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@PostMapping("/create")
	public Greeting create(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
	
	@GetMapping("/search")
	public Object search() {
		return null;
	}
	
	@PostMapping("/status")
	public Object status() {
		return null;
	}
}
