package com.techvisio.eserve.service.impl;

import java.util.ArrayList;
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
import com.techvisio.eserve.beans.ServiceAgreementHistory;
import com.techvisio.eserve.beans.ServiceRenewalBean;
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
	public Map<String, Object> checkCustomerExistOrNot(CustomerComplaint customerComplaint) {
		
		Map<String, Object> result;
		Customer customer = createCustomerfromComplaint(customerComplaint);
		
		result = customerManager.saveCustomer(customer);

		if(!(boolean) result.get("success")){
			return result;
		}

		Customer customerFromDB = customerManager.getCustomer(customer.getCustomerId());
		customerComplaint.setCustomerCode(customerFromDB.getCustomerCode());
		customerComplaint.setCustomerId(customerFromDB.getCustomerId());
		return result;
	}
	
	private Customer createCustomerfromComplaint(
			CustomerComplaint customerComplaint) {
		Customer customer = new Customer();
		customer.setCustomerName(customerComplaint.getCustomerName());
		customer.setContactNo(customerComplaint.getContactNo());
		customer.setEmailId(customerComplaint.getEmailId());
		List<Unit> units = new ArrayList<Unit>();
		units.add(customerComplaint.getUnit());

		customer.setUnits(units);
		return customer;
	}

	@Override
	public Unit renewService(Long unitId, ServiceRenewalBean renewalBean) {
		Unit unit = customerManager.renewService(unitId, renewalBean);
		return unit;
	}

	@Override
	public List<ServiceAgreementHistory> getServiceAgreementHistoryForUnit(Long unitId) {
		List<ServiceAgreementHistory> agreementHistories = customerManager.getServiceAgreementHistoryForUnit(unitId);
		return agreementHistories;
	}
}
