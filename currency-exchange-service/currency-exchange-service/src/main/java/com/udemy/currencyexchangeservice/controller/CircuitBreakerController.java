package com.udemy.currencyexchangeservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircuitBreakerController {
	
	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
	
	@GetMapping("/sample-api")
	//this annotation will retry 3 times and it will then log the actual error after 3rd retry fails
	//fallback will call the method after the unsuccessful attempts
	//@Retry(name="sample-api", fallbackMethod = "hardCodedMethod")
	
	//explained in readme
	//@CircuitBreaker(name="sample-api", fallbackMethod = "hardCodedMethod")
	
	//explained in application.properties
	@RateLimiter(name="sample-api")
	public String sampleApi() {
		
		//check the logs, below string will get print 3 times
		logger.info("Sample API called");
		
		//We are purposely writing this code to create a failure scenario
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/dummy-call", String.class);
		return forEntity.getBody();
	}
	
	//hardcoded fallback method, it requires any exception as a parameter which is of type Throwable
	public String hardCodedMethod(Exception ex) {
		return "fallback-response";
	}
}
