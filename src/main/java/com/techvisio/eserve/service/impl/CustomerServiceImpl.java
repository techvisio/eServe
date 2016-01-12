package com.techvisio.eserve.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.ComplaintAssignment;
import com.techvisio.eserve.beans.ComplaintResolution;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.CustomerComplaint;
import com.techvisio.eserve.beans.SearchComplaint;
import com.techvisio.eserve.beans.SearchComplaintCustomer;
import com.techvisio.eserve.beans.SearchComplaintUnit;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.manager.CustomerManager;
import com.techvisio.eserve.service.CustomerService;

@Component
@Transactional
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	CustomerManager customerManager;

	
	@Override
	public List<Customer> getCustomers() {
 		List<Customer> customers = customerManager.getCustomers();
		return customers;
	}

	@Override
	public Customer getCustomer(Long customerId) {
        Customer customer = customerManager.getCustomer(customerId); 
		return customer;
	}

	@Override
	public Map<String, Object> saveCustomer(Customer customer) {
		Map<String, Object> customerMap = customerManager.saveCustomer(customer);
		return customerMap;
	}

	@Override
	public void saveUnit(List<Unit> units, Long customerId) {
         customerManager.saveUnit(units, customerId);
	}

	@Override
	public void saveUnit(Unit unit) {
        customerManager.saveUnit(unit);
	}

	@Override
	public List<Unit> getUnits(Long customerId) {
		List<Unit> units = customerManager.getUnits(customerId);
		return units;
	}

	@Override
	public List<Customer> getCustomerByCriteria(SearchCriteria searchCriteria) {
		List<Customer> customers = customerManager.getCustomerByCriteria(searchCriteria);
		return customers;
	}

	@Override
	public void saveComplaint(CustomerComplaint customerComplaint) {
customerManager.saveComplaint(customerComplaint);		
	}

	@Override
	public CustomerComplaint getCustomerComplaint(Long complaintId) {
		CustomerComplaint customerComplaint = customerManager.getCustomerComplaint(complaintId);
		return customerComplaint;
	}

	@Override
	public Customer getCustomerBasicInfo(Long customerId) {
        Customer customer =  customerManager.getCustomerBasicInfo(customerId);
		return customer;
	}

	@Override
	public Unit getUnitBasicInfo(Long unitId) {
		Unit unit = customerManager.getUnitBasicInfo(unitId);
		return unit;
	}

	@Override
	public List<CustomerComplaint> getCustomerComplaints(Long customerId) {
		List<CustomerComplaint> complaints = customerManager.getCustomerComplaints(customerId);
		return complaints;
	}

	@Override
	public void saveComplaintResolution(Long complaintId,
			ComplaintResolution complaintResolution) {
		customerManager.saveComplaintResolution(complaintId, complaintResolution);
	}

	@Override
	public ComplaintResolution getComplaintResolution(Long complaintId) {
		ComplaintResolution complaintResolution = customerManager.getComplaintResolution(complaintId);
		return complaintResolution;
	}

	@Override
	public void saveComplaintAssignment(Long complaintId,
			ComplaintAssignment complaintAssignment) {
		customerManager.saveComplaintAssignment(complaintId, complaintAssignment);
		
	}

	@Override
	public ComplaintAssignment getComplaintAssignment(Long complaintId) {
		ComplaintAssignment assignment = customerManager.getComplaintAssignment(complaintId);
		return assignment;
	}

	@Override
	public List<SearchComplaintCustomer> getCustomerForComplaintByCriteria(
			SearchCriteria searchCriteria) {
		List<SearchComplaintCustomer> complaintCustomers = customerManager.getCustomerForComplaintByCriteria(searchCriteria);
		return complaintCustomers;
	}

	@Override
	public List<SearchComplaintUnit> getSearchUnitByCustomerId(Long customerId) {
		List<SearchComplaintUnit> complaintUnits = customerManager.getSearchUnitByCustomerId(customerId);
		return complaintUnits;
	}

	@Override
	public List<SearchComplaint> getComplaintByUnitId(Long unitId) {
		List<SearchComplaint> complaints = customerManager.getComplaintByUnitId(unitId);
		return complaints;
	}

}
