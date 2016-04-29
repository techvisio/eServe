package com.techvisio.eserve.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.CustomerReport;
import com.techvisio.eserve.beans.ReportAttribute;

import com.techvisio.eserve.db.ReportDao;
import com.techvisio.eserve.manager.ReportManager;

@Component
public class ReportManagerImpl implements ReportManager {

	@Autowired
	ReportDao reportDao;

	@Override
	public List<CustomerReport> getCustomerReportByCriteria(ReportAttribute customerReportAttribute, Long clientId){

		List<CustomerReport> customerReports = reportDao.getCustomerReportByCriteria(customerReportAttribute, clientId);
		return customerReports;


	}
}
