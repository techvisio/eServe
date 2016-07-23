package com.techvisio.eserve.component.communication;

import com.techvisio.eserve.beans.Customer;

public interface CustomerServiceComponentCommunication {

	public void preCustomerSave(Customer customer);
	public void postCustomerSave(Customer customer);
	
}
