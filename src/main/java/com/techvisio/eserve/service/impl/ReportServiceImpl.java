package com.techvisio.eserve.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.CustomerReport;
import com.techvisio.eserve.beans.ReportAttribute;
import com.techvisio.eserve.manager.ReportManager;
import com.techvisio.eserve.service.ReportService;
import com.techvisio.eserve.util.CommonUtil;
@Component
@Transactional
public class ReportServiceImpl implements ReportService {

	@Autowired
	ReportManager reportManager;

	@Override
	public List<CustomerReport> getCustomerReportByCriteria(ReportAttribute customerReportAttribute){
		Long clientId = CommonUtil.getCurrentClient().getClientId();	
		List<CustomerReport> customerReports = reportManager.getCustomerReportByCriteria(customerReportAttribute, clientId);
		return customerReports;
	}
}
