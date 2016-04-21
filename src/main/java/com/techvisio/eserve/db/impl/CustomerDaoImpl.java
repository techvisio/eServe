package com.techvisio.eserve.db.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.AgreementDuration;
import com.techvisio.eserve.beans.ApproveUnitDtl;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.CustomerReport;
import com.techvisio.eserve.beans.CustomerReportAttribute;
import com.techvisio.eserve.beans.EquipmentDetail;
import com.techvisio.eserve.beans.EquipmentHistory;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.ServiceAgreement;
import com.techvisio.eserve.beans.ServiceAgreementFinanceHistory;
import com.techvisio.eserve.beans.ServiceAgreementHistory;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.UnitHistory;
import com.techvisio.eserve.db.CacheDao;
import com.techvisio.eserve.db.CustomerDao;
import com.techvisio.eserve.exception.NoEntityFoundException;
import com.techvisio.eserve.factory.UniqueIdentifierGenerator;

@Component
public class CustomerDaoImpl extends BaseDao implements CustomerDao{

	@Autowired
	UniqueIdentifierGenerator identifierGenerator;

	@Autowired
	CacheDao cacheDao;

	@Override
	public Customer getCustomer(Long customerId) {
		String queryString="FROM Customer cus WHERE cus.customerId = "+customerId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Customer> customers= (List<Customer>)query.getResultList();
		if(customers != null && customers.size()>0){
			return customers.get(0);
		}
		return null;
	}

	@Override
	public List<Customer> getCustomers() {
		String queryString="FROM Customer";
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Customer> customers= (List<Customer>)query.getResultList();
		return customers;
	}

	@Override
	public List<Customer> getCustomerByCriteria(SearchCriteria searchCriteria) {

		String queryString="from Customer WHERE client.clientId = coalesce(:clientId, client.clientId) and lower(contactNo) = coalesce(:contactNo, contactNo) and lower(customerCode) = coalesce(:customerCode, customerCode)  and lower(emailId) = coalesce(:emailId, emailId) and  lower(customerName) LIKE :customerName";
		Query query= getEntityManager().createQuery(queryString);

		String customerName = StringUtils.isEmpty(searchCriteria.getCustomerName())?"":searchCriteria.getCustomerName().toLowerCase();
		String emailId = StringUtils.isEmpty(searchCriteria.getEmailId())?null:searchCriteria.getEmailId().toLowerCase();
		String contactNo = StringUtils.isEmpty(searchCriteria.getContactNo())?null:searchCriteria.getContactNo().toLowerCase();
		String customerCode = StringUtils.isEmpty(searchCriteria.getCustomerCode())?null:searchCriteria.getCustomerCode().toLowerCase();
		query.setParameter("customerName", "%"+customerName+"%");
		query.setParameter("contactNo", contactNo);
		query.setParameter("clientId", searchCriteria.getClientId());
		query.setParameter("customerCode", customerCode);
		query.setParameter("emailId", emailId);

		@SuppressWarnings("unchecked")
		List<Customer> result= (List<Customer>)query.getResultList();
		return result;

	}

	@Override
	public Long saveCustomer(Customer customer) {
		if(customer.getCustomerId()==null){

			String customerCode = customer.getCustomerCode();
			populateUniqueIdentifierInCustomerAndUnit(customer, customerCode);

			getEntityManager().persist(customer);
			getEntityManager().flush();

			List<Unit> units= customer.getUnits();
			for(Unit unit : units){

				unit = getUnit(unit.getUnitId());
				if(unit.getServiceAgreement().getUnitId()==null){
					unit.getServiceAgreement().setUnitId(unit.getUnitId());
				}
				unit.getServiceAgreement().setVersionId(unit.getVersionId());
				saveUnit(unit);			
			}
		}

		else{
			for(Unit unit : customer.getUnits()){
				insertServiceExpirationDateInServiceAgreement(unit);
			}
			getEntityManager().merge(customer);
		}
		return customer.getCustomerId();
	}

	private void populateUniqueIdentifierInCustomerAndUnit(Customer customer,
			String customerCode) {
		if(customerCode==null){
			customerCode=identifierGenerator.getUniqueIdentifierForCustomer(customer);
			customer.setCustomerCode(customerCode);
			for(Unit unit : customer.getUnits()){
				if(unit.getUnitCode()==null){
					unit.setUnitCode(identifierGenerator.getUniqueIdentifierForUnit(unit));
				}

				insertServiceExpirationDateInServiceAgreement(unit);
			}
		}
	}

