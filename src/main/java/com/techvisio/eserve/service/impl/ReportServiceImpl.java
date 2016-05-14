package com.techvisio.eserve.service.impl;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.CustomerReport;
import com.techvisio.eserve.beans.ReportAttribute;
import com.techvisio.eserve.beans.SearchResultData;
import com.techvisio.eserve.manager.ReportManager;
import com.techvisio.eserve.service.ReportService;
import com.techvisio.eserve.util.CommonUtil;
@Component
@Transactional
public class ReportServiceImpl implements ReportService {

	@Autowired
	ReportManager reportManager;

	@Override
	public SearchResultData getCustomerReportByCriteria(ReportAttribute customerReportAttribute) throws ParseException{
		Long clientId = CommonUtil.getCurrentClient().getClientId();	
		SearchResultData customerReports = reportManager.getCustomerReportByCriteria(customerReportAttribute, clientId);
		return customerReports;
	}

	@Override
	public byte[] getReportFile(ReportAttribute customerReportAttribute) {
		Long clientId = CommonUtil.getCurrentClient().getClientId();	
		byte[] file = reportManager.getReportFile(customerReportAttribute, clientId);
		return file;
	}
}
