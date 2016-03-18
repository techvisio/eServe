package com.techvisio.eserve.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techvisio.eserve.beans.ApproveUnitDtl;
import com.techvisio.eserve.beans.Response;
import com.techvisio.eserve.beans.WorkItem;
import com.techvisio.eserve.service.WorkItemService;

@RestController
@RequestMapping("service/workitem")
public class WorkItemController {
	
	@Autowired
	WorkItemService workItemService;

	@RequestMapping(value="/user/{userId}",method = RequestMethod.GET)
	public ResponseEntity<Response> getWorkItembyUserType(@PathVariable Long userId, @RequestParam(value="type", defaultValue="") String type,
			@RequestParam(value="status", defaultValue="") String status){
		 
		Response response=new Response();
		List<WorkItem> workItems = workItemService.getWorkItembyUserandType(userId, type,status);
		response.setResponseBody(workItems);
		return new ResponseEntity<Response>(response,HttpStatus.OK);		
	}
	
	
}
