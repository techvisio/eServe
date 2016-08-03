package com.techvisio.eserve.icc;

import java.util.List;

import com.techvisio.eserve.beans.Customer;

public interface CustomerServiceICC {

	public void preRetrieveAllCustomers();
	public List<Customer> postRetrieveAllCustomers(List<Customer> customers);
	
	public Customer preSaveCustomerDirect(Customer customer);
	public Customer postSaveCustomerDirect(Customer customer);
	
	public void preGetCustomerbyId();
	public Customer postGetCustomerbyId(Customer customer);
	
	
}
