package com.techvisio.eserve.manager;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.Unit;
@Transactional
@Component
public interface CustomerManager {

	public List<Customer> getCustomers();
	public Customer getCustomer(Long customerId);
	public Long saveCustomer(Customer customer);
	public void saveUnit(List<Unit> units,  Long customerId);
	public void saveUnit(Unit unit);
	public List<Unit> getUnits(Long customerId);
}
