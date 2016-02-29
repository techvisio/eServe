package com.techvisio.eserve.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techvisio.eserve.beans.WorkItem;
import com.techvisio.eserve.service.WorkItemService;

@RestController
@RequestMapping("service/workitem")
public class WorkItemController {
	
	@Autowired
	WorkItemService workItemService;

	@RequestMapping(value="/user/{userId}",method = RequestMethod.GET)
	public List<WorkItem> getWorkItembyUserType(@PathVariable Long userId, @RequestParam(value="type", defaultValue="") String type){
		return workItemService.getWorkItembyUserandType(userId, type);
	}
}
