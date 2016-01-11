package com.techvisio.eserve.manager;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.techvisio.eserve.beans.ComplaintAssignment;
import com.techvisio.eserve.beans.ComplaintResolution;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.CustomerComplaint;
import com.techvisio.eserve.beans.SearchComplaint;
import com.techvisio.eserve.beans.SearchComplaintCustomer;
import com.techvisio.eserve.beans.SearchComplaintUnit;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.Unit;
@Transactional
@Component
public interface CustomerManager {

	public List<Customer> getCustomers();
	public Customer getCustomer(Long customerId);
	public Map<String, Object> saveCustomer(Customer customer);
	public void saveUnit(List<Unit> units,  Long customerId);
	public void saveUnit(Unit unit);
	public List<Unit> getUnits(Long customerId);
	public List<Customer> getCustomerByCriteria(SearchCriteria searchCriteria);
	public void saveComplaint(CustomerComplaint customerComplaint);
	public CustomerComplaint getCustomerComplaint(Long complaintId);
	public Customer getCustomerBasicInfo(Long customerId);
	public Unit getUnitBasicInfo(Long unitId);
	public List<CustomerComplaint> getCustomerComplaints(Long customerId);
	public void saveComplaintResolution(Long complaintId, ComplaintResolution complaintResolution);
	public ComplaintResolution getComplaintResolution(Long complaintId);
	public void saveComplaintAssignment(Long complaintId, ComplaintAssignment complaintAssignment);
	public ComplaintAssignment getComplaintAssignment(Long complaintId);
	public List<SearchComplaintCustomer> getCustomerForComplaintByCriteria(
			SearchCriteria searchCriteria);
	public List<SearchComplaintUnit> getSearchUnitByCustomerId(Long customerId);
	public List<SearchComplaint> getComplaintByUnitId(Long unitId);

}
