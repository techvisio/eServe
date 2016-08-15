package com.techvisio.eserve.db.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Activity;
import com.techvisio.eserve.beans.ActivitySearchCriteria;
import com.techvisio.eserve.db.ActivityDao;
import com.techvisio.eserve.util.DateUtil;

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
	public List<Activity> searchByDate(ActivitySearchCriteria activitySearchCriteria, Long ClientId)  {

		String fromDateString = activitySearchCriteria.getFromDate();
		String toDateString = activitySearchCriteria.getToDate();
		Date fromDate = null;
		Date toDate = null;
		fromDate= DateUtil.convertStringToSqlDate(fromDateString);
		toDate=DateUtil.convertStringToSqlDate(toDateString);
		String queryString="select Client_Id, CREATED_BY, CREATED_ON,ACTIVITY_ID,ACTIVITY,ACTIVITY_DATE,DESCRIPTION,USERNAME from TB_ACTIVITY where ACTIVITY_DATE between '"+ fromDate +"' and '"+toDate +"' and Client_Id="+ClientId;
		Query query=getEntityManager().createNativeQuery(queryString, Activity.class);
		@SuppressWarnings("unchecked")
		List<Activity> activities= (List<Activity>)query.getResultList();
		return activities;
	}


}
