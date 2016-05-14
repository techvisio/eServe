package com.techvisio.eserve.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.CustomerReport;
import com.techvisio.eserve.beans.ReportAttribute;
import com.techvisio.eserve.beans.SearchResultData;
@Component
@Transactional
public interface ReportService {
	public SearchResultData getCustomerReportByCriteria(ReportAttribute customerReportAttribute) throws ParseException;
	public byte[] getReportFile(ReportAttribute customerReportAttribute);

}
