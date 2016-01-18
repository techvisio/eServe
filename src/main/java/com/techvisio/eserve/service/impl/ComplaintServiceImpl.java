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
import com.techvisio.eserve.manager.ComplaintManager;
import com.techvisio.eserve.manager.CustomerManager;
import com.techvisio.eserve.service.ComplaintService;

@Component
@Transactional
public class ComplaintServiceImpl implements ComplaintService{

	@Autowired
	ComplaintManager complaintManager;

	@Override
	public Map<String, Object> saveComplaint(CustomerComplaint customerComplaint) {
		Map<String, Object> result = complaintManager.saveComplaint(customerComplaint);		
		return result;
	}

	@Override
	public CustomerComplaint getCustomerComplaint(Long complaintId) {
		CustomerComplaint customerComplaint = complaintManager.getCustomerComplaint(complaintId);
		return customerComplaint;
	}

	@Override
	public Customer getCustomerBasicInfo(Long customerId) {
		Customer customer =  complaintManager.getCustomerBasicInfo(customerId);
		return customer;
	}

	@Override
	public Unit getUnitBasicInfo(Long unitId) {
		Unit unit = complaintManager.getUnitBasicInfo(unitId);
		return unit;
	}

	@Override
	public List<CustomerComplaint> getCustomerComplaints(Long customerId) {
		List<CustomerComplaint> complaints = complaintManager.getCustomerComplaints(customerId);
		return complaints;
	}

	@Override
	public void saveComplaintResolution(Long complaintId,
			ComplaintResolution complaintResolution) {
		complaintManager.saveComplaintResolution(complaintId, complaintResolution);
	}

	@Override
	public ComplaintResolution getComplaintResolution(Long complaintId) {
		ComplaintResolution complaintResolution = complaintManager.getComplaintResolution(complaintId);
		return complaintResolution;
	}

	@Override
	public void saveComplaintAssignment(Long complaintId,
			ComplaintAssignment complaintAssignment) {
		complaintManager.saveComplaintAssignment(complaintId, complaintAssignment);

	}

	@Override
	public ComplaintAssignment getComplaintAssignment(Long complaintId) {
		ComplaintAssignment assignment = complaintManager.getComplaintAssignment(complaintId);
		return assignment;
	}

	@Override
	public List<SearchComplaintCustomer> getCustomerForComplaintByCriteria(
			SearchCriteria searchCriteria) {
		List<SearchComplaintCustomer> complaintCustomers = complaintManager.getCustomerForComplaintByCriteria(searchCriteria);
		return complaintCustomers;
	}

	@Override
	public List<SearchComplaintUnit> getSearchUnitByCustomerId(Long customerId) {
		List<SearchComplaintUnit> complaintUnits = complaintManager.getSearchUnitByCustomerId(customerId);
		return complaintUnits;
	}

	@Override
	public List<SearchComplaint> getComplaintSearchByUnitId(Long unitId) {
		List<SearchComplaint> complaints = complaintManager.getComplaintSearchByUnitId(unitId);
		return complaints;
	}
	
	@Override
	public List<CustomerComplaint> getAllComplaintsForUnit(Long unitId) {
		List<CustomerComplaint> complaints= complaintManager.getAllComplaintsForUnit(unitId);
		return complaints;
	}

}