	@Override
	public void saveUnit(List<Unit> units, Long customerId) {

		if(units!=null && units.size()>0){
			for(Unit unit : units){
				unit.setCustomerId(customerId);
				saveUnit(unit);
			}
		}
	}

	@Override
	public Long saveUnit(Unit unit) {

		String unitCode = unit.getUnitCode();
		if(unit.getUnitId() == null){

			if(unitCode==null){
				unitCode=identifierGenerator.getUniqueIdentifierForUnit(unit);
				unit.setUnitCode(unitCode);
			}
			insertServiceExpirationDateInServiceAgreement(unit);
			if(unit.getServiceAgreement().getUnitId()==null){
				unit.getServiceAgreement().setUnitId(unit.getUnitId());
				unit.getServiceAgreement().setVersionId(unit.getVersionId());
			}
			getEntityManager().persist(unit);
			getEntityManager().flush();

		}
		else{
			if(unitCode==null){
				unitCode=identifierGenerator.getUniqueIdentifierForUnit(unit);
				unit.setUnitCode(unitCode);
			}
			updateServiceAgreement(unit.getServiceAgreement(), unit.getUnitId());
			deleteEquipmentDtlExclusion(unit.getEquipmentDetails(), unit.getUnitId());
			getEntityManager().merge(unit);
		}
		getEntityManager().flush();
		return unit.getUnitId();
	}

	private void insertServiceExpirationDateInServiceAgreement(Unit unit) {
		if(unit.getServiceAgreement().getContractStartOnString()!= null && unit.getServiceAgreement().getAgreementDuration() != null && unit.getServiceAgreement().getContractExpireOn()==null){
			Long durationId = unit.getServiceAgreement().getAgreementDuration().getAgreementDurationId();
			Date contractExpireDate = getDurationValue(unit.getServiceAgreement().getContractStartOnString(), durationId);
			unit.getServiceAgreement().setContractExpireOn(contractExpireDate);
		}
	}	

	@Override
	public List<Unit> getUnits(Long customerId) {
		String queryString="FROM Unit u WHERE u.customerId = "+customerId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Unit> units= (List<Unit>)query.getResultList();
		return units;
	}

	public void deleteEquipmentDtlExclusion(
			List<EquipmentDetail> equipmentDetails, Long unitId) {

		List<Long> equipmentDtlId = new ArrayList<Long>();
		if (equipmentDtlId != null) {
			if(equipmentDetails!=null){
				for (EquipmentDetail equipmentDetail : equipmentDetails) {
					if(equipmentDetail.getEquipmentDtlId() != null){
						equipmentDtlId.add(equipmentDetail.getEquipmentDtlId());
					}
				}
			}

			if (equipmentDtlId.size() == 0) {
				equipmentDtlId.add(-1L);
			}
		}

		String deleteQuery = "Delete from tb_equipment_detail where UNIT_ID =:UNIT_ID and EQUIPMENT_DTL_ID not in :EQUIPMENT_DTL_ID";

		Query query=(Query) getEntityManager().createNativeQuery(deleteQuery).setParameter("UNIT_ID", unitId).setParameter("EQUIPMENT_DTL_ID", equipmentDtlId);
		query.executeUpdate();	

	}

	@Override
	public boolean isCustomerExists(Customer customer) {

		String queryString="from Customer WHERE lower(emailId) = coalesce(:emailId, emailId) or lower(contactNo) = coalesce(:contactNo, contactNo)";
		Query query= getEntityManager().createQuery(queryString);

		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.setContactNo(customer.getContactNo());
		searchCriteria.setEmailId(customer.getEmailId());
		String emailId = StringUtils.isEmpty(searchCriteria.getEmailId())?null:searchCriteria.getEmailId().toLowerCase();
		String contactNo = StringUtils.isEmpty(searchCriteria.getContactNo())?null:searchCriteria.getContactNo().toLowerCase();

		query.setParameter("emailId", emailId);
		query.setParameter("contactNo", contactNo);
		@SuppressWarnings("unchecked")
		List<Customer> result= (List<Customer>)query.getResultList();
		if(result != null && result.size()>0){
			Customer existingCustomer = result.get(0);
			if(existingCustomer != null){
				return true;
			}
		}
		return false;
	}

	@Override
	public Unit getUnit(Long unitId) {
		String queryString="FROM Unit u WHERE u.unitId = "+unitId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Unit> units= (List<Unit>)query.getResultList();
		if(units != null && units.size()>0){
			return units.get(0);
		}
		return null;
	}

