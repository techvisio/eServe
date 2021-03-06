package com.techvisio.eserve.manager.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Activity;
import com.techvisio.eserve.beans.ActivityParam;
import com.techvisio.eserve.beans.ActivitySearchCriteria;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.db.ActivityDao;
import com.techvisio.eserve.manager.ActivityManager;

@Component
public class ActivityManagerImpl implements ActivityManager {
	
	@Autowired
	ActivityDao activityDao;
	
	@Override
	public void createActivityForCustomer(Customer customer){
		Activity activity =new Activity();
		ActivityParam activityParam=new ActivityParam();
		activity.setActivityId(customer.getCustomerId());
		activity.setActivity("create customer");
		activity.setActivityDate(new Date());
		activity.setUsername("avinash giri");
		activity.setDescription("customer");
		activityParam.setIndexId(1L);
		activityParam.setValue(customer.getCustomerName());
		activityParam.setUrl("customer");
		activity.setActivityParam(activityParam);
		activityDao.SaveActivity(activity);
	}
	
	/*@SuppressWarnings("unused")
	private Long  indexValue(){
		ActivityParam activityParam=new ActivityParam();
		long index=1;
		long index1 = activityParam.getIndexId()+index;
		return index1;
	}*/
	
	@Override
	public List<Activity> searchByDate(ActivitySearchCriteria activitySearchCriteria, Long ClientId){
		List<Activity> Date = activityDao.searchByDate(activitySearchCriteria,ClientId);
		return Date;
	}
	
	
}
