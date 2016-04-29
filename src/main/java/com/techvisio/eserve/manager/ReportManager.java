package com.techvisio.eserve.manager;

import java.util.List;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.CustomerReport;
import com.techvisio.eserve.beans.ReportAttribute;
@Component
public interface ReportManager {

	public List<CustomerReport> getCustomerReportByCriteria(ReportAttribute customerReportAttribute, Long clientId);
}
