package com.techvisio.eserve.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.CustomerComplaint;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.db.CustomerDao;
import com.techvisio.eserve.factory.UniqueIdentifierGenerator;
import com.techvisio.eserve.manager.CustomerManager;
import com.techvisio.eserve.util.CommonUtil;
@Transactional
@Component
public class CustomerManagerImpl implements CustomerManager {

	@Autowired
	CustomerDao customerDao;
	
	@Override
	public Customer getCustomer(Long customerId) {
		Customer customer = customerDao.getCustomer(customerId);
		return customer;
	}

	@Override
	public List<Customer> getCustomerByCriteria(SearchCriteria searchCriteria) {
		List<Customer> customers = customerDao.getCustomerByCriteria(searchCriteria);
		return customers;
	}
	
	@Override
	public Long saveCustomer(Customer customer) {
		
	    customer.setClient(CommonUtil.getCurrentClient());
		Long customerId=customerDao.saveCustomer(customer);	
		return customerId;
	}

	@Override
	public void saveUnit(List<Unit> units, Long customerId) {
		customerDao.saveUnit(units, customerId);
	}

	@Override
	public void saveUnit(Unit unit) {
		customerDao.saveUnit(unit);
		
	}

	@Override
	public List<Unit> getUnits(Long customerId) {
		List<Unit> units = customerDao.getUnits(customerId);
		return units;
	}
	
	@Override
	public List<Customer> getCustomers() {
		List<Customer> customers = customerDao.getCustomers();
		return customers;
	}

	@Override
	public CustomerComplaint getCustomerComplaint(Long complaintId) {
		CustomerComplaint complaint = customerDao.getCustomerComplaint(complaintId);
    	return complaint;
	}
	@Override
	public void saveComplaint(CustomerComplaint customerComplaint){
		customerDao.saveComplaint(customerComplaint);
	}

	@Override
	public Customer getCustomerBasicInfo(Long customerId) {
		Customer customer = customerDao.getCustomerBasicInfo(customerId);
		return customer;
	}

	@Override
	public Unit getUnitBasicInfo(Long unitId) {
		Unit unit = customerDao.getUnitBasicInfo(unitId);
		return unit;
	}
}
