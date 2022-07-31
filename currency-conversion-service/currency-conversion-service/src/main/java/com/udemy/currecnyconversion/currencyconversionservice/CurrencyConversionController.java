package com.udemy.currecnyconversion.currencyconversionservice;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeProxy currecnyExchangeProxy;

/*
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrecnyConversion calculateCurrencyConversion(@PathVariable String from,@PathVariable String to, 
			@PathVariable BigDecimal quantity) {
		
		//hardcoded to test
		//return new CurrecnyConversion(10001L, from, to, BigDecimal.ONE, quantity, BigDecimal.ONE, "");
		
		//using hashmap to store values of from and to
		HashMap<String,String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		//calling currency-exchange-service by creating Rest template
		ResponseEntity<CurrecnyConversion> response = new RestTemplate().
				getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
				CurrecnyConversion.class,uriVariables);
		CurrecnyConversion currecnyConversion = response.getBody();
		
		return new CurrecnyConversion(currecnyConversion.getId(), from, to, 
				currecnyConversion.getConversionMultiple(), 
				quantity, quantity.multiply(currecnyConversion.getConversionMultiple()), currecnyConversion.getEnvironment());
	}
*/
//We are using Proxy to do the same functionality done in above class 	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrecnyConversion calculateCurrencyConversionFeign(@PathVariable String from,@PathVariable String to, 
			@PathVariable BigDecimal quantity) {
		
		CurrecnyConversion currecnyConversion = currecnyExchangeProxy.retrieveExchangeValue(from, to);
		//Line 49 got the response body of currency-exchange microservice but we have mapped it to currecny-conversion bcs all
		//the fields are same.
		//Interface.methodname --> how are we getting the response when method is not defined???
		//Interface is annotated with localhost:8080 and method annotated with /currency-exchange/from/{from}/to/{to}
		
		return new CurrecnyConversion(currecnyConversion.getId(), from, to, 
				currecnyConversion.getConversionMultiple(), 
				quantity, quantity.multiply(currecnyConversion.getConversionMultiple()), currecnyConversion.getEnvironment());
		
	}
	
}