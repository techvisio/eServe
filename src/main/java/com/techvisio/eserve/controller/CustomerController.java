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

import com.techvisio.eserve.beans.ComplaintAssignment;
import com.techvisio.eserve.beans.ComplaintResolution;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.CustomerComplaint;
import com.techvisio.eserve.beans.Response;
import com.techvisio.eserve.beans.SearchComplaint;
import com.techvisio.eserve.beans.SearchComplaintCustomer;
import com.techvisio.eserve.beans.SearchComplaintUnit;
import com.techvisio.eserve.beans.SearchCriteria;
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

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Response> saveCustomer(@RequestBody Customer customer) {  
		Map<String, Object> result = customerService.saveCustomer(customer);
		customer = (Customer) result.get("customer");
		Response response=new Response();
		response.setResponseBody(result);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Response> updateCustomer(@RequestBody Customer customer) {  
		Map<String, Object> result = customerService.saveCustomer(customer);
		customer = (Customer) result.get("customer");
		Response response=new Response();
		response.setResponseBody(result);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value="/unit/{customerId}", method = RequestMethod.PUT)
	public ResponseEntity<Response> saveUnit(@RequestBody Unit unit, @PathVariable Long customerId) {  
		Response response=new Response();
		unit.setCustomerId(customerId);
		customerService.saveUnit(unit);
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

	@RequestMapping(value ="/complaint/", method = RequestMethod.POST)
	public ResponseEntity<Response> saveCustomerComplaint(@RequestBody CustomerComplaint complaint) {
		Response response=new Response();
        customerService.saveComplaint(complaint);
		CustomerComplaint complaintFromDB = customerService.getCustomerComplaint(complaint.getComplaintId());
        response.setResponseBody(complaintFromDB);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	
	@RequestMapping(value="customercomplaint/{customerId}",method = RequestMethod.GET)
	public ResponseEntity<Response> getCustomerForComplaint(@PathVariable Long customerId) {  
		Response response=new Response();
		Customer customer = customerService.getCustomerBasicInfo(customerId);
		response.setResponseBody(customer);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value="unitcomplaint/{unitId}",method = RequestMethod.GET)
	public ResponseEntity<Response> getUnitForComplaint(@PathVariable Long unitId) {  
		Response response=new Response();
		Unit unit = customerService.getUnitBasicInfo(unitId);
		response.setResponseBody(unit);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value="complaints/{customerId}",method = RequestMethod.GET)
	public ResponseEntity<Response> getAllCustomerComplaints(@PathVariable Long customerId) {  
		Response response=new Response();
		List<CustomerComplaint> complaints = customerService.getCustomerComplaints(customerId);
		response.setResponseBody(complaints);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value="complaint/{complaintId}",method = RequestMethod.GET)
	public ResponseEntity<Response> getCustomerComplaint(@PathVariable Long complaintId) {  
		Response response=new Response();
		CustomerComplaint complaint = customerService.getCustomerComplaint(complaintId);
		response.setResponseBody(complaint);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value ="/complaintresolution/{complaintId}", method = RequestMethod.POST)
	public ResponseEntity<Response> saveComplaintResolution(@PathVariable Long complaintId, @RequestBody ComplaintResolution complaintResolution) {
		Response response=new Response();
        customerService.saveComplaintResolution(complaintId, complaintResolution);
        CustomerComplaint complaintFromDB = customerService.getCustomerComplaint(complaintId);
        response.setResponseBody(complaintFromDB);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value ="/complaintassignment/{complaintId}", method = RequestMethod.POST)
	public ResponseEntity<Response> saveComplaintAssignment(@PathVariable Long complaintId, @RequestBody ComplaintAssignment complaintAssignment) {
		Response response=new Response();
        customerService.saveComplaintAssignment(complaintId, complaintAssignment);
		CustomerComplaint complaintFromDB = customerService.getCustomerComplaint(complaintId);
        response.setResponseBody(complaintFromDB);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value ="/searchcomplaintcustomer/", method = RequestMethod.POST)
	public ResponseEntity<Response> getCustomerComplaintByCriteria(@RequestBody SearchCriteria searchCriteria) {
		Response response=new Response();
		List<SearchComplaintCustomer> complaintByCriteria = customerService.getCustomerForComplaintByCriteria(searchCriteria);
		response.setResponseBody(complaintByCriteria);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value ="/searchcomplaintunit/{customerId}", method = RequestMethod.GET)
	public ResponseEntity<Response> getSearchUnitByCustomerId(@PathVariable Long customerId) {
		Response response=new Response();
		List<SearchComplaintUnit> searchComplaintUnits = customerService.getSearchUnitByCustomerId(customerId);
		response.setResponseBody(searchComplaintUnits);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value ="/searchcomplaint/{unitId}", method = RequestMethod.GET)
	public ResponseEntity<Response> getComplaintByUnitId(@PathVariable Long unitId) {
		Response response=new Response();
		List<SearchComplaint> searchComplaints = customerService.getComplaintByUnitId(unitId);
		response.setResponseBody(searchComplaints);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
}
