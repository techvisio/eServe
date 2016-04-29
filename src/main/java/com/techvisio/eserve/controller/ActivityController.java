package com.techvisio.eserve.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.techvisio.eserve.beans.Activity;
import com.techvisio.eserve.beans.ActivitySearchCriteria;
import com.techvisio.eserve.beans.Response;
import com.techvisio.eserve.service.ActivityService;

@RestController
@RequestMapping("service/activity")
public class ActivityController {
	
	@Autowired
	ActivityService activityService;
	
	@RequestMapping(value="/activitybydate/",method = RequestMethod.POST)
	public ResponseEntity<Response> searchByDate(@RequestBody ActivitySearchCriteria activitySearchCriteria) throws ParseException {  
		Response response=new Response();
		List<Activity> activity = (List<Activity>) activityService.searchByDate(activitySearchCriteria);
		response.setResponseBody(activity);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

}
