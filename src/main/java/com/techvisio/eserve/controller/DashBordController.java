package com.techvisio.eserve.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techvisio.eserve.beans.GraphData;
import com.techvisio.eserve.beans.Response;
import com.techvisio.eserve.service.DashBordService;
@RestController
@RequestMapping("service/dashbord")

public class DashBordController {
	
	@Autowired
	DashBordService dashBoardService;

	@RequestMapping(value="/totalcount/",method = RequestMethod.GET)
	public ResponseEntity<Response> getcount() {  
		Response response=new Response();
		Map<String, Long> count = dashBoardService.getCount();
		response.setResponseBody(count);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value="/countbypriority/",method = RequestMethod.GET)
	public ResponseEntity<Response> getComplaintCountByPriority() {  
		Response response=new Response();
		GraphData graphData = dashBoardService.getComplaintCountByPriority();
		response.setResponseBody(graphData);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value="/countbysla/",method = RequestMethod.GET)
	public ResponseEntity<Response> getComplaintCountBySlaDate() {  
		Response response=new Response();
		GraphData graphData = dashBoardService.getComplaintCountBySlaDate();
		response.setResponseBody(graphData);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value="/countbyassignment/",method = RequestMethod.GET)
	public ResponseEntity<Response> getComplaintCountByAssignment() {  
		Response response=new Response();
		GraphData graphData = dashBoardService.getComplaintCountByAssignment();
		response.setResponseBody(graphData);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
}
