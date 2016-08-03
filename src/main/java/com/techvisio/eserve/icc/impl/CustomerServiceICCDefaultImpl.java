package com.techvisio.eserve.icc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.exception.EntityLockedException;
import com.techvisio.eserve.service.ActivityService;
import com.techvisio.eserve.service.EntityLockService;
import com.techvisio.eserve.service.WorkItemService;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;

@Service
public class CustomerServiceICCDefaultImpl extends	AbstractCustomerServiceICCImpl {

	@Autowired
	WorkItemService workItemService; 

	@Autowired
	ActivityService activityService;

	@Autowired
	EntityLockService entityLockService;
	
	@Override
	public void preRetrieveAllCustomers() {
		super.preRetrieveAllCustomers();
	}

	@Override
	public List<Customer> postRetrieveAllCustomers(List<Customer> customers) {
		return super.postRetrieveAllCustomers(customers);
	}

	@Override
	public Customer preSaveCustomerDirect(Customer customer) {
		 Customer customerPreSave=super.preSaveCustomerDirect(customer);
		 String userName = CommonUtil.getCurrentUser().getUserName();
			boolean isEntityLocked=entityLockService.isEntityLocked(customerPreSave.getCustomerId(), AppConstants.EntityType.CUSTOMER.toString(), userName);
			
			if(!isEntityLocked){
				throw new EntityLockedException("Current user does not hold lock for this customer");
			}
			
		 return customerPreSave;
	}

	@Override
	public Customer postSaveCustomerDirect(Customer customer) {
		 Customer customerPostSave=super.postSaveCustomerDirect(customer);
		 entityLockService.unlockEntity("CUSTOMER", customer.getCustomerId());
		 return customerPostSave;
	}

	
	
}
