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

}
