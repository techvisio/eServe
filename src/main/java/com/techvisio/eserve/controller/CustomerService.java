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
import com.techvisio.eserve.beans.CustomerComplaint;
import com.techvisio.eserve.beans.Response;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.manager.CustomerManager;

@RestController
@RequestMapping("service/customer")
public class CustomerService {

	@Autowired
	CustomerManager customerManager;

	@RequestMapping(value="/",method = RequestMethod.GET)
	public ResponseEntity<Response> getCustomers() {  
		Response response=new Response();
		List<Customer> customers = customerManager.getCustomers();
		response.setResponseBody(customers);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value="/{customerId}",method = RequestMethod.GET)
	public ResponseEntity<Response> getCustomer(@PathVariable Long customerId) {  
		Response response=new Response();
		Customer customer = customerManager.getCustomer(customerId);
		response.setResponseBody(customer);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Response> saveCustomer(@RequestBody Customer customer) {  
		Response response=new Response();
		customerManager.saveCustomer(customer);
		Customer customerFromDB = customerManager.getCustomer(customer.getCustomerId());

		if(customerFromDB != null){
			response.setResponseBody(customerFromDB);
		}
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value="/unit/{customerId}", method = RequestMethod.PUT)
	public ResponseEntity<Response> saveUnit(@RequestBody Unit unit, @PathVariable Long customerId) {  
		Response response=new Response();
		unit.setCustomerId(customerId);
		customerManager.saveUnit(unit);
		List<Unit> unitsFromDB = customerManager.getUnits(customerId);
		response.setResponseBody(unitsFromDB);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value="/unit/{customerId}",method = RequestMethod.GET)
	public ResponseEntity<Response> getUnits(@PathVariable Long customerId) {  
		Response response=new Response();
		List<Unit> units = customerManager.getUnits(customerId);
		response.setResponseBody(units);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value ="/search/", method = RequestMethod.POST)
	public ResponseEntity<Response> getCustomerByCriteria(@RequestBody SearchCriteria searchCriteria) {
		Response response=new Response();
		List<Customer> customerByCriteria = customerManager.getCustomerByCriteria(searchCriteria);
		response.setResponseBody(customerByCriteria);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value ="/complaint/", method = RequestMethod.POST)
	public ResponseEntity<Response> saveCustomerComplaint(@RequestBody CustomerComplaint complaint) {
		Response response=new Response();
        customerManager.saveComplaint(complaint);
		CustomerComplaint complaintFromDB = customerManager.getCustomerComplaint(complaint.getComplaintId());
        response.setResponseBody(complaintFromDB);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	
	@RequestMapping(value="complaint/{customerId}",method = RequestMethod.GET)
	public ResponseEntity<Response> getCustomerForComplaint(@PathVariable Long customerId) {  
		Response response=new Response();
		Customer customer = customerManager.getCustomerBasicInfo(customerId);
		response.setResponseBody(customer);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value="unitcomplaint/{unitId}",method = RequestMethod.GET)
	public ResponseEntity<Response> getUnitForComplaint(@PathVariable Long unitId) {  
		Response response=new Response();
		Unit unit = customerManager.getUnitBasicInfo(unitId);
		response.setResponseBody(unit);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
}