	public void saveServiceAgreement(ServiceAgreement agreement) {

		if(agreement.getServiceAgreementId() == null){
			getEntityManager().persist(agreement);
		}
		else{
			getEntityManager().merge(agreement);
		}
		getEntityManager().flush();
	}

	@Override
	public void saveServiceAgreementHistory(ServiceAgreementHistory history) {
		if(history.getAgreementHistoryId()==null){
			getEntityManager().persist(history);
		}
	}	

	@Override
	public void saveServiceAgreementFinanceHistory(ServiceAgreementFinanceHistory financeHistory) {
		if(financeHistory.getSrvcAgrmntFinancHstoryId()==null){
			getEntityManager().persist(financeHistory);
		}
	}	

	@Override
	public void saveUnitHistory(UnitHistory unitHistory) {
		getEntityManager().persist(unitHistory);
	}

	@Override
	public void saveEquipmentHistory(EquipmentHistory equipmentHistory) {
		getEntityManager().persist(equipmentHistory);
	}

	private void updateServiceAgreement(ServiceAgreement agreement, Long unitId){

		if(agreement !=null && agreement.getAgreementDuration() != null){
			Date contractExpireDate = getDurationValue(agreement.getContractStartOnString(), agreement.getAgreementDuration().getAgreementDurationId());
			agreement.setContractExpireOn(contractExpireDate);
			if(agreement.getServiceAgreementFinance() != null){
				agreement.getServiceAgreementFinance().setUnitId(unitId);
				agreement.getServiceAgreementFinance().setVersionId(agreement.getVersionId());
			}
		}

	}

	private Date getDurationValue(String contractStartDate, Long durationId) {
		String startDateString = contractStartDate;
		Date startDate = null;
		DateTimeFormatter parser2 = ISODateTimeFormat.dateTime().withZoneUTC();
		if(!StringUtils.isEmpty(startDateString)){
			startDate = parser2.parseDateTime(startDateString).toDate();
		}

		Calendar c = Calendar.getInstance(); 
		c.setTime(startDate);
		AgreementDuration agreementDuration = cacheDao.getAgreementDuration(durationId); 
		c.add(Calendar.MONTH, agreementDuration.getDuration());

		Date date = c.getTime();
		c.setTime(date);
		c.add(Calendar.DATE, -1);
		Date contractExpireDate = c.getTime();
		return contractExpireDate;
	} 

	@Override
	public List<ServiceAgreementHistory> getServiceAgreementHistoryForUnit(Long unitId) {
		String queryString="FROM ServiceAgreementHistory sah WHERE sah.UnitId = "+ unitId;
		Query query= getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<ServiceAgreementHistory> agreementHistories= (List<ServiceAgreementHistory>)query.getResultList();
		return agreementHistories;
	}

	@Override
	public ApproveUnitDtl getUnitForApproval(Long unitId){


		Unit unit = getUnit(unitId);

		if(unit==null){
			throw new NoEntityFoundException("No Unit found with id : "+unitId);
		}
		Customer customer = getCustomer(unit.getCustomerId());

		ApproveUnitDtl unitDtl = new ApproveUnitDtl();

		unitDtl.setUnit(unit);
		unitDtl.setCustomerCode(customer.getCustomerCode());
		unitDtl.setCustomerName(customer.getCustomerName());
		unitDtl.setContactNo(customer.getContactNo());
		unitDtl.setEmailId(customer.getEmailId());

		return unitDtl;

	}

	@Override
	public Customer getEmailId(String EmailId) {
		String queryString="FROM Customer cus WHERE cus.emailId = "+" '" + EmailId +" ' ";
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Customer> customers= (List<Customer>)query.getResultList();

		if(customers != null && customers.size()>0){
			return customers.get(0);
		}
		return null;
	}

	@Override
	public Customer getContactNo(String ContactNo) {
		String queryString="FROM Customer cus WHERE cus.contactNo = "+" '" + ContactNo +" ' ";
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Customer> customers= (List<Customer>)query.getResultList();
		if(customers != null && customers.size()>0){
			return customers.get(0);
		}
		return null;
	}

