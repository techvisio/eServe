package com.techvisio.eserve.service.impl;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Activity;
import com.techvisio.eserve.beans.ActivitySearchCriteria;
import com.techvisio.eserve.beans.Client;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.manager.ActivityManager;
import com.techvisio.eserve.service.ActivityService;
import com.techvisio.eserve.util.CommonUtil;
@Component
public class ActivityServiceImpl implements ActivityService {
	
	@Autowired
	ActivityManager activityManager;
	
	@Override
	public void createActivityForCustomer(Customer customer){
		activityManager.createActivityForCustomer(customer);
	}
	
	@Override
	public List<Activity> searchByDate(ActivitySearchCriteria activitySearchCriteria){
		
		Client client = CommonUtil.getCurrentClient();
		List<Activity> date = activityManager.searchByDate(activitySearchCriteria,client.getClientId());
		return date;
	}

}
