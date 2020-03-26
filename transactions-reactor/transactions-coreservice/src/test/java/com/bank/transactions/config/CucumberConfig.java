package com.bank.transactions.config;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = { "classpath:features/checkStatus.feature" },
		glue = "com.bank.transactions.coreservice.steps.rule"
		)
public class CucumberConfig {

}
