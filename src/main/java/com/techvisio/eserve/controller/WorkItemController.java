package com.techvisio.eserve.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techvisio.eserve.beans.Comment;
import com.techvisio.eserve.beans.GenericRequest;
import com.techvisio.eserve.beans.Response;
import com.techvisio.eserve.beans.UnitBasicInfo;
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

	@RequestMapping(value="/unitinfo/{entityId}/{entityType}",method = RequestMethod.GET)
	public ResponseEntity<Response> getUnitInfoByEntityIdAndEntityType(@PathVariable Long entityId, @PathVariable String entityType){

		Response response=new Response();
		UnitBasicInfo unitBasicInfo = workItemService.getUnitBasicInfo(entityId,entityType);
		response.setResponseBody(unitBasicInfo);
		return new ResponseEntity<Response>(response,HttpStatus.OK);		
	}

	@RequestMapping(value="/{workitemId}",method = RequestMethod.GET)
	public ResponseEntity<Response> getWorkItemByWorkitemId(@PathVariable Long workitemId){
		Response response=new Response();
		WorkItem workItem = workItemService.getWorkitemByWorkitemId(workitemId);
		response.setResponseBody(workItem);
		return new ResponseEntity<Response>(response,HttpStatus.OK);		
	}
	
	@RequestMapping(value="/savecomment/", method = RequestMethod.POST)
	public ResponseEntity<Response> saveUnit(@RequestBody GenericRequest<WorkItem> request) {  
		Response response=new Response();
		List<Comment> commentList = workItemService.saveComment(request);
		response.setResponseBody(commentList);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value="/getcomment/{workItemId}",method = RequestMethod.GET)
	public ResponseEntity<Response> getCommentList(@PathVariable Long workItemId) {  
		Response response=new Response();
		List<Comment> comment = workItemService.getCommentList(workItemId);
		response.setResponseBody(comment);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
}
