package com.techvisio.eserve.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.Response;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.workflow.CustomerWorkflowManager;

@RestController
@RequestMapping("/customer")
public class CustomerService {

	@Autowired
	CustomerWorkflowManager customerWorkflowManager;

	@RequestMapping(value="/",method = RequestMethod.GET)
	public ResponseEntity<Response> getCustomers() {  
		Response response=new Response();
		List<Customer> customers = customerWorkflowManager.getCustomers();
		response.setResponseBody(customers);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value="/{customerId}",method = RequestMethod.GET)
	public ResponseEntity<Response> getCustomer(@PathVariable Long customerId) {  
		Response response=new Response();
		Customer customer = customerWorkflowManager.getCustomer(customerId);
		response.setResponseBody(customer);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Response> saveCustomer(@RequestBody Customer customer) {  
		Response response=new Response();
		customerWorkflowManager.saveCustomer(customer);
		Customer customerFromDB = customerWorkflowManager.getCustomer(customer.getCustomerId());

		if(customerFromDB != null){
			response.setResponseBody(customerFromDB);
		}
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value="/unit/{customerId}", method = RequestMethod.PUT)
	public ResponseEntity<Response> saveUnit(@RequestBody List<Unit> units, @PathVariable Long customerId) {  
		Response response=new Response();
		customerWorkflowManager.saveUnit(units, customerId);
		List<Unit> unitsFromDB = customerWorkflowManager.getUnits(customerId);
		response.setResponseBody(unitsFromDB);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value="/unit/{customerId}",method = RequestMethod.GET)
	public ResponseEntity<Response> getUnits(@PathVariable Long customerId) {  
		Response response=new Response();
		List<Unit> units = customerWorkflowManager.getUnits(customerId);
		response.setResponseBody(units);

    	return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

}
