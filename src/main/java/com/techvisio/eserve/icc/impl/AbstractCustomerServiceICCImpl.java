package com.techvisio.eserve.icc.impl;

import java.util.List;

import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.icc.CustomerServiceICC;

public class AbstractCustomerServiceICCImpl implements CustomerServiceICC{

	@Override
	public void preRetrieveAllCustomers() {

	}
	@Override
	public List<Customer> postRetrieveAllCustomers(List<Customer> customers) {
		return customers;
	}
	
	@Override
	public Customer preSaveCustomerDirect(Customer customer) {
		return customer;
		
	}
	
	@Override
	public Customer postSaveCustomerDirect(Customer customer) {
		return customer;
		
	}
	@Override
	public void preGetCustomerbyId() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Customer postGetCustomerbyId(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}
}
