package com.udemy.currecnyconversion.currencyconversionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//Commenting this out to test the loadbalancing of currecny-exchange microservice
//name should be same which is defined in application.properties
@FeignClient(name = "currecny-exchange", url="localhost:8000")

//Eureka will loadbalance the running instances of currency-exchange microservice
//@FeignClient(name = "currecny-exchange")
public interface CurrencyExchangeProxy {

//copied the same method defined in CurrencyExchange Service, changed the return type to CurrecnyConversion 
//bcs the parameters used are same in CurrecnyConversion class and CurrencyExchange class
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrecnyConversion retrieveExchangeValue(@PathVariable String from,@PathVariable String to);

}
