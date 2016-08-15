package com.techvisio.eserve.db.impl;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.CustomerReport;
import com.techvisio.eserve.beans.ReportAttribute;
import com.techvisio.eserve.beans.SearchResultData;
import com.techvisio.eserve.db.ReportDao;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.DateUtil;

@Component
public class ReportDaoImpl extends BaseDao implements ReportDao {

	@Override
	public SearchResultData getCustomerReportByCriteria(ReportAttribute customerReportAttribute, Long clientId) throws ParseException {

		SearchResultData<CustomerReport> searchResultData= new SearchResultData<CustomerReport>();
		String ascOrDsc = customerReportAttribute.getIsAscending()?"ASC":"DESC";

		String queryString = filterBasedQueryForCustomerReport(customerReportAttribute, clientId, ascOrDsc);
		Query query= getEntityManager().createNativeQuery(queryString);
		
		String queryString1 = "SELECT count(*),'totalCount' FROM"
				+ " (SELECT CD.CUSTOMER_ID customerId,"
				+ " CD.CUSTOMER_CODE customerCode, CD.CUSTOMER_NAME customerName,"
				+ " CD.CONTACT_NO contactNo, CD.EMAIL_ID emailId,"
				+ " CTM.CUSTOMER_TYPE customerType, UD.UNIT_ID unitId,"
				+ " UD.UNIT_CODE unitCode, UD.VERSION_ID versionId,"
				+ " UD.APPROVAL_STATUS approvalStatus, UD.ASSET_NO assetNo,"
				+ " UD.MACHINE_SERIAL_NO machineSerialNo, UD.MODEL_NO modelNo,"
				+ " UD.LAST_APPROVAL_DATE lastApprovalDate, UD.LAST_APPROVED_BY lastApprovedBy, UCT.UNIT_TYPE unitType,"
				+ " SA.SERVICE_AGREEMENT_ID serviceAgreementId,"
				+ " SA.SERVICE_CATEGORY serviceCategory,"
				+ " SA.CONTRACT_EXPIRE_ON contractExpireOn,"
				+ " SA.CONTRACT_START_ON contractStartOn,"
				+ " SA.APPROVED_BY approvedBy,"
				+ " SAM.SERVICE_PROVIDER serviceProvider"
				+ " FROM TB_CUSTOMER_DETAIL CD"
				+ " INNER JOIN TB_CUSTOMER_TYPE_MASTER CTM"
				+ " ON CD.CUSTOMER_TYPE_ID = CTM.CUSTOMER_TYPE_ID"
				+ " INNER JOIN TB_UNIT_DETAIL UD"
				+ " ON CD.CUSTOMER_ID = UD.CUSTOMER_ID"
				+ " INNER JOIN TB_UNIT_CATEGORY_MASTER UCT"
				+ " ON UD.UNIT_CATEGORY_ID = UCT.UNIT_CATEGORY_ID"
				+ " INNER JOIN TB_SERVICE_AGREEMENT SA"
				+ " ON UD.UNIT_ID=SA.UNIT_ID"
				+ " INNER JOIN TB_SERVICE_PROVIDER_MASTER SAM"
				+ " ON SA.SERVICE_PROVIDER_ID = SAM.SERVICE_PROVIDER_ID"
				+ " WHERE"
				+ " lower(CTM.CUSTOMER_TYPE_ID) = coalesce(:CUSTOMER_TYPE_ID, CTM.CUSTOMER_TYPE_ID)"
				+ "  and lower(UD.APPROVAL_STATUS) = coalesce(:APPROVAL_STATUS, UD.APPROVAL_STATUS)"
				+ "  and lower(UCT.UNIT_CATEGORY_ID) = coalesce(:UNIT_CATEGORY_ID, UCT.UNIT_CATEGORY_ID)"
				+ " and lower(SAM.SERVICE_PROVIDER_ID) = coalesce(:SERVICE_PROVIDER_ID, SAM.SERVICE_PROVIDER_ID)"
				+ " AND (coalesce(DATE(UD.LAST_APPROVAL_DATE),now()) "
				+ "BETWEEN :DATE_FROM "
				+ "AND :DATE_TO))a";
		
		
		Query query1= getEntityManager().createNativeQuery(queryString1);


		String customerTypeId = StringUtils.isEmpty(customerReportAttribute.getCustomerTypeId())?null:customerReportAttribute.getCustomerTypeId();
		String approvalStatus = customerReportAttribute.getApprovalStatus();
		String unitTypeId = StringUtils.isEmpty(customerReportAttribute.getUnitTypeId())?null:customerReportAttribute.getUnitTypeId();
		String serviceProviderId = StringUtils.isEmpty(customerReportAttribute.getServiceProviderId())?null:customerReportAttribute.getServiceProviderId();
		String dateFromString,dateToString = null;
		java.sql.Date dateToSql;
		java.sql.Date dateFromSql;
		if(customerReportAttribute.getFromDate() == null)
		{
			dateFromString = "March 01, 1986";
			dateFromSql = DateUtil.convertStringToSqlDate(dateFromString, AppConstants.DateFormat.MM_dd_yyyy.getPattern());
		}
		else
		{
			dateFromString = customerReportAttribute.getFromDate();
			dateFromSql = DateUtil.convertStringToSqlDate(dateFromString);
		}

		if(customerReportAttribute.getToDate() == null)
		{
			dateToString = "March 01, 9999";
			dateToSql = DateUtil.convertStringToSqlDate(dateToString, AppConstants.DateFormat.MM_dd_yyyy.getPattern()); 
		}
		else
		{
			dateToString = customerReportAttribute.getToDate();
			dateToSql = DateUtil.convertStringToSqlDate(dateToString);
		}

		int pageSize,pageNo;
		if(customerReportAttribute.getPageSize()==0)
		{
			pageSize = 3;
		}
		else
		{
			pageSize = customerReportAttribute.getPageSize();
		}
		if(customerReportAttribute.getPageNo() == 0)
		{
			pageNo = 1;
		}
		else
		{
			pageNo = customerReportAttribute.getPageNo();
		}
		int startIndex = (pageSize * pageNo) - pageSize;

		query.setParameter("CUSTOMER_TYPE_ID", customerTypeId);
		query.setParameter("APPROVAL_STATUS", approvalStatus);
		query.setParameter("UNIT_CATEGORY_ID", unitTypeId);
		query.setParameter("SERVICE_PROVIDER_ID", serviceProviderId);
		query.setParameter("DATE_FROM", dateFromSql);
		query.setParameter("DATE_TO", dateToSql);
		query.setParameter("PAGE_SIZE", pageSize);
		query.setParameter("START_INDEX", startIndex);
		
		query1.setParameter("CUSTOMER_TYPE_ID", customerTypeId);
		query1.setParameter("APPROVAL_STATUS", approvalStatus);
		query1.setParameter("UNIT_CATEGORY_ID", unitTypeId);
		query1.setParameter("SERVICE_PROVIDER_ID", serviceProviderId);
		query1.setParameter("DATE_FROM", dateFromSql);
		query1.setParameter("DATE_TO", dateToSql);


		@SuppressWarnings("unchecked")
		List<Object[]> counts = query1.getResultList();
		for (Object[] count : counts) {
			Long count1 = (long) ((Number) count[0]).intValue();
			searchResultData.setTotalCount(count1);
		}

			@SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();
		List<CustomerReport> customerReports = putDataInCustomerReportsFromResultSet(results);
		searchResultData.setObjectData(customerReports);
		return searchResultData;

	}

