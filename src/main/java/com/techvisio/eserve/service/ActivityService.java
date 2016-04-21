package com.techvisio.eserve.service;

import java.util.List;

import com.techvisio.eserve.beans.Activity;
import com.techvisio.eserve.beans.ActivitySearchCriteria;
import com.techvisio.eserve.beans.Customer;

public interface ActivityService {

	

	void createActivityForCustomer(Customer customer);

	List<Activity> searchByDate(ActivitySearchCriteria activitySearchCriteria);

}
