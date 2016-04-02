package com.techvisio.eserve.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.ApproveUnitDtl;
import com.techvisio.eserve.beans.ComplaintAssignment;
import com.techvisio.eserve.beans.ComplaintResolution;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.CustomerComplaint;
import com.techvisio.eserve.beans.GenericRequest;
import com.techvisio.eserve.beans.SearchComplaint;
import com.techvisio.eserve.beans.SearchComplaintCustomer;
import com.techvisio.eserve.beans.SearchComplaintUnit;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.ServiceAgreement;
import com.techvisio.eserve.beans.ServiceAgreementHistory;
import com.techvisio.eserve.beans.ServiceRenewalBean;
import com.techvisio.eserve.beans.Unit;

@Component
@Transactional
public interface CustomerService {

	public List<Customer> getCustomers();
	public Customer getCustomer(Long customerId);
	public Long saveCustomer(GenericRequest<Customer> request, String context);
	public Long saveCustomer(Customer customer);
	public void saveUnit(List<Unit> units,  Long customerId);
	public Long saveUnit(GenericRequest<Unit> request, String context);
	public List<Unit> getUnits(Long customerId);
	public List<Customer> getCustomerByCriteria(SearchCriteria searchCriteria);
//	public Map<String, Object> checkCustomerExistOrNot(
//			CustomerComplaint customerComplaint);
	public void updateServiceAgreement(ServiceAgreement agreement,Long unitId);
	public List<ServiceAgreementHistory> getServiceAgreementHistoryForUnit(Long unitId);
	public Unit getUnit(Long unitId);
	public Unit approveUnit(Unit unit);
	public Unit rejectUnitApproval(Unit unit);
	public ApproveUnitDtl getUnitForApproval(Long unitId);
	public Customer createCustomerfromComplaint(CustomerComplaint customerComplaint);
	public Customer getEmailId(String emailId);
	public Customer getContactNo(String contactNo);
}
