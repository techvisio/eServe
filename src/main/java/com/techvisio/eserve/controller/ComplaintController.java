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
import com.techvisio.eserve.service.ComplaintService;
import com.techvisio.eserve.service.CustomerService;

@RestController
@RequestMapping("service/complaint")
public class ComplaintController {

	@Autowired
	ComplaintService complaintService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Response> saveCustomerComplaint(@RequestBody CustomerComplaint complaint) {
		Long complaintId = complaintService.saveComplaint(complaint);
		CustomerComplaint coplaintFromDB = complaintService.getCustomerComplaint(complaintId);
		Response response=new Response();
		response.setResponseBody(coplaintFromDB);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Response> updateCustomerComplaint(@RequestBody CustomerComplaint complaint) {
		Response response=new Response();
        complaintService.saveComplaint(complaint);
		CustomerComplaint complaintFromDB = complaintService.getCustomerComplaint(complaint.getComplaintId());
        response.setResponseBody(complaintFromDB);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}	
	
	@RequestMapping(value="customercomplaint/{customerId}",method = RequestMethod.GET)
	public ResponseEntity<Response> getCustomerForComplaint(@PathVariable Long customerId) {  
		Response response=new Response();
		Customer customer = complaintService.getCustomerBasicInfo(customerId);
		response.setResponseBody(customer);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value="unitcomplaint/{unitId}",method = RequestMethod.GET)
	public ResponseEntity<Response> getUnitForComplaint(@PathVariable Long unitId) {  
		Response response=new Response();
		Unit unit = complaintService.getUnitBasicInfo(unitId);
		response.setResponseBody(unit);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value="complaints/{customerId}",method = RequestMethod.GET)
	public ResponseEntity<Response> getAllCustomerComplaints(@PathVariable Long customerId) {  
		Response response=new Response();
		List<CustomerComplaint> complaints = complaintService.getCustomerComplaints(customerId);
		response.setResponseBody(complaints);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value="/{complaintId}",method = RequestMethod.GET)
	public ResponseEntity<Response> getCustomerComplaint(@PathVariable Long complaintId) {  
		Response response=new Response();
		CustomerComplaint complaint = complaintService.getCustomerComplaint(complaintId);
		response.setResponseBody(complaint);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value ="/complaintresolution/{complaintId}", method = RequestMethod.POST)
	public ResponseEntity<Response> saveComplaintResolution(@PathVariable Long complaintId, @RequestBody ComplaintResolution complaintResolution) {
		Response response=new Response();
        complaintService.saveComplaintResolution(complaintId, complaintResolution);
        CustomerComplaint complaintFromDB = complaintService.getCustomerComplaint(complaintId);
        response.setResponseBody(complaintFromDB);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value ="/complaintassignment/{complaintId}", method = RequestMethod.POST)
	public ResponseEntity<Response> saveComplaintAssignment(@PathVariable Long complaintId, @RequestBody ComplaintAssignment complaintAssignment) {
		Response response=new Response();
        complaintService.saveComplaintAssignment(complaintId, complaintAssignment);
		CustomerComplaint complaintFromDB = complaintService.getCustomerComplaint(complaintId);
        response.setResponseBody(complaintFromDB);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value ="/searchcomplaintcustomer/", method = RequestMethod.POST)
	public ResponseEntity<Response> getCustomerComplaintByCriteria(@RequestBody SearchCriteria searchCriteria) {
		Response response=new Response();
		List<SearchComplaintCustomer> complaintByCriteria = complaintService.getCustomerForComplaintByCriteria(searchCriteria);
		response.setResponseBody(complaintByCriteria);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value ="/searchcomplaintunit/{customerId}", method = RequestMethod.GET)
	public ResponseEntity<Response> getSearchUnitByCustomerId(@PathVariable Long customerId) {
		Response response=new Response();
		List<SearchComplaintUnit> searchComplaintUnits = complaintService.getSearchUnitByCustomerId(customerId);
		response.setResponseBody(searchComplaintUnits);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value ="/searchcomplaint/{unitId}", method = RequestMethod.GET)
	public ResponseEntity<Response> getComplaintSearchByUnitId(@PathVariable Long unitId) {
		Response response=new Response();
		List<SearchComplaint> searchComplaints = complaintService.getComplaintSearchByUnitId(unitId);
		response.setResponseBody(searchComplaints);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value ="/complaint/{unitId}", method = RequestMethod.GET)
	public ResponseEntity<Response> getAllComplaintsForUnit(@PathVariable Long unitId) {
		Response response=new Response();
		List<CustomerComplaint> complaints = complaintService.getAllComplaintsForUnit(unitId);
		response.setResponseBody(complaints);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
}
