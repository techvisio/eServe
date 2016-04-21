package com.techvisio.eserve.db.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Activity;
import com.techvisio.eserve.beans.ActivitySearchCriteria;
import com.techvisio.eserve.db.ActivityDao;

@Component
public class ActivityDaoImpl extends BaseDao implements ActivityDao {
	

	
public void SaveActivity(Activity activity){
		
		if(activity.getActivityId()==null)
		{
			getEntityManager().persist(activity);
		}
		else{
			getEntityManager().merge(activity);
		}		
		
		getEntityManager().flush();
		activity.getActivityParam().setActivityId(activity.getActivityId());
	}

@Override
public List<Activity> searchByDate(ActivitySearchCriteria activitySearchCriteria, Long ClientId) {
	String queryString="select * from tb_activity where ACTIVITY_DATE between "+ activitySearchCriteria.getFromdate() +" and "+ activitySearchCriteria.getTodate() +" and Client_Id="+ClientId;
	Query query=getEntityManager().createNativeQuery(queryString, Activity.class);
	@SuppressWarnings("unchecked")
	List<Activity> activities= (List<Activity>)query.getResultList();
	return activities;
}


}
