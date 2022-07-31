package com.udemy.currencyexchangeservice.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CurrencyExchangeRepository repository;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(@PathVariable String from,@PathVariable String to) {
		//hard coded
		//CurrencyExchange currecnyExchange = new CurrencyExchange(1000l, from, to, BigDecimal.valueOf(50));
		
		//using JPA
		CurrencyExchange currecnyExchange = repository.findByFromAndTo(from, to);
		if(currecnyExchange == null) {
			throw new RuntimeException("Unable to find data for "+from+" to "+ to);
		}
		
		//environment is an Interface used which extends PropertyResolver Interface which has the getProperty method which returns the 
		//current port of running application
		String port = environment.getProperty("local.server.port");
		
		//we are setting the Environment attribute of currencyExchange class with the port number
		//obtained from environment
		currecnyExchange.setEnvironment(port);
		
		return currecnyExchange;
	}
}
