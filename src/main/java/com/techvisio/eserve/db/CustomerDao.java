package com.techvisio.eserve.db;

import java.util.List;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.ComplaintAssignment;
import com.techvisio.eserve.beans.ComplaintResolution;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.CustomerComplaint;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.Unit;

@Component
public interface CustomerDao {

	public Customer getCustomer(Long customerId);
	public Long saveCustomer(Customer customer);
	public void saveUnit(List<Unit> units,  Long customerId);
	public void saveUnit(Unit unit);
	public List<Unit> getUnits(Long customerId);
	public List<Customer> getCustomers();
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
	public boolean isCustomerExists(Customer customer);
}
