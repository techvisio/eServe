package com.techvisio.eserve.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.techvisio.eserve.beans.Customer;
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
	
	@Autowired
	UniqueIdentifierGenerator identifierGenerator;
	
	@Override
	public Customer getCustomer(Long customerId) {
		Customer customer = customerDao.getCustomer(customerId);
		return customer;
	}

	@Override
	public Long saveCustomer(Customer customer) {
		
	    String clientCode = null;
	    if(clientCode==null){
	    	clientCode=identifierGenerator.getUniqueIdentifierForCustomer(customer);
	    }
	    customer.setClient(CommonUtil.getCurrentClient());
	    customer.setCustomerCode(clientCode);
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

}
