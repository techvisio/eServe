package com.techvisio.eserve.db;

import java.util.List;

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

@Component
public interface ComplaintDao {

	public Long saveComplaint(CustomerComplaint customerComplaint);
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
	public List<SearchComplaint> getComplaintSearchByUnitId(Long unitId);
	public List<SearchComplaintCustomer> getCustomerByComplaintCode(
			SearchCriteria searchCriteria);
	List<CustomerComplaint> getAllComplaintsForUnit(Long unitId);

}
