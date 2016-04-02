package com.techvisio.eserve.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techvisio.eserve.beans.Response;
import com.techvisio.eserve.service.DashBordService;
@RestController
@RequestMapping("service/dashbord")

public class DashBordController {
	
	DashBordService dashbordservice;

	@RequestMapping(value="totalcount/",method = RequestMethod.GET)
	public ResponseEntity<Response> getcount(@PathVariable Long clientId) {  
		Response response=new Response();
		Map<String, Long> count = dashbordservice.getcount(clientId);
		response.setResponseBody(count);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

}
