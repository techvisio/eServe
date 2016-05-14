package com.techvisio.eserve.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techvisio.eserve.beans.CustomerReport;
import com.techvisio.eserve.beans.ReportAttribute;
import com.techvisio.eserve.beans.Response;
import com.techvisio.eserve.beans.SearchResultData;
import com.techvisio.eserve.service.ReportService;
import com.techvisio.eserve.util.ObjectExcelMapper;
@RestController
@RequestMapping("service/report")
public class ReportController {
	@Autowired
	ReportService reportService;

	@RequestMapping(value="/customerreport/",method = RequestMethod.POST)
	public ResponseEntity<Response> getCustomerByCriteria(@RequestBody ReportAttribute customerReportAttribute) throws ParseException {
		Response response=new Response();
		SearchResultData customerReportByCriteria = reportService.getCustomerReportByCriteria(customerReportAttribute);
		response.setResponseBody(customerReportByCriteria);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}



	@RequestMapping(value ="/downloadReport/{reportName}", method = RequestMethod.POST)
	public void getEnquiryReportAsExcel(@RequestBody ReportAttribute customerReportAttribute,@PathVariable String reportName,HttpServletResponse response) throws IOException{
		if(reportName != null && !reportName.contains(".xlsx")){
			reportName=reportName+".xlsx";
		}

		byte[] file = reportService.getReportFile(customerReportAttribute);
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + reportName + "\"");
		org.apache.commons.io.IOUtils.copy(new ByteArrayInputStream(file), response.getOutputStream());
		response.flushBuffer();	
	}

}