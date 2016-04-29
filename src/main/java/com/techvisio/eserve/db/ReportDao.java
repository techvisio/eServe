package com.techvisio.eserve.db;

import java.util.List;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.CustomerReport;
import com.techvisio.eserve.beans.ReportAttribute;

@Component
public interface ReportDao {
	public List<CustomerReport> getCustomerReportByCriteria(ReportAttribute customerReportAttribute, Long clientId);
}
