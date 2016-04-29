package com.techvisio.eserve.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.CustomerReport;
import com.techvisio.eserve.beans.ReportAttribute;
@Component
@Transactional
public interface ReportService {
	public List<CustomerReport> getCustomerReportByCriteria(ReportAttribute customerReportAttribute);

}
