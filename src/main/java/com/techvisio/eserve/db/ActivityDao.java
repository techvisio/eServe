package com.techvisio.eserve.db;

import java.util.Date;
import java.util.List;

import com.techvisio.eserve.beans.Activity;
import com.techvisio.eserve.beans.ActivitySearchCriteria;

public interface ActivityDao {
	
	public void SaveActivity(Activity activity);

	public List<Activity> searchByDate(ActivitySearchCriteria activitySearchCriteria,
			Long ClientId);

}
