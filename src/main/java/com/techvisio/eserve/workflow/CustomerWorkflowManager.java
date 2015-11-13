package com.techvisio.eserve.workflow;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.Unit;

@Component
@Transactional
public interface CustomerWorkflowManager {

	public Customer getCustomer(Long customerId);
	public void saveCustomer(Customer customer);
	public void saveUnit(List<Unit> units,  Long customerId);
	public void saveUnit(Unit unit);
	public List<Unit> getUnits(Long customerId);
	public List<Customer> getCustomers();
}