	@Override
	public List<CustomerReport> getCustomerReportByCriteria(CustomerReportAttribute customerReportAttribute) {

		String queryString="SELECT CD.CUSTOMER_ID, CD.CUSTOMER_CODE, CD.CUSTOMER_NAME, CD.CONTACT_NO, CD.EMAIL_ID, CTM.CUSTOMER_TYPE, UD.UNIT_ID, UD.UNIT_CODE, UD.VERSION_ID, UD.APPROVAL_STATUS, UD.ASSET_NO, UD.MACHINE_SERIAL_NO, UD.MODEL_NO, UD.LAST_APPROVAL_DATE, UCT.UNIT_TYPE, SA.SERVICE_AGREEMENT_ID, SA.SERVICE_CATEGORY, SA.CONTRACT_EXPIRE_ON, SA.CONTRACT_START_ON, SA.APPROVED_BY, SAF.SERVICE_AGREEMENT_FINANCE_ID, SAF.AGREEMENT_AMOUNT FROM tb_customer_detail CD INNER JOIN tb_customer_type_master CTM ON CD.CUSTOMER_TYPE_ID = CTM.CUSTOMER_TYPE_ID INNER JOIN tb_unit_detail UD ON CD.CUSTOMER_ID = UD.CUSTOMER_ID INNER JOIN tb_unit_category_master UCT ON UD.UNIT_CATEGORY_ID = UCT.UNIT_CATEGORY_ID INNER JOIN tb_service_agreement SA ON UD.UNIT_ID=SA.UNIT_ID INNER JOIN tb_service_agreement_finance SAF ON SA.UNIT_ID=SAF.UNIT_ID WHERE CD.CUSTOMER_CODE = coalesce(:CUSTOMER_CODE, CD.CUSTOMER_CODE) and CD.CUSTOMER_ID = coalesce(:CUSTOMER_ID, CD.CUSTOMER_ID) and lower(CTM.CUSTOMER_TYPE) = coalesce(:CUSTOMER_TYPE, CTM.CUSTOMER_TYPE)  and lower(UD.APPROVAL_STATUS) = coalesce(:APPROVAL_STATUS, UD.APPROVAL_STATUS) and UD.LAST_APPROVAL_DATE = coalesce(:LAST_APPROVAL_DATE, UD.LAST_APPROVAL_DATE) and UD.UNIT_ID = coalesce(:UNIT_ID, UD.UNIT_ID) and lower(UD.UNIT_CODE) = coalesce(:UNIT_CODE, UD.UNIT_CODE) and lower(UCT.UNIT_TYPE) = coalesce(:UNIT_TYPE, UCT.UNIT_TYPE)";
		Query query= getEntityManager().createNativeQuery(queryString);

		String customerCode = StringUtils.isEmpty(customerReportAttribute.getCustomerCode())?null:customerReportAttribute.getCustomerCode();
		String customerType = StringUtils.isEmpty(customerReportAttribute.getCustomerType())?null:customerReportAttribute.getCustomerType();
		String approvalStatus = StringUtils.isEmpty(customerReportAttribute.getApprovalStatus())?null:customerReportAttribute.getApprovalStatus();
		String unitCode = StringUtils.isEmpty(customerReportAttribute.getUnitCode())?null:customerReportAttribute.getUnitCode();
		Long customerId = customerReportAttribute.getCustomerId();
		Date approvalDate = customerReportAttribute.getApprovalDate();
		Long unitId = customerReportAttribute.getUnitId();
		String unitType = StringUtils.isEmpty(customerReportAttribute.getUnitType())?null:customerReportAttribute.getUnitType();

		query.setParameter("CUSTOMER_CODE", customerCode);
		query.setParameter("CUSTOMER_TYPE", customerType);
		query.setParameter("APPROVAL_STATUS", approvalStatus);
		query.setParameter("UNIT_CODE", unitCode);
		query.setParameter("CUSTOMER_ID", customerId);
		query.setParameter("LAST_APPROVAL_DATE", approvalDate);
		query.setParameter("UNIT_ID", unitId);
		query.setParameter("UNIT_TYPE", unitType);

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
			customerReport.setLastApprovalDate((Date) result[13]);
			customerReport.setUnitType((String) result[14]);
			Long serviceAgreementId = (long) ((Number) result[15]).intValue();
			customerReport.setServiceAgreementId(serviceAgreementId);
			customerReport.setServiceCategory((String) result[16]);
			customerReport.setContractExpireOn((Date) result[17]);
			customerReport.setContractStartOn((Date) result[18]);
			if( result[19] != null){
				Long approvedBy = (long) ((Number) result[19]).intValue();
				customerReport.setApprovedBy(approvedBy);
			}
			Long serviceAgreementFinanceId = (long) ((Number) result[20]).intValue();
			customerReport.setServiceAgreementFinanceId(serviceAgreementFinanceId);
			double agreementAmount = (double) ((Number) result[21]).intValue();
			customerReport.setAgreementAmount(agreementAmount);

			customerReports.add(customerReport);
		}
		return customerReports;

	}

}