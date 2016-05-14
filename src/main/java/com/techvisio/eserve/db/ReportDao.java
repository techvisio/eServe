package com.techvisio.eserve.db;

import java.text.ParseException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.CustomerReport;
import com.techvisio.eserve.beans.ReportAttribute;
import com.techvisio.eserve.beans.SearchResultData;

@Component
public interface ReportDao {
	public SearchResultData getCustomerReportByCriteria(ReportAttribute customerReportAttribute, Long clientId) throws ParseException;
}
