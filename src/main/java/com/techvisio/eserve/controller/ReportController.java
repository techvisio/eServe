package com.techvisio.eserve.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techvisio.eserve.beans.CustomerReport;
import com.techvisio.eserve.beans.ReportAttribute;
import com.techvisio.eserve.beans.Response;
import com.techvisio.eserve.service.ReportService;
@RestController
@RequestMapping("service/report")
public class ReportController {
	@Autowired
	ReportService reportService;
	
	@RequestMapping(value="/customerreport/",method = RequestMethod.POST)
	public ResponseEntity<Response> getCustomerByCriteria(@RequestBody ReportAttribute customerReportAttribute) {
		Response response=new Response();
		List<CustomerReport> customerReportByCriteria = reportService.getCustomerReportByCriteria(customerReportAttribute);
		response.setResponseBody(customerReportByCriteria);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

}
