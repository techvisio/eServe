package com.techvisio.eserve.workflow.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.manager.CustomerManager;
import com.techvisio.eserve.workflow.CustomerWorkflowManager;

@Component
@Transactional
public class CustomerWorkflowManagerImpl implements CustomerWorkflowManager {

	@Autowired
	CustomerManager customerManager;

	@Override
	public Customer getCustomer(Long customerId) {
		Customer customer = customerManager.getCustomer(customerId);
		return customer;
	}

	@Override
	public void saveCustomer(Customer customer) {
		customerManager.saveCustomer(customer);		
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
	public List<Customer> getCustomers() {
		List<Customer> customers = customerManager.getCustomers();
		return customers;
	}
	
}