	private List<CustomerReport> putDataInCustomerReportsFromResultSet( List<Object[]> results) {
		List<CustomerReport> customerReports = new ArrayList<CustomerReport>();
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
			if(result[13]!=null){
			Date date =  (Date) result[13];
			String lastApprovalDate = DateUtil.convertDateToString(date);
			customerReport.setLastApprovalDate(lastApprovalDate);
			}
			customerReport.setLastApprovedBy((String) result[14]);
			customerReport.setUnitType((String) result[15]);
			Long serviceAgreementId = (long) ((Number) result[16]).intValue();
			customerReport.setServiceAgreementId(serviceAgreementId);
			customerReport.setServiceCategory((String) result[17]);
			Date date1 =  (Date) result[18];
			String contractExpireOn = DateUtil.convertDateToString(date1);
			customerReport.setContractExpireOn(contractExpireOn);
			Date date2 =  (Date) result[19];
			String contractStartOn = DateUtil.convertDateToString(date2);
			customerReport.setContractStartOn(contractStartOn);
			if( result[20] != null){
				Long approvedBy = (long) ((Number) result[20]).intValue();
				customerReport.setApprovedBy(approvedBy);
			}

			customerReports.add(customerReport);
		}
		return customerReports;
	}



	private String filterBasedQueryForCustomerReport(ReportAttribute customerReportAttribute, Long clientId,
			String ascOrDsc) {
		String queryString="SELECT * FROM (SELECT CD.CUSTOMER_ID customerId, CD.CUSTOMER_CODE customerCode, "
				+ "CD.CUSTOMER_NAME customerName, "
				+ "CD.CONTACT_NO contactNo,CD.EMAIL_ID emailId, "
				+ "CTM.CUSTOMER_TYPE customerType,UD.UNIT_ID unitId, "
				+ "UD.UNIT_CODE unitCode, UD.VERSION_ID versionId, "
				+ "UD.APPROVAL_STATUS approvalStatus, UD.ASSET_NO assetNo, "
				+ "UD.MACHINE_SERIAL_NO machineSerialNo, UD.MODEL_NO modelNo, "
				+ "UD.LAST_APPROVAL_DATE lastApprovalDate, UD.LAST_APPROVED_BY lastApprovedBy, "
				+ "UCT.UNIT_TYPE unitType, "
				+ "SA.SERVICE_AGREEMENT_ID serviceAgreementId, "
				+ "SA.SERVICE_CATEGORY serviceCategory, "
				+ "SA.CONTRACT_EXPIRE_ON contractExpireOn, "
				+ "SA.CONTRACT_START_ON contractStartOn, SA.APPROVED_BY approvedBy, "
				+ "SAM.SERVICE_PROVIDER serviceProvider "
				+ "FROM TB_CUSTOMER_DETAIL CD "
				+ "INNER JOIN TB_CUSTOMER_TYPE_MASTER CTM ON "
				+ "CD.CUSTOMER_TYPE_ID = CTM.CUSTOMER_TYPE_ID "
				+ "INNER JOIN TB_UNIT_DETAIL UD "
				+ "ON CD.CUSTOMER_ID = UD.CUSTOMER_ID "
				+ "INNER JOIN TB_UNIT_CATEGORY_MASTER UCT "
				+ "ON UD.UNIT_CATEGORY_ID = UCT.UNIT_CATEGORY_ID "
				+ "INNER JOIN TB_SERVICE_AGREEMENT SA "
				+ "ON UD.UNIT_ID=SA.UNIT_ID "
				+ "INNER JOIN TB_SERVICE_PROVIDER_MASTER SAM "
				+ "ON SA.SERVICE_PROVIDER_ID = SAM.SERVICE_PROVIDER_ID "
				+ "WHERE "
				+ "lower(CTM.CUSTOMER_TYPE_ID) = coalesce(:CUSTOMER_TYPE_ID, CTM.CUSTOMER_TYPE_ID)  "
				+ "and lower(UD.APPROVAL_STATUS) = coalesce(:APPROVAL_STATUS, UD.APPROVAL_STATUS)  "
				+ "and lower(UCT.UNIT_CATEGORY_ID) = coalesce(:UNIT_CATEGORY_ID, UCT.UNIT_CATEGORY_ID) "
				+ "and lower(SAM.SERVICE_PROVIDER_ID) = coalesce(:SERVICE_PROVIDER_ID, SAM.SERVICE_PROVIDER_ID) "
				+ "and CD.client_Id ="+clientId
				+ " AND (coalesce(DATE(UD.LAST_APPROVAL_DATE),now()) "
				+ "BETWEEN :DATE_FROM "
				+ "AND :DATE_TO) "
				+ "ORDER BY  "+customerReportAttribute.getSortBy()+" "+ascOrDsc+") a "
				+ "limit :START_INDEX,:PAGE_SIZE";
		return queryString;
	}


}
