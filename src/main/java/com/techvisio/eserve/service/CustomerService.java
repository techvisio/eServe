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
	public Long saveCustomer(Customer customer, String context);
	public Long saveCustomer(Customer customer);
	public void saveUnit(List<Unit> units,  Long customerId);
	public Long saveUnit(Unit unit, String context);
	public List<Unit> getUnits(Long customerId);
	public List<Customer> getCustomerByCriteria(SearchCriteria searchCriteria);
//	public Map<String, Object> checkCustomerExistOrNot(
//			CustomerComplaint customerComplaint);
	void renewService(ServiceAgreement agreement);
	public List<ServiceAgreementHistory> getServiceAgreementHistoryForUnit(Long unitId);
	public Unit getUnit(Long unitId);
	public Unit approveUnit(Unit unit);
	ApproveUnitDtl getUnitForApproval(Long unitId);
	Customer createCustomerfromComplaint(CustomerComplaint customerComplaint);
}
