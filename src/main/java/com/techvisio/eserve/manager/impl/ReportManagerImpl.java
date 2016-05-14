package com.techvisio.eserve.manager.impl;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.ReportAttribute;
import com.techvisio.eserve.beans.SearchResultData;
import com.techvisio.eserve.db.ReportDao;
import com.techvisio.eserve.manager.ReportManager;
import com.techvisio.eserve.util.ObjectExcelMapper;

@Component
public class ReportManagerImpl implements ReportManager {

	@Autowired
	ReportDao reportDao;

	@Override
	public SearchResultData getCustomerReportByCriteria(ReportAttribute customerReportAttribute, Long clientId) throws ParseException{

		SearchResultData customerReports = reportDao.getCustomerReportByCriteria(customerReportAttribute, clientId);
		return customerReports;
	}

	@Override
	public byte[] getReportFile(ReportAttribute customerReportAttribute, Long clientId){

		SearchResultData customerReportByCriteria=null;
		customerReportAttribute.setPageSize(9999999);
		customerReportAttribute.setPageNo(1);
		try {
			customerReportByCriteria = reportDao.getCustomerReportByCriteria(customerReportAttribute, clientId);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Map<String, String> columnsMap = new HashMap<String, String>();
		columnsMap.put("customerCode","Customer Code");
		columnsMap.put("customerName", "Customer Name");
		columnsMap.put("contactNo", "Contact No");
		columnsMap.put("emailId", "Email Id");
		columnsMap.put("customerType", "Customer Type");
		columnsMap.put("unitCode", "Unit Code");
		columnsMap.put("unitType", "Unit type");
		columnsMap.put("assetNo", "Asset No");
		columnsMap.put("machineSerialNo", "Machine Serial No");
		columnsMap.put("modelNo", "Model No");
		columnsMap.put("approvalStatus", "Approval Status");
		columnsMap.put("lastApprovalDate", "Last Approved Date");
		columnsMap.put("serviceCategory", "Service Category");
		columnsMap.put("contractStartOn", "Contract Start Date");
		columnsMap.put("contractExpireOn", "Contract Expiration Date");
		columnsMap.put("lastApprovedBy", "Last Approved By");

		byte[] file = ObjectExcelMapper.createExcel(customerReportByCriteria.getObjectData(), columnsMap);
		return file;
	}
}
