package com.techvisio.eserve.manager;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.CustomerReport;
import com.techvisio.eserve.beans.ReportAttribute;
import com.techvisio.eserve.beans.SearchResultData;
@Component
public interface ReportManager {

	public SearchResultData getCustomerReportByCriteria(ReportAttribute customerReportAttribute, Long clientId) throws ParseException;

	public byte[] getReportFile(ReportAttribute customerReportAttribute,
			Long clientId);
}
