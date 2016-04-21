package com.techvisio.eserve.manager;

import java.util.List;

import com.techvisio.eserve.beans.Activity;
import com.techvisio.eserve.beans.ActivitySearchCriteria;
import com.techvisio.eserve.beans.Customer;

public interface ActivityManager {
	
	public void createActivityForCustomer(Customer customer);

	List<Activity> searchByDate(ActivitySearchCriteria activitySearchCriteria, Long ClientId);
}
