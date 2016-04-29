package com.techvisio.eserve.db.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.CustomerReport;
import com.techvisio.eserve.beans.ReportAttribute;
import com.techvisio.eserve.db.ReportDao;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.DateUtil;

@Component
public class ReportDaoImpl extends BaseDao implements ReportDao {

	@Override
	public List<CustomerReport> getCustomerReportByCriteria(ReportAttribute customerReportAttribute, Long clientId) {

		String ascOrDsc = customerReportAttribute.isAscending()?"ASC":"DESC";
		String queryString="SELECT * FROM (SELECT CD.CUSTOMER_ID customerId, CD.CUSTOMER_CODE customerCode, "
				+ "CD.CUSTOMER_NAME customerName, "
				+ "CD.CONTACT_NO contactNo,CD.EMAIL_ID emailId, "
				+ "CTM.CUSTOMER_TYPE customerType,UD.UNIT_ID unitId, "
				+ "UD.UNIT_CODE unitCode, UD.VERSION_ID versionId, "
				+ "UD.APPROVAL_STATUS approvalStatus, UD.ASSET_NO assetNo, "
				+ "UD.MACHINE_SERIAL_NO machineSerialNo, UD.MODEL_NO modelNo, "
				+ "UD.LAST_APPROVAL_DATE lastApprovalDate, "
				+ "UCT.UNIT_TYPE unitType, "
				+ "SA.SERVICE_AGREEMENT_ID serviceAgreementId, "
				+ "SA.SERVICE_CATEGORY serviceCategory, "
				+ "SA.CONTRACT_EXPIRE_ON contractExpireOn, "
				+ "SA.CONTRACT_START_ON contractStartOn, SA.APPROVED_BY approvedBy, "
				+ "SAM.SERVICE_PROVIDER serviceProvider "
				+ "FROM tb_customer_detail CD "
				+ "INNER JOIN tb_customer_type_master CTM ON "
				+ "CD.CUSTOMER_TYPE_ID = CTM.CUSTOMER_TYPE_ID "
				+ "INNER JOIN tb_unit_detail UD "
				+ "ON CD.CUSTOMER_ID = UD.CUSTOMER_ID "
				+ "INNER JOIN tb_unit_category_master UCT "
				+ "ON UD.UNIT_CATEGORY_ID = UCT.UNIT_CATEGORY_ID "
				+ "INNER JOIN tb_service_agreement SA "
				+ "ON UD.UNIT_ID=SA.UNIT_ID "
				+ "INNER JOIN tb_service_provider_master SAM "
				+ "ON SA.SERVICE_PROVIDER_ID = SAM.SERVICE_PROVIDER_ID "
				+ "WHERE "
				+ "lower(CTM.CUSTOMER_TYPE_ID) = coalesce(:CUSTOMER_TYPE_ID, CTM.CUSTOMER_TYPE_ID)  "
				+ "and lower(UD.APPROVAL_STATUS) = coalesce(:APPROVAL_STATUS, UD.APPROVAL_STATUS)  "
				+ "and lower(UCT.UNIT_CATEGORY_ID) = coalesce(:UNIT_CATEGORY_ID, UCT.UNIT_CATEGORY_ID) "
				+ "and lower(SAM.SERVICE_PROVIDER_ID) = coalesce(:SERVICE_PROVIDER_ID, SAM.SERVICE_PROVIDER_ID) "
				+ "and CD.client_Id ="+clientId
				+ " AND (DATE(UD.LAST_APPROVAL_DATE) "
				+ "BETWEEN coalesce(:DATE_FROM, DATE(UD.LAST_APPROVAL_DATE)) "
				+ "AND coalesce(:DATE_TO, DATE(UD.LAST_APPROVAL_DATE))) "
				+ "ORDER BY  "+customerReportAttribute.getSortBy()+" "+ascOrDsc+") a "
				+ "limit :START_INDEX,:PAGE_SIZE";
		Query query= getEntityManager().createNativeQuery(queryString);


		String customerTypeId = StringUtils.isEmpty(customerReportAttribute.getCustomerTypeId())?null:customerReportAttribute.getCustomerTypeId();
		String approvalStatus = customerReportAttribute.getApprovalStatus();
		String unitTypeId = StringUtils.isEmpty(customerReportAttribute.getUnitTypeId())?null:customerReportAttribute.getUnitTypeId();
		String serviceProviderId = StringUtils.isEmpty(customerReportAttribute.getServiceProviderId())?null:customerReportAttribute.getServiceProviderId();
		String dateFrom = StringUtils.isEmpty(customerReportAttribute.getFromDate())?null:customerReportAttribute.getFromDate();
		java.sql.Date datef = DateUtil.convertStringToSqlDate(dateFrom);
		String dateTo = StringUtils.isEmpty(customerReportAttribute.getToDate())?null:customerReportAttribute.getToDate();
		java.sql.Date datet = DateUtil.convertStringToSqlDate(dateTo);
		int pageSize = customerReportAttribute.getPageSize();
		int pageNo = customerReportAttribute.getPageNo();


		int startIndex = (pageSize * pageNo) - pageSize;

		query.setParameter("CUSTOMER_TYPE_ID", customerTypeId);
		query.setParameter("APPROVAL_STATUS", approvalStatus);
		query.setParameter("UNIT_CATEGORY_ID", unitTypeId);
		query.setParameter("SERVICE_PROVIDER_ID", serviceProviderId);
		query.setParameter("DATE_FROM", datef);
		query.setParameter("DATE_TO", datet);
		query.setParameter("PAGE_SIZE", pageSize);
		query.setParameter("START_INDEX", startIndex);

		List<CustomerReport> customerReports = new ArrayList<CustomerReport>();

		@SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();
		for (Object[] result : results) {
			CustomerReport customerReport = new CustomerReport();

			Long custId = (long) ((Number) result[0]).intValue();
			customerReport.setCustomerId(custId);
			customerReport.setCustomerCode((String) result[1]);
			customerReport.setCustomerName((String) result[2]);
			customerReport.setContactNo((String) result[3]);
			customerReport.setEmailId((String) result[4]);
			customerReport.setCustomerType((String) result[5]);
			Long untId = (long) ((Number) result[6]).intValue();
			customerReport.setUnitId(untId);
			customerReport.setUnitCode((String) result[7]);
			Double versionId = (double) ((Number) result[8]).intValue();
			customerReport.setVersionId(versionId);
			customerReport.setApprovalStatus((char) result[9]);
			customerReport.setAssetNo((String) result[10]);
			customerReport.setMachineSerialNo((String) result[11]);
			customerReport.setModelNo((String) result[12]);
			Date date =  (Date) result[13];
			String lastApprovalDate = DateUtil.convertDateToString(date,AppConstants.DateFormat.DD_MM_YYYY.getPattern());
			customerReport.setLastApprovalDate(lastApprovalDate);
			customerReport.setUnitType((String) result[14]);
			Long serviceAgreementId = (long) ((Number) result[15]).intValue();
			customerReport.setServiceAgreementId(serviceAgreementId);
			customerReport.setServiceCategory((String) result[16]);
			Date date1 =  (Date) result[17];
			String contractExpireOn = DateUtil.convertDateToString(date1);
			customerReport.setContractExpireOn(contractExpireOn);
			Date date2 =  (Date) result[18];
			String contractStartOn = DateUtil.convertDateToString(date2);
			customerReport.setContractStartOn(contractStartOn);
			if( result[19] != null){
				Long approvedBy = (long) ((Number) result[19]).intValue();
				customerReport.setApprovedBy(approvedBy);
			}

			customerReports.add(customerReport);
		}
		return customerReports;

	}


}
