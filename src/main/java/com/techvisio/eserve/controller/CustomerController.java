package com.techvisio.eserve.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techvisio.eserve.beans.ApproveUnitDtl;
import com.techvisio.eserve.beans.ComplaintAssignment;
import com.techvisio.eserve.beans.ComplaintResolution;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.CustomerComplaint;
import com.techvisio.eserve.beans.Response;
import com.techvisio.eserve.beans.SearchComplaint;
import com.techvisio.eserve.beans.SearchComplaintCustomer;
import com.techvisio.eserve.beans.SearchComplaintUnit;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.ServiceAgreement;
import com.techvisio.eserve.beans.ServiceAgreementHistory;
import com.techvisio.eserve.beans.ServiceRenewalBean;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.beans.UserPrivilege;
import com.techvisio.eserve.manager.CustomerManager;
import com.techvisio.eserve.service.CustomerService;

@RestController
@RequestMapping("service/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@RequestMapping(value="/",method = RequestMethod.GET)
	public ResponseEntity<Response> getCustomers() {  
		Response response=new Response();
		List<Customer> customers = customerService.getCustomers();
		response.setResponseBody(customers);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value="/{customerId}",method = RequestMethod.GET)
	public ResponseEntity<Response> getCustomer(@PathVariable Long customerId) {  
		Response response=new Response();
		Customer customer = customerService.getCustomer(customerId);
		response.setResponseBody(customer);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value="/{context}",method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Response> saveCustomer(@RequestBody Customer customer, @PathVariable String context) {  
		Long customerId = customerService.saveCustomer(customer, context);
		Customer customerFromDB = customerService.getCustomer(customerId);
		Response response=new Response();
		response.setResponseBody(customerFromDB);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Response> updateCustomer(@RequestBody Customer customer) {  
		customerService.saveCustomer(customer);
		Customer customerFromDB = customerService.getCustomer(customer.getCustomerId());
		Response response=new Response();
		response.setResponseBody(customerFromDB);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value="/unit/{customerId}/{context}", method = RequestMethod.PUT)
	public ResponseEntity<Response> saveUnit(@RequestBody Unit unit, @PathVariable Long customerId, @PathVariable String context) {  
		Response response=new Response();
		unit.setCustomerId(customerId);
		customerService.saveUnit(unit,context);
		List<Unit> unitsFromDB = customerService.getUnits(customerId);
		response.setResponseBody(unitsFromDB);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value="/unit/{customerId}",method = RequestMethod.GET)
	public ResponseEntity<Response> getUnits(@PathVariable Long customerId) {  
		Response response=new Response();
		List<Unit> units = customerService.getUnits(customerId);
		response.setResponseBody(units);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value ="/search/", method = RequestMethod.POST)
	public ResponseEntity<Response> getCustomerByCriteria(@RequestBody SearchCriteria searchCriteria) {
		Response response=new Response();
		List<Customer> customerByCriteria = customerService.getCustomerByCriteria(searchCriteria);
		response.setResponseBody(customerByCriteria);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value ="/renewService", method = RequestMethod.PUT)
	public ResponseEntity<Response> renewService(@RequestBody ServiceAgreement agreement) {
		Response response=new Response();
		customerService.renewService(agreement);
		Unit unitFromDB = customerService.getUnit(agreement.getUnitId());
		response.setResponseBody(unitFromDB);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value ="/servicehistory/{unitId}", method = RequestMethod.GET)
	public ResponseEntity<Response> getServiceAgreementHistoryForUnit(@PathVariable Long unitId) {
		Response response=new Response();
		List<ServiceAgreementHistory> agreementHistories = customerService.getServiceAgreementHistoryForUnit(unitId);
		response.setResponseBody(agreementHistories);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value ="/approveunit/", method = RequestMethod.PUT)
	public ResponseEntity<Response> getServiceAgreementHistoryForUnit(@RequestBody Unit unit) {
		Response response=new Response();
		Unit unitFromDB = customerService.approveUnit(unit);
		response.setResponseBody(unitFromDB);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value="/unitapproval/{unitId}",method = RequestMethod.GET)
	public ResponseEntity<Response> getUnitForApproval(@PathVariable Long unitId) {  
		Response response=new Response();
		ApproveUnitDtl unitDtl = customerService.getUnitForApproval(unitId);
		response.setResponseBody(unitDtl);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
}
