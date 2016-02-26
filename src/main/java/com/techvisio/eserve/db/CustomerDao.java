package com.techvisio.eserve.db;

import java.util.List;

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
import com.techvisio.eserve.beans.ServiceAgreementFinanceHistory;
import com.techvisio.eserve.beans.ServiceAgreementHistory;
import com.techvisio.eserve.beans.ServiceRenewalBean;
import com.techvisio.eserve.beans.Unit;

@Component
public interface CustomerDao {

	public Customer getCustomer(Long customerId);
	public Long saveCustomer(Customer customer);
	public void saveUnit(List<Unit> units,  Long customerId);
	public Long saveUnit(Unit unit);
	public List<Unit> getUnits(Long customerId);
	public List<Customer> getCustomers();
	public List<Customer> getCustomerByCriteria(SearchCriteria searchCriteria);
	public boolean isCustomerExists(Customer customer);
	public List<ServiceAgreementHistory> getServiceAgreementHistoryForUnit(Long unitId);
	void renewService(ServiceAgreement agreement);
	Unit getUnit(Long unitId);
	ApproveUnitDtl getUnitForApproval(Long unitId);
	void saveServiceAgreementHistory(ServiceAgreementHistory history);
	void saveServiceAgreementFinanceHistory(
			ServiceAgreementFinanceHistory financeHistory);
	
}
